package lu.lumpenstein.luxradios.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
//    primaryVariant = Purple700,
//    secondary = Teal200,
    background = Jet,
    onBackground = GoldenrodYellowLight,
    surface = JetLigth,
    onSurface = LaurelGreen
)

private val LightColorPalette = lightColors(
    primary = Jet,
//    primaryVariant = Purple700,
//    secondary = Teal200,
    background = CambridgeBlue,
    onBackground = Jet,
    surface = GoldenrodYellowLight,
    onSurface = Jet

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun LuxRadiosTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}