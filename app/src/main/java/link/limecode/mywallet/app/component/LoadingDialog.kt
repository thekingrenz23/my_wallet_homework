package link.limecode.mywallet.app.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import link.limecode.mywallet.R
import link.limecode.mywallet.app.theme.MyWalletTheme

@Suppress("FunctionNaming")
@Composable
fun LoadingDialog(
    title: String,
    isShown: Boolean
) {
    if (isShown) {

        Dialog(
            onDismissRequest = {}
        ) {
            LoadingDialogLayout(title = title)
        }
    }
}

@Suppress("FunctionNaming")
@Composable
fun LoadingDialogLayout(
    title: String
) {
    Surface(
        modifier = Modifier.padding(
            horizontal = 10.dp,
            vertical = 5.dp
        ),
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 25.dp,
                    vertical = 25.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.secondary
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 15.dp)
            ) {
                Text(text = title)
            }
        }
    }
}

@Suppress("FunctionNaming")
@Preview(name = "Loading Dialog Light")
@Preview(name = "Loading Dialog Night", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewLoadingDialog() {
    MyWalletTheme {
        LoadingDialog(
            title = stringResource(id = R.string.ui_text_currency_conversion_loading),
            isShown = true
        )
    }
}

