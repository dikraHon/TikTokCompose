package com.app.keyboarddikra

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

class KeyboardState(
    initialLayout: List<List<String>> = KeyboardLayouts.QWERTY
) {
    var textFieldValue by mutableStateOf(value = TextFieldValue(""))
        private set

    var currentLayout by mutableStateOf(value = initialLayout)
        private set

    fun toggleLanguage() {
        currentLayout = if (currentLayout == KeyboardLayouts.QWERTY) {
            KeyboardLayouts.RUSSIAN
        } else {
            KeyboardLayouts.QWERTY
        }
    }

    fun handleKeyClick(char: String) {
        val selection = textFieldValue.selection
        val oldText = textFieldValue.text
        val newText = oldText.replaceRange(
            startIndex = selection.start,
            endIndex = selection.end,
            replacement = char
        )
        val newCursorPos = selection.start + char.length
        textFieldValue = textFieldValue.copy(
            text = newText,
            selection = TextRange(index = newCursorPos)
        )
    }

    fun handleDeleteClick() {
        val selection = textFieldValue.selection
        val oldText = textFieldValue.text
        if (selection.start > 0 || selection.end > selection.start) {
            val start = if (selection.end > selection.start) selection.start else selection.start - 1
            val end = if (selection.end > selection.start) selection.end else selection.start
            val newText = oldText.replaceRange(
                startIndex = start,
                endIndex = end,
                replacement = ""
            )
            textFieldValue = textFieldValue.copy(
                text = newText,
                selection = TextRange(index = start)
            )
        }
    }

    fun handleDeleteAll() {
        textFieldValue = TextFieldValue("", selection = TextRange(index = 0))
    }

    fun handleSpaceClick() {
        handleKeyClick(char = " ")
    }

    fun updateValue(newValue: TextFieldValue) {
        textFieldValue = newValue
    }
}