@file:Suppress("MagicNumber")
package link.limecode.mywallet.app.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val Brand = Color(0xFF645CAA)
val BrandVariant = Color(0xFF4F488B)
val BrandComplementary = Color(0xFFA2AA5C)
val BrandComplementaryVariant = Color(0xFF5CAA8B)

val White = Color(0xFFFFFFFF)
val DirtyWhite = Color(0xFFEDE9E8)
val Black = Color(0xFF121212)

val SurfaceElevated = Color(0xFF282828)

data class CustomColors(
    val surfaceElevated: Color = SurfaceElevated,
    val dirtyWhite: Color = DirtyWhite
)
val LocalCustomColors = compositionLocalOf { CustomColors() }
