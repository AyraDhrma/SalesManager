package id.arya.scanat.ui.scan

import android.animation.Animator
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import id.arya.scanat.R
import kotlinx.android.synthetic.main.activity_scanner.*

class ScannerActivity : AppCompatActivity(), DecoratedBarcodeView.TorchListener {
    private lateinit var captureManager: CaptureManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        settingAnimation()

        zxing_barcode_scanner.setTorchListener(this)
        zxing_barcode_scanner.viewFinder.visibility = View.INVISIBLE

        captureManager = CaptureManager(this, zxing_barcode_scanner)
        captureManager.initializeFromIntent(intent, savedInstanceState)
        captureManager.decode()

        if (!hasFlash()) {
            switch_flashlight.visibility = View.GONE
        }

        listener()
    }

    private fun settingAnimation() {
        animation_scan.setAnimation(R.raw.scan_car)
        animation_scan.playAnimation()
        animation_scan.speed = 2.5f
        animation_scan.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                animation_scan.playAnimation()
                animation_scan.setMinProgress(0.7f)
                animation_scan.speed = 0.5f
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
    }

    private fun listener() {
        switch_flashlight.setOnClickListener {
            switchFlashLight()
        }

        home_back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        captureManager.onResume()
    }

    override fun onPause() {
        super.onPause()
        captureManager.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        captureManager.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        captureManager.onSaveInstanceState(outState)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return zxing_barcode_scanner.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }

    private fun hasFlash(): Boolean {
        return applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    private fun switchFlashLight() {
        if (getString(R.string.turn_on_flashlight) == switch_flashlight.text) {
            zxing_barcode_scanner.setTorchOn()
        } else {
            zxing_barcode_scanner.setTorchOff()
        }
    }

    override fun onTorchOn() {
        switch_flashlight.setText(R.string.turn_off_flashlight)
        switch_flashlight.setCompoundDrawablesWithIntrinsicBounds(0,
            R.drawable.ic_baseline_flash_on, 0, 0)
    }

    override fun onTorchOff() {
        switch_flashlight.setText(R.string.turn_on_flashlight)
        switch_flashlight.setCompoundDrawablesWithIntrinsicBounds(0,
            R.drawable.ic_baseline_flash_off, 0, 0)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        captureManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}