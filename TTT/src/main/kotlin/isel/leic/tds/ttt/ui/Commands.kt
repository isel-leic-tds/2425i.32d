package isel.leic.tds.ttt.ui
import isel.leic.tds.ttt.model.*

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
    "SCORE" to Command { _, game -> game.also{ it.showScore() } },
    // TODO: Add commands SAVE and LOAD
)
