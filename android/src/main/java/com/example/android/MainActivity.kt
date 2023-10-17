package com.example.android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.android.ui.theme.BlogMultiplatformTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @Preview
    @Composable
    private fun MainScreen() {
        BlogMultiplatformTheme {
            // A surface container using the 'background' color from the theme
            Scaffold {
                Column(modifier = Modifier.padding(it)) {
                    Surface(color = MaterialTheme.colorScheme.background) {
                        Greeting( "Simple things are cotly")
                    }
                    Text(text = "Following ways")
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Following ways")
                    }
                    Button(
                        onClick = {
                            Toast.makeText(this@MainActivity, "Help members move higher", Toast.LENGTH_SHORT).show()
                        }) {
                        Text(text = "Click Me")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BlogMultiplatformTheme {
        Greeting("Android")
    }
}