package link.limecode.mywallet.presentation.currencyconversion

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import link.limecode.mywallet.BuildConfig
import link.limecode.mywallet.R
import link.limecode.mywallet.app.component.GenericPageMessage
import link.limecode.mywallet.app.component.LoadingDialog
import link.limecode.mywallet.app.theme.MyWalletTheme
import link.limecode.mywallet.app.util.MultipleEventsCutter
import link.limecode.mywallet.app.util.get
import link.limecode.mywallet.presentation.currencyconversion.component.CurrencyValueSection

@OptIn(ExperimentalComposeUiApi::class)
@Suppress("FunctionName", "LongMethod", "LongParameterList", "ComplexMethod")
@Composable
fun CurrencyConversionCompactScreen(
    scaffoldState: ScaffoldState,
    uiState: CurrencyConversionUIState,
    navBack: () -> Unit,
    navToCurrencyPicker: (String) -> Unit,
    showError: (id: Int) -> Unit,
    onErrorDismiss: (messageId: Long) -> Unit,
    reload: () -> Unit,
    fromCurrencyValueChange: (TextFieldValue) -> Unit,
    toCurrencyValueChange: (TextFieldValue) -> Unit,
    submit: () -> Unit,
    onConversionSuccess: (String) -> Unit,
    resetOnConversionSuccess: () -> Unit
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }

    val fromCurrencyFocusRequester = remember { FocusRequester() }
    val toCurrencyFocusRequester = remember { FocusRequester() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.button_convert))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            multipleEventsCutter.processEvent(navBack)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                uiState is CurrencyConversionUIState.IsFailed && !uiState.isLoading -> {
                    GenericPageMessage(
                        modifier = Modifier.fillMaxSize(),
                        icon = Icons.Default.Block,
                        title = stringResource(id = R.string.ui_text_page_state_failed_load_title),
                        subTitle = stringResource(id = R.string.ui_text_page_state_failed_load_subtitle),
                        showButton = true,
                        buttonLabel = stringResource(id = R.string.button_retry),
                        onClick = reload
                    )
                }
                else -> {
                    val fromSelectedCurrency by remember(uiState) {
                        derivedStateOf {
                            if (uiState !is CurrencyConversionUIState.IsLoaded) {
                                return@derivedStateOf null
                            }

                            return@derivedStateOf uiState.selectedCurrencyFrom.currencyName
                        }
                    }

                    val toSelectedCurrency by remember(uiState) {
                        derivedStateOf {
                            if (uiState !is CurrencyConversionUIState.IsLoaded) {
                                return@derivedStateOf null
                            }

                            return@derivedStateOf uiState.selectedCurrencyTo.currencyName
                        }
                    }

                    val remainingFreeConversion = when (uiState) {
                        is CurrencyConversionUIState.IsLoaded -> uiState.remainingFreeConversion.collectAsState(
                            initial = null
                        )
                        else -> null
                    }

                    ConversionSection(
                        padding = padding,

                        fromSelectedCurrency = fromSelectedCurrency,
                        fromOnClick = {
                            showError(
                                R.string.error_message_sell_currency
                            )
                        },
                        fromCurrencyFocusRequester = fromCurrencyFocusRequester,
                        fromCurrencyValueChange = fromCurrencyValueChange,

                        toSelectedCurrency = toSelectedCurrency,
                        toOnClick = {
                            multipleEventsCutter.processEvent {
                                navToCurrencyPicker(
                                    context.getString(R.string.ui_text_to_picker_title)
                                )
                            }
                        },
                        toCurrencyFocusRequester = toCurrencyFocusRequester,

                        fromCurrencyTextField = uiState.currencyFromTextField,
                        toCurrencyTextField = uiState.currencyToTextField,
                        toCurrencyValueChange = toCurrencyValueChange,
                        remainingFreeConversion = remainingFreeConversion?.value?.freeConversion
                            ?: 0,
                        commissionFee = BuildConfig.COMMISION_FEE_PERCENTAGE.toDoubleOrNull()
                            ?: 0.00
                    )
                    SubmitButton(
                        enable = uiState.currencyFromTextField.text.isNotEmpty()
                                && (uiState.currencyFromTextField.text.toDoubleOrNull()
                            ?: 0.00) > 0,
                        onClick = submit
                    )
                }
            }
        }
    }

    LoadingDialog(
        title = stringResource(id = R.string.ui_text_currency_conversion_loading),
        isShown = uiState.isLoading
    )

    LoadingDialog(
        title = stringResource(id = R.string.ui_text_converting),
        isShown = uiState.isConverting
    )

    LaunchedEffect(uiState.isConversionSuccessful) {
        if (uiState.isConversionSuccessful != null) {
            onConversionSuccess(uiState.isConversionSuccessful!!)
            resetOnConversionSuccess()
        }
    }

    @Suppress("MagicNumber")
    LaunchedEffect(uiState.isLoading) {
        if (!uiState.isLoading) {
            fromCurrencyFocusRequester.requestFocus()
            delay(100)
            keyboardController?.show()
        }
    }

    if (uiState.errorMessages.isNotEmpty()) {
        // Remember the errorMessage to display on the screen
        val errorMessage = remember(uiState) { uiState.errorMessages[0] }

        // Get the text to show on the message from resources
        val errorMessageText: String = stringResource(id = errorMessage.messageId)
        val errorId: Long = errorMessage.id

        // If onRefreshPosts or onErrorDismiss change while the LaunchedEffect is running,
        // don't restart the effect and use the latest lambda values.
        val onErrorDismissState by rememberUpdatedState(onErrorDismiss)

        LaunchedEffect(errorId, scaffoldState) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = errorMessageText
            )

            // Once the message is displayed and dismissed, notify the ViewModel
            onErrorDismissState(errorMessage.id)
        }
    }

}

