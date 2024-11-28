package isel.leic.tds.ttt.ui

import androidx.compose.runtime.*
import isel.leic.tds.storage.TextFileStorage
import isel.leic.tds.ttt.model.*

enum class Action(val text: String){
    START("Start"), JOIN("Join")
}

class TTTViewModel{
    // Model State
    private val storage = TextFileStorage<Name,Game>("games",GameSerializer)
    private var clash by mutableStateOf(Clash(storage))

    val board: Board? get() = (clash as? ClashRun)?.game?.board
    val score get() = (clash as ClashRun).game.score
    val sidePlayer get() = (clash as? ClashRun)?.sidePlayer

    fun hasClash() = clash is ClashRun
    fun newBoard() = oper(Clash::newBoard)
    fun play(pos: Position) {
        if (board is BoardRun) oper { play(pos) }
    }

    // View State
    var showScore by mutableStateOf(false)
        private set
    fun enableScore() { showScore = true }
    fun disableScore() { showScore = false }

    fun refresh() = oper(Clash::refresh)

    var currentAction: Action? by mutableStateOf(null)
        private set
    fun startClash() { currentAction = Action.START }
    fun joinClash() { currentAction = Action.JOIN }
    fun cancelAction() { currentAction = null }
    fun performAction(name: Name) {
        oper { when(currentAction as Action) {
            Action.JOIN -> clash.join(name)
            Action.START -> clash.start(name)
        }}
        currentAction = null
    }

    var message:String? by  mutableStateOf(null)
        private set
    fun hideMessage() { message=null }
    private fun oper( fx: Clash.()->Clash ) {
        try {
            clash = clash.fx()
        }
        catch (ex: Exception) {
            if (ex is IllegalStateException || ex is IllegalArgumentException)
                message = ex.message
            else throw ex
        }
    }
}