package isel.leic.tds.ttt.ui

import androidx.compose.runtime.*
import isel.leic.tds.storage.TextFileStorage
import isel.leic.tds.ttt.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class Action(val text: String){
    START("Start"), JOIN("Join")
}

class TTTViewModel(private val scope: CoroutineScope) {
    // Model State
    private val storage = TextFileStorage<Name, Game>("games", GameSerializer)
    private var clash by mutableStateOf(Clash(storage))

    val board: Board? get() = (clash as? ClashRun)?.game?.board
    val score get() = (clash as ClashRun).game.score
    val sidePlayer get() = (clash as? ClashRun)?.sidePlayer
    val hasClash get() = clash is ClashRun
    val isSideTurn get() = clash.isSideTurn

    fun newBoard() = oper(Clash::newBoard)
    fun play(pos: Position) = oper { play(pos) }
        .also { waitForOtherSide() }

    fun exit() {
        cancelWaiting()
        clash.exit()
    }

    // View State
    var showScore by mutableStateOf(false)
        private set

    fun enableScore() {
        showScore = true
    }

    fun disableScore() {
        showScore = false
    }

    fun refresh() = oper(Clash::refresh)

    var currentAction: Action? by mutableStateOf(null)
        private set

    fun startClash() {
        currentAction = Action.START
    }

    fun joinClash() {
        currentAction = Action.JOIN
    }

    fun cancelAction() {
        currentAction = null
    }

    fun performAction(name: Name) {
        cancelWaiting()
        oper {
            when (currentAction as Action) {
                Action.JOIN -> clash.join(name)
                Action.START -> clash.start(name)
            }
        }
        waitForOtherSide()
        currentAction = null
    }

    var message: String? by mutableStateOf(null)
        private set

    fun hideMessage() {
        message = null
    }

    private fun oper(fx: Clash.() -> Clash) {
        try {
            clash = clash.fx()
        } catch (ex: Exception) {
            catchException(ex)
        }
    }

    private fun catchException(ex: Exception) {
        if (ex is IllegalStateException || ex is IllegalArgumentException) {
            message = ex.message
            if (ex is ClashNotFound) clash = Clash(clash.storage)
        } else throw ex
    }

    private var waitingJob by mutableStateOf<Job?>(null)
    val isWaiting get() = waitingJob != null

    private fun cancelWaiting() {
        waitingJob?.cancel()
        waitingJob = null
    }

    private fun waitForOtherSide() {
        if (isSideTurn) return
        waitingJob = scope.launch {
            while (true) {
                delay(3000)
                try {
                    clash = clash.refresh()
                    if (isSideTurn) break
                } catch (ex: NoChangeException) { /* continue loop */
                } catch (ex: Exception) { catchException(ex); break }
            }
            waitingJob = null
        }
    }
}