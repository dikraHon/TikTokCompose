package com.app.keyboarddikra

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun KeyboardActions(
    isLandscape: Boolean = false,
    onDeleteClick: () -> Unit,
    onDeleteAllClick: () -> Unit,
    onSpaceClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = if (isLandscape) 2.dp else 4.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionButton(
            text = "⌫",
            onClick = onDeleteClick,
            onLongClick = onDeleteAllClick,
            modifier = Modifier.weight(weight = 1f),
            containerColor = KeyboardColors.ActionDelete
        )
        ActionButton(
            text = "Space",
            onClick = onSpaceClick,
            modifier = Modifier.weight(weight = 2f)
        )
        ActionButton(
            icon = {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Confirm",
                    tint = Color.White
                )
            },
            onClick = onConfirmClick,
            modifier = Modifier.weight(weight = 1f),
            containerColor = KeyboardColors.ActionConfirm
        )
    }
}
