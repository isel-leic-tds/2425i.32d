package isel.leic.tds.ttt.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import isel.leic.tds.ttt.model.*

@Composable
fun StatusBar(board: Board?) {
    Row(
        modifier = Modifier.width(GRID_SIDE).background(Color.LightGray),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val (state, player) = when (board) {
            is BoardRun -> "Turn: " to board.turn
            is BoardWin -> "Winner: " to board.winner
            is BoardDraw -> "Draw" to null
            null -> "No board" to null
        }
        Text(state, fontSize = 32.sp)
        player?.let { Player(it, modifier = Modifier.size(32.dp)) }
    }
}

@Composable
@Preview
fun StatusBarTest() = StatusBar(Board())