package com.jgbravo.notificationapp.app.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.jgbravo.notificationapp.app.notification.models.Counter

class CounterNotificationReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val service = CounterNotificationService(context)
        if (intent?.hasExtra("reset") == true) {
            Counter.value = 0
            service.showNotification(Counter.value)
        } else {
            service.showNotification(++Counter.value)
        }
    }
}