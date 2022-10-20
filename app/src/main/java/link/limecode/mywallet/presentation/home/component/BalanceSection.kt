package link.limecode.mywallet.presentation.home.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.emptyFlow
import link.limecode.mywallet.R
import link.limecode.mywallet.app.theme.LocalInsets
import link.limecode.mywallet.app.theme.MyWalletTheme
import link.limecode.mywallet.presentation.home.HomeUIState

@Suppress("FunctionName")
@Composable
fun BalanceSection(
    modifier: Modifier,
    listState: LazyListState,
    uiState: HomeUIState
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = LocalInsets.current.levelOneInset,
                        vertical = 15.dp
                    )
            ) {
                Text(
                    text = stringResource(id = R.string.ui_text_balance_section),
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Divider()
            BalanceList(
                listState = listState,
                uiState = uiState
            )
        }
    }
}

@Suppress("FunctionName")
@Preview(name = "Balance section light")
@Preview(name = "Balance section night", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewBalanceSection() {
    MyWalletTheme {
        BalanceSection(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
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
