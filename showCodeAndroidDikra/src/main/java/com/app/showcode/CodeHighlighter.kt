package com.app.showcode

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

object CodeHighlighter {
    private val keywords = setOf(
        "package", "import", "class", "object", "fun", "val", "var",
        "if", "else", "when", "for", "while", "return", "private",
        "public", "override", "suspend", "composable", "remember",
        "mutableStateOf", "by", "it", "true", "false", "null", "internal"
    )

    fun highlightKotlin(text: String, theme: CodeTheme): AnnotatedString {
        return buildAnnotatedString {
            val tokens = text.split(Regex(pattern = "(?<=[\\s(){}\\[\\].,:;=])|(?=[\\s(){}\\[\\].,:;=])"))
            var isComment = false
            tokens.forEach { token ->
                val trimmed = token.trim()
                if (trimmed.startsWith(prefix = "//")) isComment = true
                val hexColor = tryExtractHexColor(token)
                val style = when {
                    isComment -> SpanStyle(color = theme.comment)
                    hexColor != null -> SpanStyle(color = hexColor, fontWeight = FontWeight.Bold)
                    trimmed in keywords -> SpanStyle(color = theme.keyword, fontWeight = FontWeight.Bold)
                    trimmed.startsWith(prefix = "@") -> SpanStyle(color = theme.annotation)
                    token.startsWith(prefix = "\"") && token.endsWith(suffix = "\"") -> SpanStyle(color = theme.string)
                    token.toIntOrNull() != null || token.toFloatOrNull() != null -> SpanStyle(color = theme.number)
                    token.contains(Regex(pattern = "[a-zA-Z0-9]+(?=\\()")) -> SpanStyle(color = theme.function)
                    trimmed.firstOrNull()?.isUpperCase() == true && !keywords.contains(trimmed) -> {
                        SpanStyle(color = theme.default, fontWeight = FontWeight.Bold)
                    }
                    else -> SpanStyle(color = theme.default)
                }
                
                withStyle(style) {
                    append(token)
                }
            }
        }
    }

    private fun tryExtractHexColor(token: String): Color? {
        val trimmed = token.trim()
        if (trimmed.startsWith(prefix = "0xFF") && (trimmed.length == 10)) {
            return try {
                val colorLong = trimmed.substring(startIndex = 2).toLong(radix = 16)
                Color(colorLong)
            } catch (e: Exception) {
                Log.e("TAG", "Error extracting color: $e")
                null
            }
        }
        return null
    }
}
