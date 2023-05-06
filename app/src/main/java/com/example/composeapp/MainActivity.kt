package com.example.composeapp

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.format.DateFormat
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeapp.ui.theme.ComposeAppTheme
import java.util.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(){
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()) {
        var isAndroid by remember {
            mutableStateOf(true)
        }
        var dateText by remember {
            mutableStateOf("")
        }
        if (isAndroid) {
            Greeting(name = "Android")
        } else {
            Greeting(name = "iPhone")
        }
        Button(onClick = {
            isAndroid = !isAndroid
        }) {
            Text(text = "Push me!!")
        }
        Text(text = dateText)
        Button(onClick = {
            dateText = getDataText()
        }) {
            Text(text = "Time!")

        }
    }
}

private fun getDataText() :String {
    val date = Date()
    val local = Locale.getDefault()
    val pattern = DateFormat.getBestDateTimePattern(local,"yyyyMMddHHmmss")
    return SimpleDateFormat(pattern,local).format(date)
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeAppTheme {
       MainScreen()
    }
}