package link.limecode.mywallet.presentation.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.emptyFlow
import link.limecode.mywallet.R
import link.limecode.mywallet.app.component.GenericDialog
import link.limecode.mywallet.app.theme.MyWalletTheme
import link.limecode.mywallet.app.util.MultipleEventsCutter
import link.limecode.mywallet.app.util.get
import link.limecode.mywallet.presentation.home.component.BalanceSection
import link.limecode.mywallet.presentation.home.component.UserSection

@Suppress("FunctionName")
@Composable
fun HomeCompactScreen(
    scaffoldState: ScaffoldState,
    uiState: HomeUIState,
    navToCurrencyConversion: () -> Unit,
    backStackConversionMessage: String?,
    clearBackStackConversionMessage: () -> Unit
) {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }
    var conversionMessage by rememberSaveable { mutableStateOf<String?>(null) }
    val listState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        }
    ) { padding ->
        val freeConversionCount = when (uiState) {
            is HomeUIState.IsLoaded -> uiState.freeConversion.collectAsState(initial = null)
            else -> null
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            UserSection(
                modifier = Modifier.weight(1f),
                onConvertClick = {
                    multipleEventsCutter.processEvent(navToCurrencyConversion)
                },
                freeConversionCount = freeConversionCount?.value?.freeConversion ?: 0
            )
            Spacer(modifier = Modifier.height(20.dp))
            BalanceSection(
                modifier = Modifier.weight(2f),
                listState = listState,
                uiState = uiState
            )
        }
    }

    GenericDialog(
        visible = conversionMessage != null,
        icon = Icons.Default.Check,
        title = stringResource(id = R.string.ui_text_success_conversion_title),
        body = conversionMessage ?: "",
        onDismiss = {
            conversionMessage = null
        },
        actionLabel = stringResource(id = R.string.button_default_label)
    )

    LaunchedEffect(backStackConversionMessage) {
        if (backStackConversionMessage != null) {
            conversionMessage = backStackConversionMessage
            clearBackStackConversionMessage()
        }
    }
}

@Suppress("FunctionName")
@Preview(name = "Home Compact Screen Light")
@Preview(name = "Home Compact Screen Night", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewHomeCompactScreen() {
    MyWalletTheme {
        HomeCompactScreen(
            scaffoldState = rememberScaffoldState(),
            navToCurrencyConversion = {},
            uiState = HomeUIState.IsLoaded(
                isLoading = false,
                isRefreshing = false,
                errorMessages = emptyList(),
                balanceList = emptyFlow(),
                freeConversion = emptyFlow()
            ),
            backStackConversionMessage = null,
            clearBackStackConversionMessage = {}
        )
    }
}

