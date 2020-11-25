package id.arya.scanat.notification

import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import id.arya.scanat.library.SharedPrefManager
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager

    override fun onNewToken(id: String) {
        super.onNewToken(id)
        sharedPrefManager.saveFirebaseId(id)
    }

}