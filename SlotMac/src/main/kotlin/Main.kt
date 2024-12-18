import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import model.SLOTS
import model.SlotState
import view.SlotMachineViewModel
import view.toSlotImageFilename

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Slot Machine",
        state = WindowState(size = DpSize(400.dp, 300.dp))
    )
    { MaterialTheme { SlotMachineApp() } }
}
@Composable
fun SlotMachineApp() {
    val slotMachineViewModel = remember { SlotMachineViewModel() }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlayerInputDetails(
            { slotMachineViewModel.playerName },
            { slotMachineViewModel.playerName = it }
        )
        SlotMachine(
            { slotMachineViewModel.slotState },
            slotMachineViewModel::isPlayerNameValid,
            slotMachineViewModel::play
        )
        ResultPanel(slotMachineViewModel)
    }
}
@Composable
fun PlayerInputDetails(nameGetter: () -> String, nameSetter: (String) -> Unit) {
    TextField(nameGetter(), nameSetter)
}
@Composable
fun SlotMachine(slotState: () -> SlotState, isEnabled: () -> Boolean, play: () -> Unit) {
    Row {
        repeat(SLOTS) {
            Image(painterResource(slotState().slots[it].toSlotImageFilename()), null,
                modifier = Modifier.size(100.dp).padding(10.dp))
        }
    }
    Button(onClick = play, enabled = isEnabled()) { Text("Play")}
}
@Composable
fun ResultPanel(slotMachineViewModel: SlotMachineViewModel) {
    val name = slotMachineViewModel.playerName
    Text(when {
        !slotMachineViewModel.isPlayerNameValid() -> "Insert your name and press play!"
        slotMachineViewModel.isWinner() -> "Congratulations, $name. You won!"
        else -> "Better luck next time, $name!"
    })
}
