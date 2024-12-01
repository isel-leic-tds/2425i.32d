import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

fun log(lab: String) =
    println("$lab: ${Thread.currentThread().name}")

fun main() {
    log("main started")
    application(exitProcessOnExit = false) {
        val scope = rememberCoroutineScope()
        //var clickable by remember { mutableStateOf(true) }
        var job by remember { mutableStateOf<Job?>(null) }
        val clickable = job==null
        log("application started")
        Window(onCloseRequest = ::exitApplication) {
            Row {
                Button(enabled = clickable, onClick = {
                    log("click")
                    job = scope.launch {
                        log("Sleep")
                        repeat(5){ delay(1000); print(it) }
                        job = null
                    }
                }) { Text("Click me") }
                Button(enabled = !clickable, onClick = {
                    job?.cancel()
                    job = null
                }) { Text("Enable clicks") }
            }
        }
        log("application end")
    }
    log("main end")
}