import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import isel.leic.tds.ttt.model.Board
import isel.leic.tds.ttt.model.Player
import isel.leic.tds.ttt.model.Position
import isel.leic.tds.ttt.model.play
import isel.leic.tds.ttt.ui.Grid
import isel.leic.tds.ttt.ui.Player

@Composable
@Preview
private fun GridTestApp() {
    var board by remember { mutableStateOf(Board()) }

    MaterialTheme {
        Grid(board, onClickCell = { pos ->
            try {
                board = board.play(pos)
            }catch (ex: Exception) {
               println(ex.message)
            }
        })
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Tic Tac Toe",
        state = rememberWindowState(size = DpSize.Unspecified)
    ) {
        GridTestApp()
    }
}
