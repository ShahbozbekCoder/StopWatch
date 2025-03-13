package com.shahbozbek.stopwatch

import android.content.Context
import android.media.SoundPool
import android.os.SystemClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MyViewModel (
    private val myRepository: MyRepository,
    context: Context
): ViewModel() {

    var elapsedTime by mutableLongStateOf(0L)
        private set

    private var startTime = 0L
    var running: MutableStateFlow<Boolean> =  MutableStateFlow(false)
    private var job: Job? = null

    private val soundPool = SoundPool.Builder().setMaxStreams(3).build()
    private val soundMap = mutableMapOf<String, Int>()

    init {
        soundMap["start"] = soundPool.load(context, R.raw.start_watch, 1)
        soundMap["stop"] = soundPool.load(context, R.raw.stop_watch, 1)
        soundMap["reset"] = soundPool.load(context, R.raw.reset_watch, 1)

        viewModelScope.launch {

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
    }

}