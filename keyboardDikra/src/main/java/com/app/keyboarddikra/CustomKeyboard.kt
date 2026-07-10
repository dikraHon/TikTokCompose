package com.app.keyboarddikra

import android.content.res.Configuration
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun CustomKeyboard(
    modifier: Modifier = Modifier,
    state: KeyboardState? = null,
    layout: List<List<String>> = KeyboardLayouts.QWERTY,
    visible: Boolean = true,
    onKeyClick: (String) -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onDeleteAllClick: () -> Unit = {},
    onSpaceClick: () -> Unit = {},
    onLanguageChange: () -> Unit = {},
    onConfirmClick: () -> Unit = {},
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val haptic = LocalHapticFeedback.current
    val isDark = isSystemInDarkTheme()

    val currentLayout = state?.currentLayout ?: layout

    val colors = remember(isDark) {
        if (isDark) DarkKeyboardColors else LightKeyboardColors
    }

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

    val soundId2 = remember {
        soundPool.load(
            context,
            R.raw.sound_of_droplets_v2,
            1
        )
    }

    val playSoundAndHaptic = {
        lastActivityTime = System.currentTimeMillis()
        soundPool.play(
            soundId2,
            0.4f,
            0.4f,
            0,
            0,
            1f
        )
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
    }

    var currentTime by remember { mutableLongStateOf(value = System.currentTimeMillis()) }
    LaunchedEffect(key1 = lastActivityTime) {
        while (true) {
            val now = System.currentTimeMillis()
            if (now - lastActivityTime > KeyboardAnimations.IDLE_WAVE_DELAY - KeyboardAnimations.IDLE_WAVE_PRE_DELAY) {
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
        targetValue = KeyboardAnimations.WAVE_FLOW_TARGET,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = KeyboardAnimations.WAVE_FLOW_DURATION,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave_offset"
    )

    val waterGradient = Brush.verticalGradient(
        colors = listOf(
            colors.waterLight,
            colors.waterMedium,
            colors.waterDeep
        ),
        startY = waveOffset % KeyboardAnimations.WAVE_FLOW_TARGET,
        endY = (waveOffset % KeyboardAnimations.WAVE_FLOW_TARGET) + KeyboardAnimations.WAVE_FLOW_TARGET
    )

    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    CompositionLocalProvider(value = LocalKeyboardColors provides colors) {
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
                        .padding(bottom = KeyboardDimens.RowHorizontalPadding),
                    verticalArrangement = Arrangement.spacedBy(
                        if (isLandscape) {
                            KeyboardDimens.KeyPadding
                        } else {
                            KeyboardDimens.RowHorizontalPadding
                        }
                    )
                ) {
                    KeyboardLayout(
                        layout = currentLayout,
                        currentTime = currentTime,
                        lastActivityTime = lastActivityTime,
                        isLandscape = isLandscape,
                        onKeyClick = {
                            playSoundAndHaptic()
                            state?.handleKeyClick(char = it)
                            onKeyClick(it)
                        }
                    )

                    KeyboardActions(
                        isLandscape = isLandscape,
                        onDeleteClick = {
                            playSoundAndHaptic()
                            state?.handleDeleteClick()
                            onDeleteClick()
                        },
                        onDeleteAllClick = {
                            playSoundAndHaptic()
                            state?.handleDeleteAll()
                            onDeleteAllClick()
                        },
                        onSpaceClick = {
                            playSoundAndHaptic()
                            state?.handleSpaceClick()
                            onSpaceClick()
                        },
                        onLanguageChange = {
                            playSoundAndHaptic()
                            state?.toggleLanguage()
                            onLanguageChange()
                        },
                        onConfirmClick = {
                            playSoundAndHaptic()
                            onConfirmClick()
                        }
                    )
                }
            }
        }
    }
}
