package com.shahbozbek.stopwatch

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.SoundPool
import android.os.IBinder
import android.os.SystemClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MyViewModel (
    private val myRepository: MyRepository,
    private val context: Context
): ViewModel() {

    var elapsedTime by mutableLongStateOf(0L)
        private set

    private var startTime = 0L
    var running: MutableStateFlow<Boolean> =  MutableStateFlow(false)

    private var job: Job? = null

    private val soundPool = SoundPool.Builder().setMaxStreams(3).build()
    private val soundMap = mutableMapOf<String, Int>()

    private var _service = MutableStateFlow<StopwatchService?>(null)
    val service: StateFlow<StopwatchService?> = _service.asStateFlow()

    private var serviceBound = false
    private var serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val localBinder = binder as? StopwatchService.LocalBinder
            _service.value = localBinder?.getService()
            serviceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            _service.value = null
            serviceBound = false
        }
    }

    init {
        soundMap["start"] = soundPool.load(context, R.raw.start_watch, 1)
        soundMap["stop"] = soundPool.load(context, R.raw.stop_watch, 1)
        soundMap["reset"] = soundPool.load(context, R.raw.reset_watch, 1)

        viewModelScope.launch {

            val myTime = _service.value?.elapsedTimeFlow?.last() ?: 0L

            myRepository.saveTime(myTime)

            elapsedTime = myRepository.getTime()

            startTime = SystemClock.elapsedRealtime() - elapsedTime
            startWatch()
        }
    }

    private fun playSound(soundName: String) {
        val soundId = soundMap[soundName]
        soundId?.let {
            soundPool.play(it, 1f, 1f, 0, 0, 1f)
        }
    }

    fun startService(initTime: Long = 0L) {
        val intent = Intent(context, StopwatchService::class.java)
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        _service.value?.startStopwatch(initTime)
    }
    fun stopService() {
        if (serviceBound) {
            context.unbindService(serviceConnection)
            serviceBound = false
        }
        _service.value?.stopStopwatch()
    }

   fun startWatch() {

       playSound("start")

        if (!running.value && job == null) {

            job = viewModelScope.launch {

                running.value = true

                startTime = SystemClock.elapsedRealtime() - elapsedTime

                while (isActive) {

                    elapsedTime = SystemClock.elapsedRealtime() - startTime

                    myRepository.saveTime(elapsedTime)

                    delay(100)
                }
            }
        }
    }

    fun stopWatch() {

        playSound("stop")

        running.value = false

        job?.cancel()

        job = null
    }

    fun resetWatch() {

        playSound("reset")

        job?.cancel()

        job = null

        elapsedTime = 0L

        running.value = false

        viewModelScope.launch {
            myRepository.saveTime(0L)
        }
    }

    override fun onCleared() {
        super.onCleared()
        soundPool.release()
        if (serviceBound) {
            context.unbindService(serviceConnection)
            serviceBound = false
        }
    }
}