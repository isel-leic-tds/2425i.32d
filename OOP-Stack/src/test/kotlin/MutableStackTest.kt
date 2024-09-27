import linkedlist.MutableStack
import kotlin.test.*

class MutableStackTest {
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
    @Test fun `two empty stacks are equals`() {
        val sut1 = MutableStack<Int>()
        val sut2 = MutableStack<Int>()
        assertTrue(sut1.equals(sut2))
    }
    @Test fun `non empty stacks maybe equals`() {
        val sut1 = MutableStack<String>()
        sut1.push("27")
        val sut2 = MutableStack<String>()
        sut2.push("27")
        assertTrue(sut1==sut2)
        assertTrue(sut1.hashCode()==sut2.hashCode())
        println(sut1.hashCode())
        sut2.push("10")
        assertFalse(sut1==sut2)
        println(sut2.hashCode())
    }
}