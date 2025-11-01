package com.example.myapplication.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.data.dto.WeatherResponse
import com.example.myapplication.ui.component.WeatherDetailItem
import com.example.myapplication.ui.component.PandaIcon
import com.example.myapplication.ui.viewmodel.WeatherState
import com.example.myapplication.ui.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WeatherApp(apiKey: String) {
    val viewModel: WeatherViewModel = viewModel()
    WeatherScreen(viewModel = viewModel, apiKey = apiKey)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel, apiKey: String) {
    var city by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Full screen weather background
        Image(
            painter = painterResource(id = R.drawable.weather_background),
            contentDescription = "Weather Background",
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize().padding(top = 40.dp)
        ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = city,
                        onValueChange = { city = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Search city", color = Color.Gray) },
                        singleLine = true,

                        shape = RoundedCornerShape(25.dp),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                if (city.isNotBlank()) {
                                    viewModel.getWeather(city, apiKey)
                                }
                            }
                        ),

                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (city.isNotBlank()) {
                                viewModel.getWeather(city, apiKey)
                            }
                        },
                        modifier = Modifier.height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50)
                        ),
                        shape = RoundedCornerShape(25.dp),
                        enabled = city.isNotBlank()
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                    }
                }



            when (state) {
                is WeatherState.Idle -> {
                    InitialState()
                }
                is WeatherState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
                is WeatherState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.cloud_panda),
                                contentDescription = "Error",
                                modifier = Modifier.size(80.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                (state as WeatherState.Error).message,
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
                is WeatherState.Success -> {
                    val data = (state as WeatherState.Success).data
                    WeatherResultView(data = data)
                }
            }
        }
    }
}

@Composable
fun InitialState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.sun_panda),
            contentDescription = "Welcome Panda",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            "Search a city to get started",
            fontSize = 18.sp,
            color = Color.White
        )
    }
}

@Composable
fun WeatherResultView(data: WeatherResponse) {
    val condition = data.weather?.firstOrNull()?.main ?: "Clear"

    val rainfall = data.rain?.oneHour ?: data.rain?.threeHours ?: 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp) // REDUCED SPACING
    ) {

        Text(
            text = data.name ?: "Unknown Location",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )


        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = formatDate(data.dt),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Updated as of ${formatTime(data.dt)}",
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )
        }

        Spacer(Modifier.height(80.dp))


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = data.weather?.firstOrNull()?.main ?: "Clear",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                PandaIcon(
                    condition = condition,
                    sizeDp = 160
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${data.main?.temp?.toInt() ?: "--"}°C",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Feels like ${data.main?.feelsLike?.toInt() ?: "--"}°",
                        fontSize = 18.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WeatherDetailItem(
                    title = "HUMIDITY",
                    value = "${data.main?.humidity ?: "--"}%",
                    iconRes = R.drawable.humidity,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                WeatherDetailItem(
                    title = "WIND",
                    value = "${data.wind?.speed?.let { (it * 3.6).toInt() } ?: "--"}km/h",
                    iconRes = R.drawable.wind,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                WeatherDetailItem(
                    title = "FEELS LIKE",
                    value = "${data.main?.feelsLike?.toInt() ?: "--"}°",
                    iconRes = R.drawable.feels_like,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WeatherDetailItem(
                    title = "RAIN FALL",
                    value = "${rainfall}mm",
                    iconRes = R.drawable.umbrella,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                WeatherDetailItem(
                    title = "PRESSURE",
                    value = "${data.main?.pressure ?: "--"}hPa",
                    iconRes = R.drawable.pressure,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                WeatherDetailItem(
                    title = "CLOUDS",
                    value = "${data.clouds?.all ?: "--"}%",
                    iconRes = R.drawable.clouds,
                    modifier = Modifier.weight(1f)
                )
            }
        }


        Spacer(modifier = Modifier.height(40.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            SunriseSunsetItem(
                iconRes = R.drawable.sunrise,
                time = formatTime(data.sys?.sunrise),
                label = "SUNRISE"
            )

            SunriseSunsetItem(
                iconRes = R.drawable.sunset,
                time = formatTime(data.sys?.sunset),
                label = "SUNSET"
            )
        }


        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Composable
fun SunriseSunsetItem(iconRes: Int, time: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(120.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = time,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.9f),
            textAlign = TextAlign.Center
        )
    }
}

fun formatTime(epochSeconds: Long?): String {
    if (epochSeconds == null) return "--:--"
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(Date(epochSeconds * 1000L))
}

fun formatDate(epochSeconds: Long?): String {
    if (epochSeconds == null) return ""
    val sdf = SimpleDateFormat("MMMM dd", Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(Date(epochSeconds * 1000L))
}