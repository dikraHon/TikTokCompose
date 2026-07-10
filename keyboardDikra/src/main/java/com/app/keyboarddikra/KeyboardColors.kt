package com.app.keyboarddikra

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class KeyboardColors(
    val waterLight: Color,
    val waterMedium: Color,
    val waterDeep: Color,
    val keyPressed: Color,
    val keyReleased: Color,
    val waterGradientStart: Color,
    val waterGradientEnd: Color,
    val textPressed: Color,
    val textReleased: Color,
    val actionDelete: Color,
    val actionConfirm: Color,
    val shadow: Color,
    val splash: Color,
    val crack: Color,
    val actionHighlight: Color,
    val actionShadow: Color
)

val LightKeyboardColors = KeyboardColors(
    waterLight = Color(0xCCB3E5FC),
    waterMedium = Color(0xCC81D4FA),
    waterDeep = Color(0xCC4FC3F7),
    keyPressed = Color(0xFFE1F5FE),
    keyReleased = Color.White.copy(alpha = 0.85f),
    waterGradientStart = Color(0x4403A9F4),
    waterGradientEnd = Color(0x9901579B),
    textPressed = Color(0xFF0277BD),
    textReleased = Color(0xFF01579B),
    actionDelete = Color(0xFFA5ABB6),
    actionConfirm = Color(0xFF4ADE80),
    shadow = Color(0x50000000),
    splash = Color(0xFF03A9F4),
    crack = Color.Black,
    actionHighlight = Color.White.copy(alpha = 0.4f),
    actionShadow = Color.Black.copy(alpha = 0.2f)
)

val DarkKeyboardColors = KeyboardColors(
    waterLight = Color(0xCC01579B),
    waterMedium = Color(0xCC0277BD),
    waterDeep = Color(0xCC039BE5),
    keyPressed = Color(0xFF0d47a1),
    keyReleased = Color(0xFF1e1e1e).copy(alpha = 0.85f),
    waterGradientStart = Color(0x4401579B),
    waterGradientEnd = Color(0x99000000),
    textPressed = Color(0xFFE1F5FE),
    textReleased = Color(0xFFB3E5FC),
    actionDelete = Color(0xFF454a4f),
    actionConfirm = Color(0xFF2e7d32),
    shadow = Color(0x80000000),
    splash = Color(0xFF0288D1),
    crack = Color.White,
    actionHighlight = Color.White.copy(alpha = 0.15f),
    actionShadow = Color.Black.copy(alpha = 0.4f)
)

val LocalKeyboardColors = staticCompositionLocalOf { LightKeyboardColors }
