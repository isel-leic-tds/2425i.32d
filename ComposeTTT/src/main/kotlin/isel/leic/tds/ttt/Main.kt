package isel.leic.tds.ttt

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*
import isel.leic.tds.ttt.ui.*

@Composable
fun FrameWindowScope.TTTMenuBar(vm: TTTViewModel, onExit: () -> Unit) = MenuBar{
    Menu("Game") {
        Item("New board", enabled = vm.isSideTurn, onClick = vm::newBoard)
        Item("Refresh", enabled = vm.hasClash, onClick = vm::refresh)
        Item("Score", enabled = vm.hasClash, onClick = vm::enableScore)
        Item("Exit", onClick = onExit)
    }
    Menu("Clash") {
        Item("Start", onClick = vm::startClash)
        Item("Join", onClick = vm::joinClash)
    }
}

@Composable
private fun FrameWindowScope.TTTApp(vm: TTTViewModel, onExit: ()->Unit) {
    TTTMenuBar(vm, onExit = onExit )
    MaterialTheme {
        if (vm.showScore)
            ScoreView(vm.score, onClose = vm::disableScore)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Grid(vm.board, onClickCell = { pos -> vm.play(pos) })
            StatusBar(vm.board, vm.sidePlayer)
        }
        vm.currentAction?.let {
            ClashNameEdit(it, onCancel = vm::cancelAction, onAction = vm::performAction)
        }
        vm.message?.let { Message(it, onOk = vm::hideMessage) }
        if (vm.isWaiting) Waiting()
    }
}

fun main() = application {
    val scope = rememberCoroutineScope()
    val vm = remember { TTTViewModel(scope) }
    val onExit = { vm.exit(); exitApplication() }
    Window(
        onCloseRequest = onExit,
        title = "Tic Tac Toe",
        state = rememberWindowState(size = DpSize.Unspecified)
    ) {
        TTTApp(vm, onExit = onExit)
    }
}
