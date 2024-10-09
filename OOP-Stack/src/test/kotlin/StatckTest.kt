import kotlin.test.*
import oop.*

class StatckTest {
    @Test fun basicTest() {
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
    @Test fun `using three stacks`() {
        val s1 = Stack<Int>()
        val s2 = s1.push(27)
        val s3 = s2.push(5)
        assertTrue(s1.isEmpty())
        assertEquals(27,s2.top)
        assertEquals(5,s3.top)
        val s4 = s3.pop()
        assertEquals(27,s4.top)
        //val (top, s5) = s3.pop2()
        //assertEquals(5,top)
        //assertEquals(27,s5.top)
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
    @Test fun equality() {
        val sut1 = Stack<Int>().push(27).push(10)
        val sut2 = Stack<Int>().push(27).push(10)
        assertEquals(sut1,sut2)
        assertEquals(sut1.hashCode(),sut2.hashCode())
        val se1 = Stack<Char>()
        val se2 = Stack<Char>()
        assertEquals(se1,se2)
        assertEquals(se1.hashCode(),se2.hashCode())
    }
    @Test fun `Stack with elements`() {
        val sut = stackOf(2,5,27,10)
        assertEquals(10,sut.top)
        val se = stackOf<Int>()
        assertTrue(se.isEmpty())
        val sut1 = Stack(10) { 'A'+it }
        assertEquals('A'+9,sut1.top)
    }
    @Test fun `Stack toString()`() {
        val sut = stackOf(1,2,7)
        assertEquals("[7, 2, 1]",sut.toString())
    }
}