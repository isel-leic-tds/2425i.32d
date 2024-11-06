package isel.leic.tds.ttt.ui
import isel.leic.tds.ttt.model.*

class Command(
    val syntax: String = "",
    val isTerminate: Boolean = false,
    val execute: (args: List<String>, clash: Clash) -> Clash = { _, clash -> clash }
)

private val playCommand = Command("<position>") { args, clash ->
    require(args.isNotEmpty()) { "Missing position" }
    val pos = requireNotNull(args[0].toPositionOrNull()) { "Illegal position ${args[0]}"}
    clash.play(pos)
}

private fun commandNoArgs(clashFunction: Clash.()->Clash) = Command { _, clash -> clash.clashFunction() }

private fun commandWithName(clashFunction: Clash.(Name)->Clash) = Command("<name>") { args, clash ->
    require(args.isNotEmpty()) { "Missing name" }
    clash.clashFunction(Name(args[0]))
}

fun getCommands() = mapOf(
    "EXIT" to Command(isTerminate = true),
    "NEW" to commandNoArgs( Clash::newBoard ),
    "PLAY" to playCommand,
    "SCORE" to Command { _, clash -> clash.also{ (it as? ClashRun)?.game?.showScore() } },
    "START" to commandWithName( Clash::start ),
    "JOIN" to commandWithName( Clash::join ),
    "REFRESH" to commandNoArgs( Clash::refresh ),
)
