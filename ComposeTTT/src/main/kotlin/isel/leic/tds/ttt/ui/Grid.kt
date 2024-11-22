package isel.leic.tds.ttt.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import isel.leic.tds.ttt.model.*

val CELL_SIDE = 120.dp
val LINE_WIDTH = 10.dp
val GRID_SIDE = CELL_SIDE * BOARD_DIM + LINE_WIDTH * (BOARD_DIM-1)

@Composable
fun Grid(board: Board?, onClickCell: (Position)->Unit ) {
    Column(
        Modifier.size(GRID_SIDE).background(Color.Black),
        Arrangement.SpaceBetween
    ) {
        repeat(BOARD_DIM) { row ->
            Row(
                Modifier.fillMaxWidth(),
                Arrangement.SpaceBetween
            ) {
                repeat(BOARD_DIM) { col ->
                    val pos = Position(row * BOARD_DIM + col)
                    Player(
                        player = board?.let { it.moves[pos] },
                        Modifier.size(CELL_SIDE).background(Color.White),
                        onClick = { onClickCell(pos) }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun GridPreview() {
    val board = Board().play(Position(4)).play(Position(0))
    Grid(board, {})
}