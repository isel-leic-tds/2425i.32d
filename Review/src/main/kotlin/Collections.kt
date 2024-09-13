// List<T>, Set<T>, Map<K,D>

fun main() {
    val l: List<Int> = listOf(3,6,5,6)
    println(l)
    println(l[2])
    val lst10 = ((1..10) + (2..7)).shuffled().toSet()
    println(lst10)
    val m = mapOf("abc" to 10, "xpto" to 20)
    println(m)
    println(m["abc"])
}