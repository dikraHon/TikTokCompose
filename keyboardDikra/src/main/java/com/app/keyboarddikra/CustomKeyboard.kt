package com.app.keyboarddikra

import android.content.res.Configuration
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun CustomKeyboard(
    modifier: Modifier = Modifier,
    layout: List<List<String>>,
    visible: Boolean = true,
    onKeyClick: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onDeleteAllClick: () -> Unit = {},
    onSpaceClick: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    var lastActivityTime by remember { mutableLongStateOf(value = System.currentTimeMillis()) }
    
    val soundPool = remember {
        SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            .build()
    }

    val soundId2 = remember { soundPool.load(context, R.raw.sound_of_droplets_v2, 1) }

    val playSound = {
        lastActivityTime = System.currentTimeMillis()
        soundPool.play(soundId2, 0.4f, 0.4f, 0, 0, 1f)
    }

    var currentTime by remember { mutableLongStateOf(value = System.currentTimeMillis()) }
    LaunchedEffect(key1 = lastActivityTime) {
        while(true) {
            val now = System.currentTimeMillis()
            if (now - lastActivityTime > KeyboardAnimations.IDLE_WAVE_DELAY - 500) {
                currentTime = System.currentTimeMillis()
                delay(duration = 16.milliseconds)
            } else {
                delay(duration = KeyboardAnimations.IDLE_CHECK_INTERVAL.milliseconds)
            }
        }
    }

    DisposableEffect(key1 = Unit) {
        onDispose { soundPool.release() }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "water_flow")
    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave_offset"
    )

    val waterGradient = Brush.verticalGradient(
        colors = listOf(
            KeyboardColors.WaterLight,
            KeyboardColors.WaterMedium,
            KeyboardColors.WaterDeep
        ),
        startY = waveOffset % 1000f,
        endY = (waveOffset % 1000f) + 1000f
    )

    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = waterGradient)
                .windowInsetsPadding(insets = WindowInsets.navigationBars),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = KeyboardDimens.MaxKeyboardWidth)
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                verticalArrangement = Arrangement.spacedBy(if (isLandscape) 2.dp else 4.dp)
            ) {
                KeyboardLayout(
                    layout = layout,
                    currentTime = currentTime,
                    lastActivityTime = lastActivityTime,
                    isLandscape = isLandscape,
                    onKeyClick = {
                        playSound()
                        onKeyClick(it)
                    }
                )

                KeyboardActions(
                    isLandscape = isLandscape,
                    onDeleteClick = { playSound(); onDeleteClick() },
                    onDeleteAllClick = { playSound(); onDeleteAllClick() },
                    onSpaceClick = { playSound(); onSpaceClick() },
                    onConfirmClick = { playSound(); onConfirmClick() }
                )
            }
        }
    }
}
