package isel.leic.tds.ttt

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*
import isel.leic.tds.ttt.ui.*

@Composable
fun FrameWindowScope.TTTMenuBar(vm: TTTViewModel, onExit: () -> Unit) = MenuBar{
    Menu("Game") {
        Item("New board", onClick = vm::newBoard)
        Item("Score", onClick = vm::enableScore)
        Item("Exit", onClick = onExit)
    }
    Menu("Clash") {
        Item("Start", onClick = vm::startClash)
        Item("Join", onClick = vm::joinClash)
    }
}

@Composable
private fun FrameWindowScope.TTTApp(onExit: ()->Unit) {
    val vm = remember { TTTViewModel() }
    TTTMenuBar(vm, onExit)
    MaterialTheme {
        if (vm.showScore)
            ScoreView(vm.score, onClose = vm::disableScore)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Grid(vm.board, onClickCell = { pos -> vm.play(pos) })
            StatusBar(vm.board)
        }
        vm.currentAction?.let {
            ClashNameEdit(it, vm::cancelAction, vm::performAction)
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
