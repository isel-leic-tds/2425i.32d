package isel.leic.tds.ttt.model

const val BOARD_DIM = 3
const val BOARD_CELLS = BOARD_DIM* BOARD_DIM

class Board(
    val turn: Player = Player.X,
    val moves: List<Player?> = List(BOARD_CELLS) { null }
)

fun Board.play(pos: Position): Board {
    check(moves[pos.index]==null) { "Position $pos is already used" }
    return Board(
        turn = turn.other,
        moves = moves.mapIndexed { idx, p -> if (idx == pos.index) turn else p }
    )
}

fun Board.isWinner(p: Player) =
    (0..6 step 3).any{ line -> (0..2).all{ moves[line+it]==p } } ||
    (0..2).any { col -> (0..6 step 3).all{ moves[col+it]==p } } ||
    (0..8 step 4).all{ moves[it]==p } ||
    (2..6 step 2).all{ moves[it]==p }

fun Board.isDraw() = moves.none { it == null }