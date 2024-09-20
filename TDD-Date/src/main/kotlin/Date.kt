private const val START_YEAR_GREGORIAN = 1582
private const val MAX_YEAR = 2500

private val daysOfMonth = listOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
private const val FEB_LEAP_YEAR_DAYS = 29
private val MAX_MONTH = daysOfMonth.size

class Date(val year: Int, val month: Int = 1, val day: Int = 1) : Any() {
    init {
        require(year in START_YEAR_GREGORIAN..MAX_YEAR) { "Illegal year $year" }
        require(month in 1..MAX_MONTH) { "Illegal month $month"}
        require(day in 1..lastDayOfMonth) { "Illegal day $day" }
    }
    override fun equals(other: Any?) =
        this===other ||
        other is Date && year==other.year && month==other.month && day==other.day

    override fun hashCode() = day + (month + year*13)*31
    override fun toString() = "%4d-%02d-%02d".format(year,month,day)
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

operator fun Date.compareTo(d: Date): Int = when {
    year != d.year -> year - d.year
    month != d.month -> month -d.month
    else -> day - d.day
}