package com.shahbozbek.stopwatch

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class StopwatchService : Service() {

    private val binder = LocalBinder()
    private var startTime = 0L
    private var elapsedTime = 0L
    private var job: Job? = null

    private val _elapsedTimeFlow = MutableStateFlow(0L)
    val elapsedTimeFlow: StateFlow<Long> = _elapsedTimeFlow.asStateFlow()

    override fun onBind(intent: Intent?): IBinder = binder

    inner class LocalBinder : Binder() {
        fun getService(): StopwatchService = this@StopwatchService
    }

    override fun onCreate() {
        super.onCreate()
        startForeground(1, createNotification("00:00:00"))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    fun startStopwatch(initialTime: Long) {
        startTime = SystemClock.elapsedRealtime() - initialTime

        Log.d("TAG", "startStopwatch: $startTime")

        job = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                Log.d("TAG", "startStopwatch: $isActive")
                elapsedTime = SystemClock.elapsedRealtime() - startTime
                _elapsedTimeFlow.value = elapsedTime
                updateNotification(formatTime(elapsedTime))
                delay(100)
            }
        }
    }

    fun stopStopwatch() {
        _elapsedTimeFlow.value = elapsedTime
        job?.cancel()
        job = null
    }

    private fun createNotification(time: String): Notification {
        val notificationChannelId = "STOPWATCH_SERVICE"
        val notificationManager = getSystemService(NotificationManager::class.java)
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(notificationChannelId, "Stopwatch", NotificationManager.IMPORTANCE_LOW)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        notificationManager.createNotificationChannel(channel)

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle("Stopwatch Running")
            .setContentText(time)
            .setSmallIcon(R.drawable.logo)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    private fun updateNotification(time: String) {
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(1, createNotification(time))
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
        job = null
    }
}

