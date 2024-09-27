import linkedlist.MutableStack
import kotlin.test.*

class StackTest {
    @Test
    fun `test stack`() {
        val stack = MutableStack<Char>()
        assertTrue(stack.isEmpty())
        stack.push('A')
        assertEquals('A', stack.top)
        stack.push('B')
        assertEquals('B', stack.top)
        assertFalse(stack.isEmpty())
        while(!stack.isEmpty()) {
            println(stack.pop())
        }
        assertTrue(stack.isEmpty())
    }
    @Test fun `empty stack conditions`() {
        val sut = MutableStack<String>()
        assertTrue(sut.isEmpty())
        assertFailsWith<NoSuchElementException> { sut.top }
        assertFailsWith<NoSuchElementException> { sut.pop() }
    }
    @Test fun `no empty stack conditions`() {
        val sut = MutableStack<Int>()
        sut.push(27)
        assertFalse(sut.isEmpty())
        assertEquals(27, sut.top)
        assertEquals(27,sut.pop())
        assertTrue(sut.isEmpty())
    }
}