package isel.leic.tds.ttt.model

import isel.leic.tds.storage.Serializer

const val BOARD_DIM = 3
const val BOARD_CELLS = BOARD_DIM* BOARD_DIM

typealias Moves = Map<Position,Player>

sealed class Board(val moves: Moves) {
    override fun equals(other: Any?) = other is Board && moves == other.moves
    override fun hashCode() = moves.hashCode()
}
class BoardRun(val turn: Player, moves: Moves): Board(moves)
class BoardWin(val winner: Player, moves: Moves): Board(moves)
class BoardDraw(moves: Moves): Board(moves)

// TODO: Add BoardSerializer
// Format examples:
//    "RUN X | 4:X 0:O 5:X 1:O"
//    "WIN O | 4:X 0:O 5:X 1:O 6:O 2:X 7:O 3:X 8:O"
//    "DRAW  | 4:X 0:O 5:X 1:O 6:O 2:X 7:X 3:O 8:X"
object BoardSerializer: Serializer<Board> {
    override fun serialize(data: Board): String {
        val moves = data.moves.map { "${it.key}:${it.value}" }.joinToString(" ")
        return when(data) {
            is BoardRun -> "RUN ${data.turn}"
            is BoardWin -> "WIN ${data.winner}"
            is BoardDraw -> "DRAW "
        } + " | $moves"
    }
    override fun deserialize(text: String): Board {
        val (state, plays) = text.split(" | ")
        val (type, player) = state.split(" ")
        val moves = if (plays.isEmpty()) emptyMap()
            else plays.split(" ").associate {
                val (pos, ply) = it.split(":")
                Position(pos.toInt()) to Player.valueOf(ply)
            }
        return when(type) {
            "RUN" -> BoardRun(Player.valueOf(player), moves)
            "WIN" -> BoardWin(Player.valueOf(player), moves)
            "DRAW" -> BoardDraw(moves)
            else -> error("Invalid state $type")
        }
    }
}


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
