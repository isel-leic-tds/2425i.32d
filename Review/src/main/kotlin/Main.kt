
const val y = 20

fun main() {
    println("Hello, World!")
    val x = y + 2L
    var z: UByte = 130u
    //...
    z = x.toUByte()
    val res = 3.toByte() + 2.toByte()
    val d = 13 / 2
    val r = 13 % 2

    val price = 4.37F
    println(price+0.01)

    val range = 2.3 .. 10.5
    //println(range.count())
    println(5.3 in range)

    val words = "abc" .. "gato"
    "bata" in words

}

// Int, Long, Byte, Short
// UInt, ULong, UByte, UShort
// Double, Float
// Char, String (UTF-8)
// Boolean
// IntRange, CharRange, Ranges..

//Unit, Any, Nothing
