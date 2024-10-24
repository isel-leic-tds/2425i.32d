package isel.leic.tds.ttt.model

@JvmInline
value class Position private constructor(val index: Int) {
    init {
        require(index in 0 ..< BOARD_CELLS) { "Invalid position $index" }
    }
    val row get() = index / BOARD_DIM
    val col get() = index % BOARD_DIM
    val backSlash get() = row == col
    val slash get() = row+col == BOARD_DIM-1
    companion object {
        val values = List(BOARD_CELLS) { Position(it) }
        operator fun invoke(idx: Int) = values[idx]
    }

    override fun toString() = index.toString()
}

fun String.toPositionOrNull(): Position? =
    toIntOrNull()?.let { idx -> Position.values.getOrNull(idx) }



