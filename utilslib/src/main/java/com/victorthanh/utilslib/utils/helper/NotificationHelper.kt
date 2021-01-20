package com.victorthanh.utilslib.utils.helper
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationHelper(val context: Context, private var totalFile: Int) {

    private val notificationID = 29182
    private val channelId = "notify backup"
    private val finishId = 200
    private var notificationManager: NotificationManager? =null
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var notification: Notification

    fun notifyProgressNotification(isStart: Boolean = true, totalFile: Int = 0) {
        if (totalFile > 0) {
            this.totalFile = totalFile
        }
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelTitle = "BackupChanel"
        notificationBuilder = NotificationCompat.Builder(context, channelId)
        notificationBuilder.setOngoing(true)
//                .setContentTitle(context.getString(R.string.general_title_any_backup_backup_progress))
//                .setSmallIcon(R.drawable.ic_primary_notification_small_icon)


        if (isStart) notificationBuilder.setProgress(100, 1, false)
        if (Build.VERSION.SDK_INT >= 26) {
            val notificationChannel = NotificationChannel(channelId, channelTitle, NotificationManager.IMPORTANCE_LOW)
            notificationChannel.vibrationPattern = longArrayOf(0);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true)
            notificationChannel.setSound(null, null)
            notificationManager?.createNotificationChannel(notificationChannel)
        }
        notification = notificationBuilder.build()
        notificationManager?.notify(notificationID, notification)
    }


    // TODO Just temp for marketing video
    fun updateProgressNotification(progress: Int, currentNumFile: Int = totalFile) {
        notificationBuilder.setProgress(100, progress, false)
//                .setContentTitle("${context.getString(R.string.general_title_any_backup_backup_progress)} $currentNumFile/$totalFile")

        notification = notificationBuilder.build()
        notificationManager?.notify(notificationID, notification)
        if (progress == 100 && currentNumFile == totalFile) {
            notificationManager?.cancel(notificationID)
            val channelTitle = "BackupChanel-Finish"
            notificationBuilder = NotificationCompat.Builder(context, channelId)
            notificationBuilder
//                    .setContentTitle(context.getString(R.string.general_title_any_backup_backup_progress_completed))
//                    .setSmallIcon(R.drawable.ic_primary_notification_small_icon)
            if (Build.VERSION.SDK_INT >= 26) {
                notificationManager?.deleteNotificationChannel(channelId)
                val notificationChannel = NotificationChannel(channelId, channelTitle, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.vibrationPattern = longArrayOf(0);
                notificationChannel.enableVibration(true);
                notificationChannel.setShowBadge(true)
                notificationChannel.setSound(null, null)
                notificationManager?.createNotificationChannel(notificationChannel)
            }

            val notification: Notification = notificationBuilder.build()
            notificationManager?.notify(finishId, notification)
        }
    }

    fun cancelAllNotifyBakup() {
        if (notificationManager == null) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            notificationManager?.deleteNotificationChannel(channelId)
        notificationManager?.cancel(notificationID)
        notificationManager?.cancel(finishId)
    }

}