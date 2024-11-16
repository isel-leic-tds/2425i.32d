package isel.leic.tds.ttt

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*
import isel.leic.tds.ttt.model.*
import isel.leic.tds.ttt.ui.*

@Composable
@Preview
private fun FrameWindowScope.TTTApp(onExit: ()->Unit) {
    var game by remember { mutableStateOf(Game()) }
    MenuBar{
        Menu("Game") {
            Item("New"){ game = game.newBoard() }
            Item("Exit", onClick = onExit)
        }
    }
    MaterialTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val board = game.board
            if (board==null) Box(Modifier.size(GRID_SIDE))
            else
                Grid(board, onClickCell = { pos ->
                    if (board is BoardRun) game = game.play(pos)
                })
            StatusBar(board)
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Tic Tac Toe",
        state = rememberWindowState(size = DpSize.Unspecified)
    ) {
        TTTApp(onExit = { exitApplication() })
    }
}
