package link.limecode.mywallet.presentation.home.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import link.limecode.mywallet.R
import link.limecode.mywallet.app.theme.LocalInsets
import link.limecode.mywallet.app.theme.MyWalletTheme
import link.limecode.mywallet.app.theme.montserratFamily

@Suppress("FunctionName")
@Composable
fun UserSection(
    modifier: Modifier,
    onConvertClick: () -> Unit,
    freeConversionCount: Int
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            UserInfoSection(
                freeConversionCount = freeConversionCount
            )
            UserActionSection(
                onConvertClick = onConvertClick
            )
        }
    }
}

@Suppress("FunctionName")
@Composable
fun ColumnScope.UserInfoSection(
    freeConversionCount: Int
) {
    Row(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .padding(horizontal = LocalInsets.current.levelOneInset),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(75.dp),
            bitmap = ImageBitmap.imageResource(id = R.drawable.user),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.ui_text_welcome),
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                text = stringResource(id = R.string.placeholder_username),
                style = MaterialTheme.typography.h6.copy(fontFamily = montserratFamily)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null
                )
            }
            Text(text = freeConversionCount.toString())
        }
    }
}

@Suppress("FunctionName", "MagicNumber")
@Composable
fun UserActionSection(
    onConvertClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(90.dp)
            .fillMaxWidth()
            .padding(horizontal = LocalInsets.current.levelOneInset, vertical = 17.dp)
    ) {
        Button(
            onClick = onConvertClick,
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(50)
        ) {
            Text(
                text = stringResource(id = R.string.button_convert),
                style = MaterialTheme.typography.button.copy(fontSize = 17.sp)
            )
        }
    }
}

@Suppress("FunctionName")
@Preview(name = "User Section Light")
@Preview(name = "User Section Night", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewUserSection() {
    MyWalletTheme {
        Surface {
            UserSection(
                modifier = Modifier.height(200.dp),
                onConvertClick = {},
                freeConversionCount = 9
            )
        }
    }
}
