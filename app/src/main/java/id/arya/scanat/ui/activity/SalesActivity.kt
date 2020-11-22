package id.arya.scanat.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import id.arya.scanat.R
import id.arya.scanat.costumeui.DialogFragmentGps
import id.arya.scanat.library.SharedPrefManager
import id.arya.scanat.model.request.RequestParams
import id.arya.scanat.repository.MainRepository
import id.arya.scanat.ui.dialog.DialogLoading
import id.arya.scanat.viewmodel.MainViewModel
import id.arya.scanat.viewmodelfactory.MainFactory
import kotlinx.android.synthetic.main.activity_sales.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SalesActivity : AppCompatActivity() {

    private val GPS_ENABLE_REQUEST = 1010
    private var photoURI: Uri? = null
    private var latitude: String = ""
    private var longitude: String = ""
    private val REQUEST_IMAGE_CAPTURE = 1009

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainFactory: MainFactory
    private val loadingDialog = DialogLoading()
    private var dialogFragmentGps = DialogFragmentGps()
    private lateinit var bitmap: Bitmap
    private var photo: String = ""
    lateinit var currentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        dependency()
        getLocation()
        listener()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        getLocation()
        checkGpsOn()
    }

    private fun checkGpsOn(): Boolean {
        var isGpsOn = false
        val manager: LocationManager =
            this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            dialogFragmentGps = DialogFragmentGps()
            dialogFragmentGps.show(supportFragmentManager, "GPS")
            isGpsOn = false
        } else {
            isGpsOn = true
        }
        return isGpsOn
    }

    private fun validate(): Boolean {
        var isExist = false

        if (latitude == "" || longitude == "") {
            isExist = checkGpsOn()
        } else if (keterangan_dokumen_activity.text.toString() == "") {
            keterangan_dokumen_activity.error = resources.getString(R.string.cannot_null)
        } else {
            isExist = true
        }
        return isExist
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            val mFusedLocation = LocationServices.getFusedLocationProviderClient(this)
            mFusedLocation.lastLocation.addOnSuccessListener(
                this
            ) { location ->
                latitude = location?.latitude.toString()
                longitude = location?.longitude.toString()
            }
        } else {
            Dexter.withContext(this@SalesActivity)
                .withPermissions(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {
                            if (report.areAllPermissionsGranted()) {
                                Toasty.success(
                                    this@SalesActivity, "All Permission Granted",
                                    Toast.LENGTH_SHORT, true
                                ).show()
                            }
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        // Remember to invoke this method when the custom rationale is closed
                        // or just by default if you don't want to use any custom rationale.
                        token?.continuePermissionRequest()
                    }
                })
                .withErrorListener {
                    Toasty.error(this@SalesActivity, it.name, Toast.LENGTH_SHORT, true).show()
                }
                .check()
        }

    }

    private fun listener() {
        image_activity.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                // Ensure that there's a camera activity to handle the intent
                takePictureIntent.resolveActivity(packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        photoURI = FileProvider.getUriForFile(
                            this,
                            "id.arya.scanat",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }
        }

        add_activity.setOnClickListener {
            if (validate()) {
                val projectCode = intent.getStringExtra("project_code")
                val caption = keterangan_dokumen_activity.text.toString()
                val slcode = sharedPrefManager.loadSalesCode()
                val customer = intent.getStringExtra("customer")

                val requestParams = RequestParams(
                    "" +
                            "$slcode|$customer|$projectCode|||$photo|$latitude#$longitude|$caption" +
                            ""
                )
                mainViewModel.submitActivity(sharedPrefManager.loadApiKey(), requestParams)
                    .observe(this, Observer { res ->
                        if (res.rc == "0000") {
                            Toasty.success(
                                this@SalesActivity, res.message,
                                Toast.LENGTH_SHORT, true
                            ).show()
                            finish()
                        } else {
                            val snackbar = Snackbar.make(
                                keterangan_dokumen_activity,
                                res.message,
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.view.setBackgroundColor(resources.getColor(R.color.colorError))
                            snackbar.show()
                        }
                    })
            }
        }

    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    @SuppressLint("SetTextI18n")
    private fun dependency() {
        val url = sharedPrefManager.loadUrl()
        mainFactory = MainFactory(MainRepository(url))
        mainViewModel = ViewModelProvider(this, mainFactory)[MainViewModel::class.java]
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == Activity.RESULT_OK) {
                // bitmap = bitmapResizer(data?.extras?.get("data") as Bitmap, 1000, 1000)
                val options: BitmapFactory.Options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                val bitmapResult: Bitmap = MediaStore.Images.Media.getBitmap(
                    contentResolver, photoURI
                )
                bitmap = Bitmap.createScaledBitmap(bitmapResult, 1400, 900, false)

                image_activity.setImageBitmap(bitmap)
                photo = encodeImage(bitmap)
            }
        }
    }

    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

}