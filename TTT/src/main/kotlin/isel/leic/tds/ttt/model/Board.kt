package isel.leic.tds.ttt.model

const val BOARD_DIM = 2
const val BOARD_CELLS = BOARD_DIM* BOARD_DIM

typealias Moves = Map<Position,Player>

// States of Board (Run,Win,Draw)
/*
abstract class Board(val moves: Moves)
class BoardRun(val turn: Player, moves: Moves): Board(moves)
class BoardWin(val winner: Player, moves: Moves): Board(moves)
class BoardDraw(moves: Moves): Board(moves)
*/

class Board(
    val turn: Player = Player.X,
    val moves: List<Player?> = List(BOARD_CELLS) { null },
    val winner: Player? = null
)

operator fun Board.get(p: Position) = moves[p.index]

fun Board.play(pos: Position): Board {
    require(moves[pos.index]==null) { "Position $pos is already used" }
    check(winner==null) { "Game over" }
    val movesAfter = moves.mapIndexed { idx, p -> if (idx == pos.index) turn else p }
    return Board(
        turn = turn.other,
        moves = movesAfter,
        winner = winnerIn(pos, movesAfter)
    )
}

private fun winnerIn(p: Position, moves: List<Player?>): Player? {
    val player = checkNotNull(moves[p.index])
    val places = Position.values.filter { moves[it.index] == player }
    if (places.size < BOARD_DIM) return null
    return player.takeIf {
        places.count { it.row == p.row } == BOARD_DIM ||
        places.count { it.col == p.col } == BOARD_DIM ||
        p.slash && places.count { it.slash } == BOARD_DIM ||
        p.backSlash && places.count { it.backSlash } == BOARD_DIM
    }
}

fun Board.isDraw() = winner==null && moves.none { it == null }