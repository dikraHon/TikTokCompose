package com.app.tiktokcompose.testingKeyboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.keyboarddikra.CustomKeyboard
import com.app.keyboarddikra.KeyboardLayouts
import com.app.keyboarddikra.KeyboardState

@Composable
fun TestScreenKeyBoard() {
    val keyboardState = remember { KeyboardState() }
    var isKeyboardVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Тест клавиатуры",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            WaterTextField(
                value = keyboardState.textFieldValue,
                onValueChange = { keyboardState.updateValue(it) },
                onFocusChanged = { focused ->
                    if (focused) isKeyboardVisible = true
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        CustomKeyboard(
            state = keyboardState,
            visible = isKeyboardVisible,
            layout = KeyboardLayouts.QWERTY,
            onConfirmClick = {
                isKeyboardVisible = false
                focusManager.clearFocus()
            }
        )
    }
}
