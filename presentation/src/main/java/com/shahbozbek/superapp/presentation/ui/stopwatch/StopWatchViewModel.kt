package com.shahbozbek.superapp.presentation.ui.stopwatch

import android.content.Context
import android.media.SoundPool
import android.os.SystemClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahbozbek.superapp.domain.usecases.GetTimeUseCase
import com.shahbozbek.superapp.domain.usecases.SaveTimeUseCase
import com.shahbozbek.superapp.presentation.R
import com.shahbozbek.superapp.presentation.utils.Constants.RESET
import com.shahbozbek.superapp.presentation.utils.Constants.START
import com.shahbozbek.superapp.presentation.utils.Constants.STOP
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StopWatchViewModel @Inject constructor(
    private val saveTimeUseCase: SaveTimeUseCase,
    private val getTimeUseCase: GetTimeUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    var elapsedTime by mutableLongStateOf(0L)
        private set

    private var startTime = 0L
    var running: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var job: Job? = null

    private val soundPool = SoundPool.Builder().setMaxStreams(3).build()
    private val soundMap = mutableMapOf<String, Int>()

    init {
        soundMap[START] = soundPool.load(context, R.raw.start_watch, 1)
        soundMap[STOP] = soundPool.load(context, R.raw.stop_watch, 1)
        soundMap[RESET] = soundPool.load(context, R.raw.reset_watch, 1)

        viewModelScope.launch {

            elapsedTime = getTimeUseCase()

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

        playSound(START)

        if (!running.value && job == null) {

            job = viewModelScope.launch {

                running.value = true

                startTime = SystemClock.elapsedRealtime() - elapsedTime

                while (isActive) {

                    elapsedTime = SystemClock.elapsedRealtime() - startTime

                    saveTimeUseCase(elapsedTime)

                    delay(100)
                }
            }
        }
    }

    fun stopWatch() {

        playSound(STOP)

        running.value = false

        job?.cancel()

        job = null
    }

    fun resetWatch() {

        playSound(RESET)

        job?.cancel()

        job = null

        elapsedTime = 0L

        running.value = false

        viewModelScope.launch {
            saveTimeUseCase(elapsedTime)
        }
    }

    override fun onCleared() {
        super.onCleared()
        soundPool.release()
    }

}