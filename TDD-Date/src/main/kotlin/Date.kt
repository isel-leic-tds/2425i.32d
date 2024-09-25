private const val START_YEAR_GREGORIAN = 1582
private const val MAX_YEAR = 2500

private val daysOfMonth = listOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
private const val FEB_LEAP_YEAR_DAYS = 29
private val MAX_MONTH = daysOfMonth.size

const val YEAR_BITS = 12
const val MONTH_BITS = 4
const val DAY_BITS = 5

@JvmInline
value class Date private constructor(val bits: Int) {
    constructor(y: Int, m: Int = 1, d: Int = 1) :this(
        d or (m shl DAY_BITS) or (y shl (DAY_BITS+MONTH_BITS))
    ) {
        require(y in START_YEAR_GREGORIAN..MAX_YEAR) { "Illegal year $y" }
        require(m in 1..MAX_MONTH) { "Illegal month $m"}
        require(d in 1..lastDayOfMonth) { "Illegal day $d" }
    }
    val year get() = bits shr (DAY_BITS+MONTH_BITS)
    val month get() = (bits shr DAY_BITS) and (1 shl MONTH_BITS)-1
    val day get() = bits and (1 shl DAY_BITS)-1

    override fun toString() = "%4d-%02d-%02d".format(year,month,day)

    operator fun compareTo(d: Date): Int = bits-d.bits
}

val Date.isLeapYear get() = year.isLeapYear

val Int.isLeapYear: Boolean
    get() = this % 4 == 0 && this % 100 != 0 || this % 400 == 0

val Date.lastDayOfMonth: Int
    get() = if (month==2 && isLeapYear) FEB_LEAP_YEAR_DAYS else daysOfMonth[month-1]

operator fun Date.plus(days: Int): Date = addDays(days)
operator fun Int.plus(dt: Date): Date = dt.addDays(this)

tailrec fun Date.addDays(days: Int): Date {
    val d = days+day
    return if (d <= lastDayOfMonth)
        Date(year,month,d)
    else
        (if (month!=MAX_MONTH) Date(year,month+1) else Date(year+1))
        .addDays(d-lastDayOfMonth-1)
}