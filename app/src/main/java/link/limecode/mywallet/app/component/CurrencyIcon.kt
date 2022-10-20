package link.limecode.mywallet.app.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import link.limecode.mywallet.R
import link.limecode.mywallet.app.theme.MyWalletTheme

@Suppress("FunctionName")
@Composable
fun CurrencyLogo(
    name: String,
    size: Dp = 45.dp,
    background: Color = MaterialTheme.colors.secondary
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(size)
            .background(background)
            .padding(4.dp)
    ) {
        Text(
            text = name.first().toString(),
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.onSecondary)
        )
    }
}

@Suppress("FunctionName")
@Preview(name = "Currency Logo Light")
@Preview(name = "Currency Logo Night", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewCurrencyLogo() {
    MyWalletTheme {
        CurrencyLogo(
            name = stringResource(id = R.string.placeholder_currency)
        )
    }
}
