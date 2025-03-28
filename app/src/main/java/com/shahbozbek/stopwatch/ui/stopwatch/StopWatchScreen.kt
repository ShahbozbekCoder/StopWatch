package com.shahbozbek.stopwatch.ui.stopwatch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun StopWatchScreen(myViewModel: StopWatchViewModel = hiltViewModel(), paddingValues: PaddingValues) {

    val elapsedTime by rememberUpdatedState(newValue = myViewModel.elapsedTime)

    val isRunning by myViewModel.running.collectAsState()

    val formattedTime by remember {
        derivedStateOf { formatTime(elapsedTime) }
    }

    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = object : DefaultLifecycleObserver {
        override fun onResume(owner: LifecycleOwner) {
            myViewModel.startWatch()
        }
        override fun onPause(owner: LifecycleOwner) {
            myViewModel.stopWatch()
        }
    }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(
            text = formattedTime,
            fontSize = 42.sp
        )
        Spacer(modifier = Modifier.height(80.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(onClick = { if (!isRunning) {
                myViewModel.startWatch()
            } else {
                myViewModel.stopWatch()
            } }) {
                Text(text = if (isRunning) "Stop" else "Start")
            }
            Button(onClick = {
                myViewModel.resetWatch()
            }) {
                Text(text = "Reset")
            }
        }
    }
}

fun formatTime(time: Long): String {
    val seconds = ((time/ 1000) % 60).toInt()
    val minutes = ((time / (1000 * 60)) % 60).toInt()
    val hours = (time / (1000 * 60 * 60)).toInt()

    val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)

    return formattedTime
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun MainPagePreview() {
    StopWatchScreen(
        hiltViewModel(),
        PaddingValues()
    )
}