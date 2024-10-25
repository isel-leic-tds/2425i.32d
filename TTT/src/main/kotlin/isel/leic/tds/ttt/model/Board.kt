package isel.leic.tds.ttt.model

const val BOARD_DIM = 3
const val BOARD_CELLS = BOARD_DIM* BOARD_DIM

typealias Moves = Map<Position,Player>

sealed class Board(val moves: Moves)
class BoardRun(val turn: Player, moves: Moves): Board(moves)
class BoardWin(val winner: Player, moves: Moves): Board(moves)
class BoardDraw(moves: Moves): Board(moves)

fun Board(turn: Player= Player.X): Board = BoardRun(turn, emptyMap())

operator fun Board.get(p: Position) = moves[p]

fun Board.play(pos: Position): Board {
    require(pos !in moves) { "Position $pos is already used" }
    when(this) {
        is BoardWin, is BoardDraw -> error("Game over")
        is BoardRun -> {
            val movesAfter = moves + (pos to turn)
            val winner = winnerIn(pos, movesAfter)
            return when {
                winner!=null -> BoardWin(turn, movesAfter)
                movesAfter.size == BOARD_CELLS -> BoardDraw(movesAfter)
                else -> BoardRun(turn.other, movesAfter)
            }
        }
    }
}

private fun winnerIn(p: Position, moves: Moves): Player? {
    val player = checkNotNull(moves[p])
    val places = moves.filter { it.value == player }.keys
    if (places.size < BOARD_DIM) return null
    return player.takeIf {
        places.count { it.row == p.row } == BOARD_DIM ||
        places.count { it.col == p.col } == BOARD_DIM ||
        p.slash && places.count { it.slash } == BOARD_DIM ||
        p.backSlash && places.count { it.backSlash } == BOARD_DIM
    }
}
