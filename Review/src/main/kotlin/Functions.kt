
fun readName(): String {
    print("Enter your name ? ")
    val name = readln()
    return name.ifBlank { "Unknown" }
}

fun String.readAge(default: Int = 18): Int {
    print("Enter your age, $this ?")
    val txt = readln().trim()
    val fx: (Char)->Boolean = { it !in '0'..'9' }
    return if (txt.isEmpty() || txt.any(fx) )
        default
    else
        txt.toInt()
}

fun main() {
    val name = readName()
    val age = name.readAge(default = 20)
    println("Hello $name. Your age is $age")
}