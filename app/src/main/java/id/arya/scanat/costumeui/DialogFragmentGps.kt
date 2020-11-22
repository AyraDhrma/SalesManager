package id.arya.scanat.costumeui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.arya.scanat.R
import kotlinx.android.synthetic.main.fragment_dialog_gps.*

class DialogFragmentGps : BottomSheetDialogFragment() {

    private val GPS_ENABLE_REQUEST = 1010

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_gps, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.peekHeight = getWindowHeight()
        }
    }

    private fun getWindowHeight(): Int { // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        val manager: LocationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            activity?.finish()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setDisplayDialog()
        listener()
    }

    private fun listener() {
        costume_dialog_button.setOnClickListener {
            dismiss()
            startActivityForResult(
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                GPS_ENABLE_REQUEST
            )
        }
    }

    private fun setDisplayDialog() {
        illustration.setAnimation(R.raw.gps_anim)
        illustration.playAnimation()
        costume_dialog_title.text = "Turn On GPS"
        costume_dialog_subtitle.text = "You need to turn on GPS to add new activity"
    }

}