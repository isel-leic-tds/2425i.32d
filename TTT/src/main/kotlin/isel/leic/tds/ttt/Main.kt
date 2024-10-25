package isel.leic.tds.ttt
import isel.leic.tds.ttt.model.*
import isel.leic.tds.ttt.ui.*

fun main() {
    var game = Game()
    val cmds = getCommands()
    while (true) {
        val (name, args) = readCommand()
        val cmd = cmds[name]
        if (cmd==null) println("Invalid command $name")
        else try {
            game = cmd.execute(args, game)
            if (cmd.isTerminate) break
            game.show()
        } catch (e : IllegalArgumentException) {
            println("${e.message}. Use: $name ${cmd.syntax}")
        } catch (e : IllegalStateException) {
            println(e.message)
        }
    }
}