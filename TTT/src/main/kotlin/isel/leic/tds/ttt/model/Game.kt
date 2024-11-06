package isel.leic.tds.ttt.model

import isel.leic.tds.storage.Serializer

typealias Score = Map<Player?,Int>

data class Game(
    val board: Board? = null,
    val firstPlayer: Player = Player.entries.first(),
    val score: Score = (Player.entries+null).associateWith { 0 }
)

// TODO: Add GameSerializer
// Format: <firstPlayer> # <score> # <board>
// Example: "X # X:2 O:1 null:4 # RUN X | 4:X 0:O 5:X 1:O"
object GameSerializer: Serializer<Game> {
    override fun serialize(data: Game): String {
        val score = data.score.map { "${it.key}:${it.value}" }.joinToString(" ")
        val board = data.board?.let { BoardSerializer.serialize(it) } ?: ""
        return "${data.firstPlayer} # $score # $board"
    }
    override fun deserialize(text: String): Game {
        val (player, score, board) = text.split(" # ")
        val firstPlayer = Player.valueOf(player)
        val scoreMap = score.split(" ").associate {
            val (ply, cnt) = it.split(":")
            Player.entries.firstOrNull { it.name==ply } to cnt.toInt()
        }
        val boardData = if(board.isNotEmpty()) BoardSerializer.deserialize(board) else null
        return Game(boardData, firstPlayer, scoreMap)
    }
}

private fun Score.advance(player: Player?) =
    this - player + (player to checkNotNull(this[player]) +1)

fun Game.newBoard() = Game(
    board = Board(turn = firstPlayer),
    firstPlayer = firstPlayer.other,
    score = if (board is BoardRun) score.advance(board.turn.other) else score
)

fun Game.play(pos: Position): Game {
    checkNotNull(board) { "No board" }
    val board = board.play(pos)
    return copy(
        board = board,
        score = when(board) {
            is BoardWin -> score.advance(board.winner)
            is BoardDraw -> score.advance(null)
            is BoardRun -> score
        }
    )
}




