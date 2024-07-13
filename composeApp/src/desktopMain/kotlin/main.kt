import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "comboom.sucht",
        focusable = true,
        undecorated = false
    ) {
        App()
    }
}