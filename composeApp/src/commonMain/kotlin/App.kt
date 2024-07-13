import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import kotlinx.coroutines.delay

enum class ColorMode {
    SYSTEM, LIGHT, DARK
}

val DarkColorPalette = darkColors(
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
    error = Color(0xFFFF0000),
    onError = Color(0xFFFFFFFF)
)

val LightColorPalette = lightColors(
    primary = Color(0xFF00FF00),
    primaryVariant = Color(0xFF00AA00),
    onPrimary = Color(0xFF000000),
    secondary = Color(0xFF0000FF),
    secondaryVariant = Color(0xFFAA0000),
    onSecondary = Color(0xFF000000),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF000000),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF000000),
    error = Color(0xFFFF0000),
    onError = Color(0xFF000000)
)

@Composable
fun rememberColorMode(): MutableState<ColorMode> {
    return remember { mutableStateOf(ColorMode.SYSTEM) }
}

@Composable
fun MyAppTheme(colorMode: ColorMode, content: @Composable () -> Unit) {
    val colors = when (colorMode) {
        ColorMode.SYSTEM -> if (isSystemInDarkTheme()) DarkColorPalette else LightColorPalette
        ColorMode.LIGHT -> LightColorPalette
        ColorMode.DARK -> DarkColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SettingsPage(colorMode: MutableState<ColorMode>, openDrawer: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Settings", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { colorMode.value = ColorMode.SYSTEM }) {
            Text("System")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { colorMode.value = ColorMode.LIGHT }) {
            Text("Light")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { colorMode.value = ColorMode.DARK }) {
            Text("Dark")
        }
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = openDrawer,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Menu")
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomePage(openDrawer: () -> Unit) {
    val currentTime = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime.value = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())
            delay(1000L)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Home", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Current Time:", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        Text(currentTime.value, style = MaterialTheme.typography.h3)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
@Preview
fun App() {
    val colorMode = rememberColorMode()
    val scaffoldState = rememberScaffoldState()

    MyAppTheme(colorMode.value) {
        val navController = rememberNavController()

        Scaffold(
            scaffoldState = scaffoldState,
            drawerBackgroundColor = MaterialTheme.colors.background,
            drawerContent = {
                Column {
                    Text(
                        text = "Menu",
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colors.onPrimary
                    )
                    Divider()
                    TextButton(onClick = { navController.navigate("home") }) {
                        Text("Home", color = MaterialTheme.colors.onPrimary)
                    }
                    TextButton(onClick = { navController.navigate("settings") }) {
                        Text("Settings", color = MaterialTheme.colors.onPrimary)
                    }
                }
            },
            topBar = {
                TopAppBar(
                    title = { Text("comboom.sucht", color = MaterialTheme.colors.onBackground) },
                    backgroundColor = MaterialTheme.colors.background,
                    navigationIcon = {
                        TextButton(onClick = {
                            scope.launch {
                                scaffoldState.drawerState.snapTo(DrawerValue.Open)
                            }
                        }) {
                            Text("Menu", color = MaterialTheme.colors.onBackground)
                        }
                    }

                )
            }
        ) {
            NavHost(navController, startDestination = "home") {
                composable("home") {
                    HomePage{ scope.launch {
                        scaffoldState.drawerState.snapTo(DrawerValue.Open)
                    } }
                }
                composable("settings") {
                    SettingsPage(colorMode, openDrawer = { scope.launch {
                        scaffoldState.drawerState.snapTo(DrawerValue.Open)
                    } })
                }
            }
        }
    }
}