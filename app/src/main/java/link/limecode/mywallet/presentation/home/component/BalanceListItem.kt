package link.limecode.mywallet.presentation.home.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import link.limecode.mywallet.R
import link.limecode.mywallet.app.component.CurrencyLogo
import link.limecode.mywallet.app.theme.LocalInsets
import link.limecode.mywallet.app.theme.MyWalletTheme
import link.limecode.mywallet.app.theme.montserratFamily

@Suppress("FunctionName")
@Composable
fun BalanceListItem(
    name: String,
    balance: Double
) {
    Row(
        modifier = Modifier
            .height(75.dp)
            .fillMaxWidth()
            .padding(
                horizontal = LocalInsets.current.levelOneInset,
                vertical = 10.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CurrencyLogo(name = name)
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Medium
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(id = R.string.format_currency, balance),
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = montserratFamily
            )
        )
    }
}

@Suppress("FunctionName")
@Preview(name = "Balance List Item Section Light")
@Preview(name = "Balance List Item Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewBalanceItem() {
    MyWalletTheme {
        Surface {
            BalanceListItem(
                name = stringResource(id = R.string.placeholder_currency),
                balance = 100.00
            )
        }
    }
}
