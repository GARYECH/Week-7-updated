package com.example.myapplication.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun PandaIcon(condition: String, sizeDp: Int = 100) {
    val res = when (condition.uppercase()) {
        "CLEAR" -> R.drawable.sun_panda
        "CLOUDS" -> R.drawable.cloud_panda
        "RAIN", "DRIZZLE", "THUNDERSTORM" -> R.drawable.umbrella_panda
        else -> R.drawable.sun_panda
    }

    Image(
        painter = painterResource(id = res),
        contentDescription = "Weather Panda",
        modifier = Modifier.size(sizeDp.dp)
    )
}