@Suppress("FunctionName", "LongMethod", "LongParameterList")
@Composable
fun ConversionSection(
    padding: PaddingValues,

    fromSelectedCurrency: String?,
    fromOnClick: () -> Unit,
    fromCurrencyFocusRequester: FocusRequester,
    fromCurrencyTextField: TextFieldValue,
    fromCurrencyValueChange: (TextFieldValue) -> Unit,

    toSelectedCurrency: String?,
    toOnClick: () -> Unit,
    toCurrencyFocusRequester: FocusRequester,
    toCurrencyTextField: TextFieldValue,
    toCurrencyValueChange: (TextFieldValue) -> Unit,
    remainingFreeConversion: Int,
    commissionFee: Double
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(bottom = 70.dp)
            .verticalScroll(
                state = rememberScrollState()
            )
    ) {
        CurrencyValueSection(
            title = stringResource(id = R.string.ui_text_from_conversion),
            selectedCurrency = fromSelectedCurrency,
            onClick = fromOnClick,
            focusRequester = fromCurrencyFocusRequester,
            textFieldValue = fromCurrencyTextField,
            onValueChange = fromCurrencyValueChange,
            remainingFreeConversion = remainingFreeConversion,
            commissionFee = commissionFee
        )
        CurrencyValueSection(
            title = stringResource(id = R.string.ui_text_to_conversion),
            sectionBackground = MaterialTheme.colors.surface,
            color = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.onSecondary,
            editable = false,
            selectedCurrency = toSelectedCurrency,
            onClick = toOnClick,
            focusRequester = toCurrencyFocusRequester,
            textFieldValue = toCurrencyTextField,
            onValueChange = toCurrencyValueChange
        )
    }
}

@Suppress("FunctionName")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxScope.SubmitButton(
    enable: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .align(Alignment.BottomCenter),
        color = if (enable) MaterialTheme.colors.primary else Color.LightGray,
        contentColor = MaterialTheme.colors.onPrimary,
        enabled = enable
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.button_submit_conversion),
                style = MaterialTheme.typography.button.copy(fontSize = 17.sp),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Suppress("FunctionName")
@Preview(name = "Currency Conversion Compact Screen Light")
@Preview(name = "Currency Conversion Compact Screen Night", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewCurrencyConversionCompactScreen() {
    MyWalletTheme {
        CurrencyConversionCompactScreen(
            scaffoldState = rememberScaffoldState(),
            uiState = CurrencyConversionUIState.IsFailed(
                isLoading = false,
                isRefreshing = false,
                errorMessages = emptyList(),

                currencyToTextField = TextFieldValue(),
                currencyFromTextField = TextFieldValue(),
                isConverting = false,
                isConversionSuccessful = null
            ),
            navBack = {},
            navToCurrencyPicker = {},
            showError = {},
            onErrorDismiss = {},
            reload = {},
            fromCurrencyValueChange = {},
            toCurrencyValueChange = {},
            submit = {},
            onConversionSuccess = {},
            resetOnConversionSuccess = {}
        )
    }
}
