package isel.leic.tds.ttt.ui
import isel.leic.tds.ttt.model.*

fun Game.show() = board?.show()

fun Game.showScore() {
    (Player.entries+null).forEach { player ->
        println("${player?:"Draw"} = ${score[player]}")
    }
}

private val sepLine = "---" + "+---".repeat(BOARD_DIM-1)

fun Board.show() {
    Position.values.forEach { pos ->
        print(" ${this[pos]?:" "} ")
        if (pos.col< BOARD_DIM-1) print('|')
        else {
            println()
            if (pos.row< BOARD_DIM-1) println(sepLine)
        }
    }
    println( when(this) {
        is BoardWin -> "Winner: $winner"
        is BoardDraw -> "Draw"
        is BoardRun -> "Turn: $turn"
    } )
}

