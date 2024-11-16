package isel.leic.tds.ttt.model

import isel.leic.tds.storage.Storage

@JvmInline
value class Name(val value: String) {
    init {
        require(value.isNotEmpty() && value.all { it.isLetterOrDigit() }) { "Name not valid" }
    }
    override fun toString() = value
}

typealias GameStorage = Storage<Name, Game>

open class Clash(val storage: GameStorage)

class ClashRun(
    storage: GameStorage,
    val name: Name, val game: Game, val sidePlayer: Player
) : Clash(storage)

fun Clash.start(name: Name): Clash =
    ClashRun(storage, name, Game().newBoard(), Player.X)
    .also { storage.create(name, it.game) }

fun Clash.join(name: Name): Clash = ClashRun(
    storage, name,
    game = checkNotNull(storage.read(name)) { "Clash $name not found" },
    sidePlayer = Player.O
)

/**
 * Utility function to ensure the clash is a ClashRun and
 * return a new ClashRun with the game returned by the function getGame.
 * The getGame function is an extension of ClashRun.
 */
private fun Clash.onClashRun(getGame: ClashRun.() -> Game): Clash {
    check(this is ClashRun) { "There is no clash yet" }
    return ClashRun(storage, name, getGame(), sidePlayer)
}

fun Clash.refresh() = onClashRun {
    checkNotNull(storage.read(name))
    .also { check(it != game) { "No changes in clash $name" } }
}

fun Clash.play(pos: Position) = onClashRun {
    game.play(pos)
    .also {
        check((game.board as? BoardRun)?.turn == sidePlayer) { "Not your turn" }
        storage.update(name, it)
    }
}

fun Clash.newBoard() = onClashRun {
    game.newBoard()
    .also { storage.update(name, it) }
}