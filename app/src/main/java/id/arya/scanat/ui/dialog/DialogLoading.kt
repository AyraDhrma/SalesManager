package id.arya.scanat.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import id.arya.scanat.R
import kotlinx.android.synthetic.main.fragment_dialog_loading.*

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class DialogLoading : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_loading, container, false)
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        animation_loading.setAnimation(R.raw.loading_animation)
        animation_loading.playAnimation()
        animation_loading.loop(true)
    }
}