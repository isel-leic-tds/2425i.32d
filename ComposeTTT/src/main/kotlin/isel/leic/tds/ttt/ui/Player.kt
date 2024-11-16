package isel.leic.tds.ttt.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import isel.leic.tds.ttt.model.Player

@Composable
fun Player(
    player: Player?,
    modifier: Modifier = Modifier.size(100.dp),
    onClick: (()->Unit)? = null
) {
    if (player == null) {
        Box(
            modifier = onClick?.let { modifier.clickable(onClick = it) } ?: modifier
        )
    } else {
        val file = when (player) {
            Player.X -> "cross"
            Player.O -> "circle"
        }
        Image(
            painter = painterResource("$file.png"),
            contentDescription = file,
            modifier = modifier
        )
    }
}

@Composable
@Preview
fun PlayerViewTest() {
    Row {
        Player(Player.X)
        Player(Player.O)
    }
}