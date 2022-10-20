package link.limecode.mywallet.presentation.currencyconversion.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import link.limecode.mywallet.R
import link.limecode.mywallet.app.theme.LocalCustomColors
import link.limecode.mywallet.app.theme.LocalInsets
import link.limecode.mywallet.app.theme.MyWalletTheme

@Suppress("FunctionName", "LongParameterList", "LongMethod")
@Composable
fun CurrencyValueSection(
    title: String,
    textFieldValue: TextFieldValue = TextFieldValue(),
    sectionBackground: Color = if (isSystemInDarkTheme()) {
        LocalCustomColors.current.surfaceElevated
    } else {
        LocalCustomColors.current.dirtyWhite
    },
    color: Color = MaterialTheme.colors.primaryVariant,
    contentColor: Color = MaterialTheme.colors.onPrimary,
    editable: Boolean = true,
    selectedCurrency: String?,
    onClick: () -> Unit,
    focusRequester: FocusRequester,
    onValueChange: (TextFieldValue) -> Unit,
    remainingFreeConversion: Int? = null,
    commissionFee: Double? = null
) {
    Row(
        Modifier
            .heightIn(min = 200.dp)
            .fillMaxWidth()
            .background(sectionBackground)
            .padding(
                horizontal = LocalInsets.current.levelOneInset,
                vertical = 30.dp
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .height(70.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (selectedCurrency != null) {
                    if (selectedCurrency.isNotEmpty()) {
                        SelectedCurrency(
                            currencyName = selectedCurrency,
                            onClick = onClick,
                            color = color,
                            contentColor = contentColor
                        )
                    }
                }
                BasicTextField(
                    value = textFieldValue,
                    onValueChange = onValueChange,
                    textStyle = MaterialTheme.typography.h4.copy(textAlign = TextAlign.End, color = MaterialTheme.colors.onSurface),
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester),
                    decorationBox = { textField ->
                        CurrencyConversionTextFieldDecoration(
                            textField = textField,
                            textFieldValue = textFieldValue
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    readOnly = !editable,
                    cursorBrush = SolidColor(MaterialTheme.colors.secondary),
                    singleLine = true
                )
            }
            if (remainingFreeConversion != null && commissionFee != null) {
                Spacer(modifier = Modifier.height(20.dp))
                val commissionText = buildAnnotatedString {
                    append(stringResource(id = R.string.ui_text_commission_fee_label))
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        if (remainingFreeConversion > 0) {
                            append(" $remainingFreeConversion ")
                        } else {
                            append(" ${stringResource(id = R.string.format_currency, commissionFee)}% ")
                        }
                    }
                    if (remainingFreeConversion > 0) {
                        append(stringResource(id = R.string.ui_text_commission_post_value))
                    }
                }
                Text(text = commissionText, style = MaterialTheme.typography.subtitle2)
            }
        }
    }
}

@Suppress("FunctionName")
@Composable
fun CurrencyConversionTextFieldDecoration(
    textField: @Composable () -> Unit,
    textFieldValue: TextFieldValue
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (textFieldValue.text.isEmpty()) {
            Text(
                text = "0",
                style = MaterialTheme.typography.h4.copy(
                    textAlign = TextAlign.End,
                    color = Color.Gray
                ),
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            textField()
        }
    }
}

@Suppress("FunctionName")
@Preview(name = "From Conversion Light")
@Preview(name = "From Conversion Night", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewFromConversion() {
    MyWalletTheme {
        Surface {
            CurrencyValueSection(
                title = stringResource(id = R.string.ui_text_from_conversion),
                selectedCurrency = stringResource(id = R.string.placeholder_currency),
                onClick = {},
                focusRequester = remember { FocusRequester() },
                textFieldValue = TextFieldValue(),
                onValueChange = {},
                remainingFreeConversion = 2,
                commissionFee = 20.00
            )
        }
    }
}
