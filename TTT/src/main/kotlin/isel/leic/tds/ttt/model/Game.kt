package isel.leic.tds.ttt.model

typealias Score = Map<Player?,Int>

data class Game(
    val board: Board? = null,
    val firstPlayer: Player = Player.entries.first(),
    val score: Score = (Player.entries+null).associateWith { 0 }
)

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




