package id.arya.scanat.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import id.arya.scanat.R
import id.arya.scanat.library.SharedPrefManager
import id.arya.scanat.ui.main.MainActivity
import javax.inject.Inject


@AndroidEntryPoint
class MyFirebaseNotificationService : FirebaseMessagingService() {

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager

    override fun onNewToken(id: String) {
        super.onNewToken(id)
        sharedPrefManager.saveFirebaseId(id)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val data = remoteMessage.data

        val title = data["title"]
        val body = data["text"]
        if (title != null) {
            if (body != null) {
                sendNotification(body, title)
            }
        }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()

    }

    private fun sendNotification(body: String, title: String) {
        val channelId = getString(R.string.default_notification_channel_id)
        val channelName = getString(R.string.app_name)
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    this.resources,
                    R.mipmap.ic_launcher_salesforce
                )
            )
            .setSmallIcon(R.mipmap.ic_launcher_salesforce)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSound)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.InboxStyle())
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            builder.setChannelId(channelId)
            notificationManager.createNotificationChannel(notificationChannel)
        } else {
            val notification: Notification = builder.build()
            notificationManager.notify(0, notification)
        }
    }
}