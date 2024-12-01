package isel.leic.tds.ttt.model

import isel.leic.tds.storage.Storage

@JvmInline
value class Name(private val value: String) {
    init {
        require(isValid(value)) { "Name not valid" }
    }
    override fun toString() = value
    companion object {
        fun isValid(txt: String) =
            txt.isNotEmpty() && txt.all { it.isLetterOrDigit() }
    }
}

typealias GameStorage = Storage<Name, Game>

open class Clash(val storage: GameStorage)

class ClashRun(
    storage: GameStorage,
    val name: Name, val game: Game, val sidePlayer: Player
) : Clash(storage)

private fun Clash.deleteIfOwner() {
    if (this is ClashRun && sidePlayer==Player.X)
        storage.delete(name)
}

fun Clash.start(name: Name): Clash =
    ClashRun(storage, name, Game().newBoard(), Player.X)
    .also {
        deleteIfOwner()
        storage.create(name, it.game)
    }

class ClashNotFound(name: Name): IllegalStateException("Clash $name not found")

private fun Clash.read(name: Name) =
    storage.read(name) ?: throw ClashNotFound(name)

fun Clash.join(name: Name): Clash =
    ClashRun(storage, name, read(name), Player.O)
        .also { deleteIfOwner() }

fun Clash.exit() {
    deleteIfOwner()
}

/**
 * Utility function to ensure the clash is a ClashRun and
 * return a new ClashRun with the game returned by the function getGame.
 * The getGame function is an extension of ClashRun.
 */
private fun Clash.onClashRun(getGame: ClashRun.() -> Game): Clash {
    check(this is ClashRun) { "There is no clash yet" }
    return ClashRun(storage, name, getGame(), sidePlayer)
}

class NoChangeException(name: Name): IllegalStateException("No changes in clash $name")

fun Clash.refresh() = onClashRun {
    read(name).also { if(it == game) throw NoChangeException(name) }
}

fun Clash.play(pos: Position) = onClashRun {
    check((game.board as? BoardRun)?.turn == sidePlayer) { "Not your turn" }
    game.play(pos).also { storage.update(name, it) }
}

fun Clash.newBoard() = onClashRun {
    game.newBoard().also { storage.update(name, it) }
}

val Clash.isSideTurn: Boolean get() = this is ClashRun &&
    sidePlayer == when (game.board) {
        is BoardRun -> game.board.turn
        else -> game.firstPlayer
    }