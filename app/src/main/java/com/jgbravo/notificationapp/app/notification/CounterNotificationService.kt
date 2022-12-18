package com.jgbravo.notificationapp.app.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.jgbravo.notificationapp.R
import com.jgbravo.notificationapp.ui.MainActivity

class CounterNotificationService(
    private val context: Context
) {

    companion object {
        const val COUNTER_CHANNEL_ID = "counter_channel"
    }

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(counter: Int) {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Increment counter")
            .setContentText("The count is $counter")
            .setContentIntent(activityPendingIntent)
            .addAction(
                R.drawable.ic_notification,
                "Increment",
                buildCreateIncrementReceiver()
            )
            .addAction(
                R.drawable.ic_reload,
                "Reset",
                buildCounterResetReceiver()
            )
            .build()

        notificationManager.notify(1, notification)
    }

    private fun buildCreateIncrementReceiver(): PendingIntent {
        val intent = Intent(context, CounterNotificationReceiver::class.java)
        return PendingIntent.getBroadcast(
            context,
            2,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun buildCounterResetReceiver(): PendingIntent {
        val intent = Intent(context, CounterNotificationReceiver::class.java).apply {
            putExtra("reset", 0)
        }
        return PendingIntent.getBroadcast(
            context,
            5,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
}