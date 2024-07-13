import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import comboom_sucht_app.composeapp.generated.resources.Res
import comboom_sucht_app.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    val darkColors = darkColors(
        primary = Color(0xFF00FF00),
        primaryVariant = Color(0xFF00AA00),
        onPrimary = Color(0xFF000000),
        secondary = Color(0xFF0000FF),
        secondaryVariant = Color(0xFFAA0000),
        onSecondary = Color(0xFF000000),
        background = Color(0xFF000000),
        onBackground = Color(0xFFFFFFFF),
        surface = Color(0xFF000000),
        onSurface = Color(0xFFFFFFFF),
        onError = Color(0xFFFF0000)
    )

    val lightColors = lightColors(
        primary = Color(0xFF00FF00),
        primaryVariant = Color(0xFF00AA00),
        onPrimary = Color(0xFF000000),
        secondary = Color(0xFF0000FF),
        secondaryVariant = Color(0xFFAA0000),
        onSecondary = Color(0xFF000000),
        background = Color(0xFF000000),
        onBackground = Color(0xFFFFFFFF),
        surface = Color(0xFF000000),
        onSurface = Color(0xFFFFFFFF),
        onError = Color(0xFFFF0000)
    )

    val colors = if (isSystemInDarkTheme()) darkColors else lightColors

    MaterialTheme(colors = colors) {

        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)) {
                Text("Click me!", color = MaterialTheme.colors.onPrimary)
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}