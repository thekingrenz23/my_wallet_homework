package link.limecode.mywallet.app.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val DarkColorPalette = darkColors(
    primary = Brand,
    primaryVariant = BrandVariant,
    secondary = BrandComplementary,
    secondaryVariant = BrandComplementaryVariant,
    onPrimary = White,
    onSecondary = White,
    surface = Black
)

private val LightColorPalette = lightColors(
    primary = Brand,
    primaryVariant = BrandVariant,
    secondary = BrandComplementary,
    secondaryVariant = BrandComplementaryVariant,
    onPrimary = White,
    onSecondary = White,
    surface = White
)

@Suppress("FunctionName")
@Composable
fun MyWalletTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(
        LocalCustomColors provides CustomColors(),
        LocalInsets provides Insets()
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}
