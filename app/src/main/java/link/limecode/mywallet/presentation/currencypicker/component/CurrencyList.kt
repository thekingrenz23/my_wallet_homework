package link.limecode.mywallet.presentation.currencypicker.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.SignalWifiBad
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.emptyFlow
import link.limecode.mywallet.R
import link.limecode.mywallet.app.component.GenericPageMessage
import link.limecode.mywallet.app.theme.MyWalletTheme
import link.limecode.mywallet.presentation.currencypicker.CurrencyPickerUIState

@Suppress("FunctionName")
@Composable
fun CurrencyList(
    listState: LazyListState,
    uiState: CurrencyPickerUIState,
    onClick: (String) -> Unit
) {
    when (uiState) {
        is CurrencyPickerUIState.IsLoaded -> {
            val currencyList by uiState.currencyList.collectAsState(initial = emptyList())

            BoxWithConstraints {
                val height = this.maxHeight
                val width = this.maxWidth

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = listState
                ) {
                    when {
                        currencyList.isNotEmpty() -> {
                            itemsIndexed(
                                items = currencyList,
                                key = { _, item ->
                                    item.id
                                }
                            ) { _, item ->
                                CurrencyListItem(
                                    name = item.currencyName,
                                    onClick = {
                                        onClick(item.currencyName)
                                    }
                                )
                            }
                        }
                        else -> {
                            item {
                                GenericPageMessage(
                                    modifier = Modifier
                                        .height(height)
                                        .width(width),
                                    icon = Icons.Default.Block,
                                    title = stringResource(id = R.string.ui_text_page_state_no_data_title),
                                    subTitle = stringResource(id = R.string.ui_text_page_state_no_data_subtitle),
                                    showButton = false
                                )
                            }
                        }
                    }
                }
            }
        }
        else -> {
            GenericPageMessage(
                modifier = Modifier
                    .fillMaxSize(),
                icon = Icons.Default.SignalWifiBad,
                title = stringResource(id = R.string.ui_text_page_state_failed_load_title),
                subTitle = stringResource(id = R.string.ui_text_page_state_failed_load_subtitle),
                showButton = true,
                buttonLabel = stringResource(id = R.string.button_retry),
                onClick = {}
            )
        }
    }
}

@Suppress("FunctionName")
@Preview(name = "Currency List Light")
@Preview(name = "Currency List Night", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewCurrencyList() {
    MyWalletTheme {
        Surface {
            CurrencyList(
                listState = rememberLazyListState(),
                uiState = CurrencyPickerUIState.IsLoaded(
                    isLoading = false,
                    isRefreshing = false,
                    errorMessages = emptyList(),
                    currencyList = emptyFlow(),
                    searchTextFieldValue = TextFieldValue()
                ),
                onClick = {}
            )
        }
    }
}

