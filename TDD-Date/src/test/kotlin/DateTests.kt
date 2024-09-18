import kotlin.test.*

class DateTests {
    @Test
    fun `date is correct`() {
        val sut = Date(2024,9,18)
        assertEquals(2024, sut.year)
        assertEquals(9, sut.month)
        assertEquals(18, sut.day)
    }
    @Test
    fun `date without a day`() {
        val sut = Date(2024,9)
        assertEquals(2024, sut.year)
        assertEquals(9, sut.month)
        assertEquals(1, sut.day)
    }
    @Test
    fun `date without a month and day`() {
        val sut = Date(2024)
        assertEquals(2024, sut.year)
        assertEquals(1, sut.month)
        assertEquals(1, sut.day)
    }
    @Test
    fun `verify if is a leap year`() {
        val sut = Date(2024)
        assertTrue(sut.isLeapYear)
        val sut2 = Date(2023)
        assertFalse(sut2.isLeapYear)
        assertTrue(2024.isLeapYear)
        assertFalse(2023.isLeapYear)
    }
    @Test
    fun `get the last day of the month`() {
        val sut = Date(2024, 2)
        assertEquals(29, sut.lastDayOfMonth)
        val sut2 = Date(2023, 9, 18)
        assertEquals(30, sut2.lastDayOfMonth)
    }
    @Test
    fun `make illegal dates unrepresentable`() {
        val ex = assertFailsWith<IllegalArgumentException> {
            val sut = Date(2024, 0, 34)
            assertEquals(2024,sut.year)
        }
        assertEquals("Illegal month 0",ex.message)
    }
}