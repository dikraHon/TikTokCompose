package com.app.showcode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun CodeScrollingView(
    code: String,
    modifier: Modifier = Modifier,
    autoScroll: Boolean = true,
    typingSpeed: Long = 1L,
    theme: CodeTheme = DarculaTheme
) {
    val lines = remember(key1 = code) { code.split("\n") }
    var displayedLinesCount by remember { mutableIntStateOf(value = 0) }
    var lastLineText by remember { mutableStateOf(value = "") }
    val listState = rememberLazyListState()
    LaunchedEffect(key1 = code) {
        displayedLinesCount = 0
        for (i in lines.indices) {
            val fullLine = lines[i]
            displayedLinesCount = i + 1
            lastLineText = ""
            for (char in fullLine) {
                lastLineText += char
                delay(duration = typingSpeed.milliseconds)
                if (autoScroll && fullLine.isNotEmpty()) {
                    listState.scrollToItem(index = i)
                }
            }
            delay(duration = (typingSpeed * 2).milliseconds)
            
            if (autoScroll) {
                listState.animateScrollToItem(index = i)
            }
        }
    }
    CompositionLocalProvider(value = LocalCodeTheme provides theme) {
        LazyColumn(
            state = listState,
            modifier = modifier
                .fillMaxSize()
                .background(color = theme.background)
                .windowInsetsPadding(insets = WindowInsets.statusBars),
            verticalArrangement = Arrangement.spacedBy(space = 0.dp),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 100.dp
            )
        ) {
            items(displayedLinesCount) { index ->
                val isLastLine = index == displayedLinesCount - 1
                CodeLine(
                    lineNumber = index + 1,
                    text = if (isLastLine) lastLineText else lines[index],
                    theme = theme
                )
            }
        }
    }
}
