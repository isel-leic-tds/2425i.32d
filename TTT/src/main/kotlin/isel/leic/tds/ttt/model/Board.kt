package isel.leic.tds.ttt.model

class Board(
    val turn: Char = 'X',
    val moves: List<Char> = listOf(
        ' ',' ',' ',
        ' ',' ',' ',
        ' ',' ',' ',
    )
)

fun Board.canPlay(pos: Int) =
    pos in moves.indices && moves[pos]==' '

fun Board.play(pos: Int): Board =
    Board(
        turn = if (turn=='X') 'O' else 'X',
        moves = moves.mapIndexed{ idx, p -> if (idx==pos) turn else p }
    )

fun Board.isWinner(p: Char) =
    (0..6 step 3).any{ line -> (0..2).all{ moves[line+it]==p } } ||
    (0..2).any { col -> (0..6 step 3).all{ moves[col+it]==p } } ||
    (0..8 step 4).all{ moves[it]==p } ||
    (2..6 step 2).all{ moves[it]==p }

fun Board.show() {
    repeat(3) { line ->
        println( moves.subList(line*3,line*3+3).joinToString(" | "," "," ") )
        if (line < 2) println("---+---+---")
    }
    when {
        isWinner('X') -> println("Winner: X")
        isWinner('O') -> println("winner: O")
        moves.none{ it == ' '} -> println("Draw")
        else -> println("Turn: $turn")
    }
}