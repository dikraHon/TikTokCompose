package com.app.showcode

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CodeTheme(
    val background: Color,
    val keyword: Color,
    val string: Color,
    val number: Color,
    val function: Color,
    val annotation: Color,
    val comment: Color,
    val default: Color
)

val DarculaTheme = CodeTheme(
    background = Color(0xFF2B2B2B),
    keyword = Color(0xFFCC7832),
    string = Color(0xFF6A8759),
    number = Color(0xFF6897BB),
    function = Color(0xFFFFC66D),
    annotation = Color(0xFFBBB529),
    comment = Color(0xFF808080),
    default = Color(0xFFA9B7C6)
)

val LocalCodeTheme = staticCompositionLocalOf { DarculaTheme }
