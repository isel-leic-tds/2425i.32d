package isel.leic.tds.tennis.fp

import isel.leic.tds.tennis.*
import isel.leic.tds.tennis.Points.*

class Score(
    val placard: String,
    val isGame: Boolean = false,  // default parameter
    val next: (win: Player) -> Score
)

private fun game(winner: Player) = Score("Game $winner",true){ error("Is over") }

private fun advantage(player: Player) = Score("Advantage $player") { win ->
    if (win==player) game(win) else deuce()
}

private fun deuce(): Score = Score("Deuce", next= ::advantage)

private fun byPoints(pointsA: Points, pointsB: Points): Score = Score(
    placard = "${pointsA.value} - ${pointsB.value}",
    next = { win ->
        fun pointsOf(p: Player) = if (p==Player.A) pointsA else pointsB
        when {
            pointsOf(win) == THIRTY -> forty(win, pointsOf(win.other()))
            win == Player.A -> byPoints( pointsA.next(), pointsB )
            else -> byPoints( pointsA, pointsB.next())
        }
    }
)

private fun forty(player: Player, pointsOther: Points): Score = Score(
    placard = if (player==Player.A) "40 - ${pointsOther.value}"
              else "${pointsOther.value} - 40"
) { when {
        it == player -> game(it)
        pointsOther == THIRTY -> deuce()
        else -> forty(player, pointsOther.next())
} }

val InitialScore = byPoints(LOVE,LOVE)

