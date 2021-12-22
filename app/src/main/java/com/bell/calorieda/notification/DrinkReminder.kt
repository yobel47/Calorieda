package com.bell.calorieda.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.bell.calorieda.R
import com.bell.calorieda.data.DataRepository
import com.bell.calorieda.data.entity.DrinkSchedule
import com.bell.calorieda.ui.main.MainActivity
import java.util.*

class DrinkReminder: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val repository = DataRepository.getInstance(context)
        val drinkSchedule = repository.getDrinkScheduleList()
        showNotification(context,drinkSchedule)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun setNotif(context: Context, content: List<DrinkSchedule>) {
        val alarmManager: AlarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DrinkReminder::class.java)
        val calendar = Calendar.getInstance()

        content.map {
            val timeSplit = it.time.split(":").toTypedArray()
            val part1 = timeSplit[0]
            val part2 = timeSplit[1]
            val hour = Integer.parseInt(part1)
            val minute = Integer.parseInt(part2)
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            val pendingIntent = PendingIntent.getBroadcast(context, it.id, intent, 0)
            if (calendar.timeInMillis <= System.currentTimeMillis()) {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
            }
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }

    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun cancelAlarm(context: Context, content: List<DrinkSchedule>){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DrinkReminder::class.java)
        content.map {
            val pendingIntent = PendingIntent.getBroadcast(context, it.id, intent, 0)
            pendingIntent.cancel()

            alarmManager.cancel(pendingIntent)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showNotification(context: Context, content: List<DrinkSchedule>) {
        content.map {
            val notificationManagerCompat =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationIntent = Intent(context, MainActivity::class.java)
            val contentIntent = PendingIntent.getActivity(context, it.id, notificationIntent, 0)
            val builder = NotificationCompat.Builder(context, "2")
                .setSmallIcon(R.drawable.ic_drink)
                .setContentTitle("Drink Reminder")
                .setContentText("Have you drink today?")
                .setContentIntent(contentIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(alarmSound)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val channel = NotificationChannel(
                    "2",
                    "drink",
                    NotificationManager.IMPORTANCE_DEFAULT
                )

                channel.enableVibration(true)
                channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

                builder.setChannelId("1")

                notificationManagerCompat.createNotificationChannel(channel)
            }

            val notification = builder.build()
            notificationManagerCompat.notify(2, notification)
        }

    }

}