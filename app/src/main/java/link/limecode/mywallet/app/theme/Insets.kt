package link.limecode.mywallet.app.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Insets(
    val levelOneInset: Dp = 15.dp
)

val LocalInsets = compositionLocalOf { Insets() }
