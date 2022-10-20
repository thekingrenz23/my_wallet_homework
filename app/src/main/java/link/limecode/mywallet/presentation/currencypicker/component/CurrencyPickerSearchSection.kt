package link.limecode.mywallet.presentation.currencypicker.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import link.limecode.mywallet.R
import link.limecode.mywallet.app.theme.LocalInsets
import link.limecode.mywallet.app.theme.MyWalletTheme

@Suppress("FunctionName")
@Composable
fun CurrencyPickerSearchSection(
    modifier: Modifier,
    textFieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    Column(
        modifier = modifier
            .padding(
                vertical = 10.dp,
                horizontal = LocalInsets.current.levelOneInset
            )
    ) {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.placeholder_search),
                    style = MaterialTheme.typography.body1
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(cursorColor = MaterialTheme.colors.secondary)
        )
    }
}

@Suppress("FunctionName")
@Preview(name = "Currency Picker Light")
@Preview(name = "Currency Picker Night", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewCurrencyPicker() {
    MyWalletTheme {
        Surface {
            CurrencyPickerSearchSection(
                modifier = Modifier.fillMaxSize(),
                textFieldValue = TextFieldValue(),
                onValueChange = {}
            )
        }
    }
}

