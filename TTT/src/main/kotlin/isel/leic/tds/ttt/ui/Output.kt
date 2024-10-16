package isel.leic.tds.ttt.ui
import isel.leic.tds.ttt.model.*

fun Board.show() {
    repeat(3) { line ->
        println( moves.subList(line*3,line*3+3).joinToString(" | "," "," "){
            it?.toString() ?: " "
        })
        if (line < 2) println("---+---+---")
    }
    when {
        isWinner(Player.X) -> println("Winner: X")
        isWinner(Player.O) -> println("winner: O")
        isDraw() -> println("Draw")
        else -> println("Turn: $turn")
    }
}

