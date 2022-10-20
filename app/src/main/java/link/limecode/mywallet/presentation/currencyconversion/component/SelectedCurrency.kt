package link.limecode.mywallet.presentation.currencyconversion.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import link.limecode.mywallet.R
import link.limecode.mywallet.app.component.CurrencyLogo
import link.limecode.mywallet.app.theme.MyWalletTheme

@Suppress("FunctionName")
@Composable
fun SelectedCurrency(
    currencyName: String,
    onClick: () -> Unit,
    color: Color,
    contentColor: Color
) {
    val textWithInlineIcon = buildAnnotatedString {
        append("$currencyName ")
        appendInlineContent("navToCurrencySelection", "[icon]")
    }

    val inlineContent = mapOf(
        Pair(
            "navToCurrencySelection",
            InlineTextContent(
                Placeholder(
                    width = 23.sp,
                    height = 23.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Default.ChevronRight),
                    contentDescription = null
                )
            }
        )
    )

    Surface(
        modifier = Modifier
            .clip(CircleShape)
            .clickable(
                onClick = onClick
            ),
        color = color,
        contentColor = contentColor
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CurrencyLogo(
                name = currencyName,
                size = 30.dp,
                background = if (color == MaterialTheme.colors.primaryVariant) {
                    MaterialTheme.colors.secondary
                } else {
                    MaterialTheme.colors.primaryVariant
                }
            )
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = textWithInlineIcon,
                inlineContent = inlineContent,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Suppress("FunctionName")
@Preview(name = "Selected Currency Light")
@Preview(name = "Selected Currency Night", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewSelectedCurrency() {
    MyWalletTheme {
        SelectedCurrency(
            currencyName = stringResource(id = R.string.placeholder_currency),
            onClick = {},
            color = MaterialTheme.colors.primaryVariant,
            contentColor = MaterialTheme.colors.onPrimary
        )
    }
}
