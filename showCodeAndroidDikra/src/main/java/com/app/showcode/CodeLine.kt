package com.app.showcode

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

@Composable
fun CodeLine(lineNumber: Int, text: String, theme: CodeTheme) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = lineNumber.toString().padStart(length = 3, padChar = ' '),
            fontFamily = FontFamily.Monospace,
            fontSize = 8.sp,
            color = theme.default.copy(alpha = 0.5f),
            lineHeight = 10.sp,
            modifier = Modifier
                .width(32.dp)
                .padding(end = 8.dp),
            textAlign = TextAlign.End
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(color = theme.default.copy(alpha = 0.2f))
        )
        Text(
            text = CodeHighlighter.highlightKotlin(text, theme),
            fontFamily = FontFamily.Monospace,
            fontSize = 8.sp,
            color = theme.default,
            lineHeight = 10.sp,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
