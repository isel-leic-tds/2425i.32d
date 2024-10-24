package isel.leic.tds.ttt.ui
import isel.leic.tds.ttt.model.*

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
    println( when {
        winner!=null -> "Winner: $winner"
        isDraw() -> "Draw"
        else -> "Turn: $turn"
    } )
}

