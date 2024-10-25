import kotlin.test.*
import isel.leic.tds.ttt.model.*
import isel.leic.tds.ttt.ui.show

// All tests assume BOARD_DIM = 3
private fun playSequence(vararg moves: Int): Board =
    moves.fold(Board()) { b, idx -> b.play(Position(idx))}

class TestPlay {
    @Test fun `Winner in a Column`() {
        val board = playSequence(4,0,1,3,5,2,7)
        assertIs<BoardWin>(board)
        assertEquals(Player.X,board.winner)
    }
    @Test fun `Winner in a Row`() {
        val board = playSequence(0,3,1,4,2)
        assertIs<BoardWin>(board)
        assertEquals(Player.X,board.winner)
    }
    @Test fun `Winner in Slash`() {
        val board = playSequence(2,0,4,3,6)
        //board.show()
        assertIs<BoardWin>(board)
        assertEquals(Player.X,board.winner)
    }
    @Test fun `Winner in BackSlash`() {
        val board = playSequence(4,1,0,2,3,5,8)
        board.show()
        assertIs<BoardWin>(board)
        assertEquals(Player.X,board.winner)
    }
    @Test fun `Try play in game over`() {
        val board = playSequence(4,1,0,2,3,5,8)
        val ex = assertFailsWith<IllegalStateException> {
            board.play(Position(7))
        }
        assertEquals("Game over",ex.message)
    }
}