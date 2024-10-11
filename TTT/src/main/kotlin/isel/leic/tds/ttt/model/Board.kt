package isel.leic.tds.ttt.model

class Board(
    val turn: Char = 'X',
    val moves: List<Char> = listOf(
        ' ',' ',' ',
        ' ',' ',' ',
        ' ',' ',' ',
    )
)

fun Board.play(pos: Int): Board {
    require(pos in moves.indices) { "Position $pos out of bounds" }
    check(moves[pos]==' ') { "Position $pos is already used" }
    return Board(
        turn = if (turn == 'X') 'O' else 'X',
        moves = moves.mapIndexed { idx, p -> if (idx == pos) turn else p }
    )
}

fun Board.isWinner(p: Char) =
    (0..6 step 3).any{ line -> (0..2).all{ moves[line+it]==p } } ||
    (0..2).any { col -> (0..6 step 3).all{ moves[col+it]==p } } ||
    (0..8 step 4).all{ moves[it]==p } ||
    (2..6 step 2).all{ moves[it]==p }

fun Board.isDraw() = moves.none { it == ' ' }