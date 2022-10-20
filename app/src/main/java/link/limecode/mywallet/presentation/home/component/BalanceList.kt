package link.limecode.mywallet.presentation.home.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.emptyFlow
import link.limecode.mywallet.R
import link.limecode.mywallet.app.component.GenericPageMessage
import link.limecode.mywallet.app.theme.MyWalletTheme
import link.limecode.mywallet.presentation.home.HomeUIState

@Suppress("FunctionName")
@Composable
fun BalanceList(
    listState: LazyListState,
    uiState: HomeUIState
) {
    when (uiState) {
        is HomeUIState.IsLoaded -> {
            val balanceList by uiState.balanceList.collectAsState(initial = emptyList())

            BoxWithConstraints {
                val height = this.maxHeight
                val width = this.maxWidth

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = listState
                ) {
                    when {
                        balanceList.isNotEmpty() -> {
                            itemsIndexed(
                                items = balanceList,
                                key = { _, item ->
                                    item.id
                                }
                            ) { _, item ->
                                BalanceListItem(
                                    name = item.currencyName,
                                    balance = item.balance ?: 0.00
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
@Preview(name = "Balance List Light")
@Preview(name = "Balance List Night", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewBalanceList() {
    MyWalletTheme {
        Surface {
            BalanceList(
                listState = rememberLazyListState(),
                uiState = HomeUIState.IsLoaded(
                    isLoading = false,
                    isRefreshing = false,
                    errorMessages = emptyList(),
                    balanceList = emptyFlow(),
                    freeConversion = emptyFlow()
                )
            )
        }
    }
}
