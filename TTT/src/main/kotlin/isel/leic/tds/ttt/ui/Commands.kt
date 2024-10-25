package isel.leic.tds.ttt.ui
import isel.leic.tds.ttt.model.*

// TODO: Functional solution for commands.
class Command(
    val syntax: String = "",
    val isTerminate: Boolean = false,
    val execute: (args: List<String>, game: Game) -> Game = { _, game -> game }
)

val playCommand = Command("<position>") { args, game ->
    require(args.isNotEmpty()) { "Missing position" }
    val pos = requireNotNull(args[0].toPositionOrNull()) { "Illegal position ${args[0]}"}
    game.play(pos)
}

fun getCommands() = mapOf(
    "EXIT" to Command(isTerminate = true),
    "NEW" to Command { _, game -> game.newBoard() },
    "PLAY" to playCommand,
    "SCORE" to Command { _, game -> game.also{ it.showScore() } }
)
/*
abstract class Command(val syntax: String = ""){
    open fun execute(args: List<String>, game: Game): Game = game
    open val isTerminate: Boolean get() = false
}

object PlayCommand: Command("<position>") {
    override fun execute(args: List<String>, game: Game): Game {
        require(args.isNotEmpty()) { "Missing position" }
        val pos = requireNotNull(args[0].toPositionOrNull()) { "Illegal position ${args[0]}"}
        return game.play(pos)
    }
}

fun getCommands() = mapOf(
    "EXIT" to object :Command() {
        override val isTerminate: Boolean get() = true
    },
    "NEW" to object :Command() {
        override fun execute(args: List<String>, game: Game): Game = game.newBoard()
    },
    "PLAY" to PlayCommand,
    "SCORE" to object :Command() {
        override fun execute(args: List<String>, game: Game) = game.also{ it.showScore() }
    }
)*/
