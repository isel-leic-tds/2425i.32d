

class Thing: Any() {
    override fun equals(other: Any?) = other is Thing && other.number==number
    override fun hashCode() = number + 128
    override fun toString() = "Thing($number)"

    val number =  (1..3).random()
}

fun main() {
    val obj = Thing()
    println(obj.toString())
    val obj2 = Thing()
    println(obj2.toString())
    println(obj == obj2)
    println(obj2.hashCode())
    println(obj.equals("abc"))
}