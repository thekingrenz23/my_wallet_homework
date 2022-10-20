package link.limecode.mywallet.presentation.currencypicker.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
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

@OptIn(ExperimentalMaterialApi::class)
@Suppress("FunctionName")
@Composable
fun CurrencyListItem(
    name: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .height(75.dp)
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
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
        }
    }
}

@Suppress("FunctionName")
@Preview(name = "Currency List Item Light")
@Preview(name = "Currency List Item Night", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewCurrencyListItem() {
    MyWalletTheme {
        Surface {
            CurrencyListItem(
                name = stringResource(id = R.string.placeholder_currency),
                onClick = {}
            )
        }
    }
}

