import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
private fun App() {
    val text = mutableStateOf("Hello, World!")
    MaterialTheme {
        Button(onClick = {
            print("\nClick ")
            text.value += "Hello, Desktop!"
        }) {
            print("text = ${text.value}")
            Text(text.value)
        }
    }
}

fun main() {
    print("Main ")
    application(exitProcessOnExit = false) {
        print("Window ")
        Window(onCloseRequest = ::exitApplication) {
            print("App ")
            App()
        }
    }
    println("done.")
}
