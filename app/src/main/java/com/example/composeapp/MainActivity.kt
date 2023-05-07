package com.example.composeapp

import android.os.Bundle
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeapp.ui.theme.ComposeAppTheme


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
fun MainScreen(viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home" ) {
        composable("home") {
            HomeScreen(viewModel,navController)
        }
        composable("comm") {
            CommScreen()
        }
    }
}

@Composable
fun HomeScreen(
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController = rememberNavController()
) {
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()) {
        val isAndroid = viewModel.isAndroid.collectAsState()

        val dateText = viewModel.dateText.collectAsState()
        if (isAndroid.value) {
            Greeting(name = "Android")
        } else {
            Greeting(name = "iPhone")
        }
        Button(onClick = {
            viewModel.onClick()
        }) {
            Text(text = "Push me!!")
        }
        Text(text = dateText.value)
        
        Button(onClick = { navController.navigate("comm")
        }) {
            Text(text = "Navigate CommScreen")
        }
    }
}

@Composable
fun CommScreen(){
    Greeting(name = "CommScreen")
}
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ComposeAppTheme {
       HomeScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun CommPreview() {
    ComposeAppTheme {
        CommScreen()
    }
}