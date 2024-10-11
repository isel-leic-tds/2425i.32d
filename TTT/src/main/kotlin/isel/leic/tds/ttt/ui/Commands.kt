package isel.leic.tds.ttt.ui
import isel.leic.tds.ttt.model.*

abstract class Command(val syntax: String = ""){
    open fun execute(args: List<String>, board: Board?): Board? = board
    open val isTerminate: Boolean get() = false
}

object PlayCommand: Command("<position>") {
    override fun execute(args: List<String>, board: Board?): Board {
        checkNotNull(board) { "No board to play" }
        require(args.isNotEmpty()) { "Missing position" }
        val pos = requireNotNull(args[0].toIntOrNull()) { "Illegal position ${args[0]}"}
        return board.play(pos)
    }
}

fun getCommands(): Map<String,Command> = mapOf(
    "EXIT" to object :Command() {
        override val isTerminate: Boolean get() = true
    },
    "NEW" to object :Command() {
        override fun execute(args: List<String>, board: Board?) = Board('X')
    },
    "PLAY" to PlayCommand
)