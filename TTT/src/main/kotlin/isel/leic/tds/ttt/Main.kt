package isel.leic.tds.ttt
import isel.leic.tds.ttt.model.*

fun main() {
    var board: Board? = null
    while (true) {
        print("> ")
        val cmd = readln().trim().uppercase().split(' ')
        when(cmd[0]) {
            "NEW" -> board = Board(turn='X')
            "EXIT" -> break
            "PLAY" -> {
                val pos =cmd[1].toInt()
                if (board?.canPlay(pos) == true)
                    board = board.play(pos)
                else println("Cant play in $pos")
            }
            else -> println("Invalid command ${cmd[0]}")
        }
        board?.show()
    }
}