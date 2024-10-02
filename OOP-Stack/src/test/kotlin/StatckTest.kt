import kotlin.test.*
import linkedlist.*

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
        assertFalse(sut.isEmpty())
        assertEquals(10,sut.top)
    }
    @Test
    fun `using three stacks`() {
        val s1 = Stack<Int>()
        val s2 = s1.push(27)
        val s3 = s1.push(5)
        assertTrue(s1.isEmpty())
        assertEquals(27,s2.top)
        assertEquals(5,s3.top)
        val s4 = s3.pop()
        assertEquals(27,s4.top)
        val (top, s5) = s3.pop2()
        assertEquals(5,top)
        assertEquals(27,s5.top)
    }
    @Test fun `iterate over stack`() {
        val sut = Stack<Char>().push('A').push('B')
        sut.forEach { println(it) }
        val l: List<Int> = sut.map{ it.code }
        assertEquals(listOf(66,65),l)

        // for(e in c) action(e) →
        // val it = c.iterator()
        // while(it.hasNext()) {
        //     val e = it.next()
        //     action(e)
    }
    @Test fun companionTest() {
        assertEquals("Stack",Stack.name)
        assertEquals(42,Stack.fx())
    }
}