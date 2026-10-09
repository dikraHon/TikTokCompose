package com.app.haze

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HazeShowcaseScreen(
    modifier: Modifier = Modifier
) {
    var blurRadiusDp by remember { mutableFloatStateOf(20f) }
    var cardClickCount by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0F2027),
                        Color(0xFF203A43),
                        Color(0xFF2C5364)
                    )
                )
            )
    ) {
        Box(
            modifier = Modifier
                .offset(x = 40.dp, y = 80.dp)
                .size(160.dp)
                .clip(CircleShape)
                .background(Color(0xFFFF007F).copy(alpha = 0.6f))
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = 40.dp, y = (-50).dp)
                .size(200.dp)
                .clip(CircleShape)
                .background(Color(0xFF7F00FF).copy(alpha = 0.5f))
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = (-30).dp, y = (-100).dp)
                .size(180.dp)
                .clip(CircleShape)
                .background(Color(0xFF00F2FE).copy(alpha = 0.5f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Haze & Animation Module",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
            )

            AnimatedHazeCard(
                title = "Эффект Haze & Glassmorphism",
                subtitle = "Нажатий: $cardClickCount | Кликните для анимации",
                onClick = { cardClickCount++ }
            )

            Spacer(modifier = Modifier.height(24.dp))

            HazeBox(
                style = HazeStyle(
                    blurRadius = blurRadiusDp.dp,
                    tintColor = Color.Black.copy(alpha = 0.25f),
                    borderColor = Color.White.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(20.dp)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Настройка размытия Haze: ${blurRadiusDp.toInt()} dp",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Slider(
                        value = blurRadiusDp,
                        onValueChange = { blurRadiusDp = it },
                        valueRange = 0f..50f,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = Color(0xFF00F2FE)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            HazeBox(
                style = HazeStyle(
                    blurRadius = 16.dp,
                    tintColor = Color(0xFF7F00FF).copy(alpha = 0.2f),
                    borderColor = Color(0xFF00F2FE).copy(alpha = 0.5f),
                    shape = RoundedCornerShape(20.dp)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Модуль :hazeEffects успешно подключен!",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(20.dp)
                )
            }
        }
    }
}
