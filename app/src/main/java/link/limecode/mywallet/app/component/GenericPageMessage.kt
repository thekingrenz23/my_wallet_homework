package link.limecode.mywallet.app.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import link.limecode.mywallet.R
import link.limecode.mywallet.app.theme.MyWalletTheme

@Suppress("FunctionName", "LongParameterList")
@Composable
fun GenericPageMessage(
    modifier: Modifier,
    icon: ImageVector,
    title: String,
    subTitle: String,
    showButton: Boolean,
    buttonLabel: String? = null,
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = title, style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = subTitle, style = MaterialTheme.typography.body1)
        if (showButton) {
            Spacer(modifier = Modifier.height(25.dp))
            Button(onClick = onClick ?: {}) {
                Text(text = buttonLabel ?: stringResource(id = R.string.button_default_label))
            }
        }
    }
}

@Suppress("FunctionName")
@Preview(name = "Generic Page Message Light")
@Preview(name = "Generic Page Message Night", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewGenericPageMessage() {
    MyWalletTheme {
        Surface {
            GenericPageMessage(
                modifier = Modifier.fillMaxSize(),
                icon = Icons.Default.Block,
                title = stringResource(id = R.string.ui_text_page_state_no_data_title),
                subTitle = stringResource(id = R.string.ui_text_page_state_no_data_subtitle),
                showButton = true,
                buttonLabel = stringResource(id = R.string.button_retry),
                onClick = {}
            )
        }
    }
}
