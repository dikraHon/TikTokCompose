package com.app.keyboarddikra

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

class KeyboardState {
    var textFieldValue by mutableStateOf(value = TextFieldValue(""))
        private set

    fun handleKeyClick(char: String) {
        val selection = textFieldValue.selection
        val oldText = textFieldValue.text
        val newText = oldText.substring(
            0,
            selection.start
        ) + char + oldText.substring(startIndex = selection.end)
        val newCursorPos = selection.start + char.length
        textFieldValue = textFieldValue.copy(
            text = newText,
            selection = TextRange(
                index = newCursorPos
            )
        )
    }

    fun handleDeleteClick() {
        val selection = textFieldValue.selection
        val oldText = textFieldValue.text
        if (selection.start > 0 || selection.end > selection.start) {
            val newText = if (selection.end > selection.start) {
                oldText.substring(0, selection.start) + oldText.substring(startIndex = selection.end)
            } else {
                oldText.substring(0, selection.start - 1) + oldText.substring(startIndex = selection.start)
            }
            val newCursorPos =
                if (selection.end > selection.start) {
                    selection.start
                } else {
                    selection.start - 1
                }
            textFieldValue =
                textFieldValue.copy(
                    text = newText,
                    selection = TextRange(index = newCursorPos)
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
