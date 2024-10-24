import kotlin.test.*
import isel.leic.tds.ttt.model.*
import isel.leic.tds.ttt.ui.show

// All tests assume BOARD_DIM = 3
private fun playSequence(vararg moves: Int): Board =
    moves.fold(Board()) { b, idx -> b.play(Position(idx))}

class TestPlay {
    @Test fun `Winner in a Column`() {
        val board = playSequence(4,0,1,3,7)
        assertEquals(Player.X,board.winner)
    }
    @Test fun `Winner in a Row`() {
        val board = playSequence(0,3,1,4,2)
        assertEquals(Player.X,board.winner)
    }
    @Test fun `Winner in a Slash`() {
        val board = playSequence(2,0,4,3,6)
        //board.show()
        assertEquals(Player.X,board.winner)
    }
}