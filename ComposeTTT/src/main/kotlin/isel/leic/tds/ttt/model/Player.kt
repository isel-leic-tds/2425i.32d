package isel.leic.tds.ttt.model

enum class Player {
    X, O;
    val other get() = if (this==X) O else X
//    operator fun invoke(v: Int) { println("Player $v") }
//    operator fun invoke() { println("Player $this") }
}

fun Char.toPlayer() = Player.entries.first { it.name.first() == this }


/*
fun main() {
    val player = Player.X
    player(3)
    player()
}*/
