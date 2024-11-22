package isel.leic.tds.ttt.ui

import androidx.compose.runtime.*
import isel.leic.tds.ttt.model.*

class TTTViewModel{
    // Model State
    private var game by mutableStateOf(Game())
    val board get() = game.board
    val score get() = game.score

    fun newBoard() { game = game.newBoard() }
    fun play(pos: Position) {
        if (board is BoardRun) game = game.play(pos)
    }

    // View State
    var showScore by mutableStateOf(false)
        private set
    fun enableScore() { showScore = true }
    fun disableScore() { showScore = false }

    var currentAction: Action? by mutableStateOf(null)
        private set
    fun startClash() { currentAction = Action.START }
    fun joinClash() { currentAction = Action.JOIN }
    fun cancelAction() { currentAction = null }
    fun performAction(name: Name) {
        println("$currentAction $name")
        currentAction = null
    }
}