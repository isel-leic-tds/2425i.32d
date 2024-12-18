import model.*
import kotlin.test.*

/*
Crie testes automáticos para verificação da correção das funções random e isWinner da classe SlotState.
Para os testes, assuma que pode chamar o construtor primário de SlotState
 */
class SlotStateTest {
    @Test
    fun randomStateTest() {
        val sut = SlotState.random()
        sut.slots.forEach{ assertTrue(it in RANGE_VALUES) }
    }
    @Test
    fun winnerTrueCondition() {
        val sut = SlotState(List(SLOTS){ 2 })
        assertTrue(sut.isWinner())
    }
    @Test
    fun winnerFalseCondition() {
        val sut = SlotState(List(SLOTS){ it.toByte() })
        assertFalse(sut.isWinner())
    }
}