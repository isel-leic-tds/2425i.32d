import kotlin.test.*

class StatckTest {
    @Test
    fun basicTest() {
        val sut = Stack<Int>().push(27).push(10)
        assertFalse(sut.isEmpty())
        assertEquals(10,sut.top)
        var stk = sut
        while (! stk.isEmpty() ) {
            println(stk.top)
            stk = stk.pop()
        }
        assertTrue(stk.isEmpty())
    }
}