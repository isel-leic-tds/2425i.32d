import kotlin.test.*

class StackTest {
    @Test
    fun `test stack`() {
        val stack = Stack<Char>()
        assertTrue(stack.isEmpty())
        stack.push('A')
        assertEquals('A', stack.top())
        stack.push('B')
        assertEquals('B', stack.top())
        assertFalse(stack.isEmpty())
        while(!stack.isEmpty()) {
            println(stack.pop())
        }
        assertTrue(stack.isEmpty())
    }
}