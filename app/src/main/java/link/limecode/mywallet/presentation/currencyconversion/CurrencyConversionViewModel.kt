package link.limecode.mywallet.presentation.currencyconversion

import androidx.annotation.StringRes
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import java.util.UUID
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import link.limecode.mywallet.BuildConfig
import link.limecode.mywallet.R
import link.limecode.mywallet.app.exception.NoInternetConnection
import link.limecode.mywallet.app.util.ErrorMessage
import link.limecode.mywallet.app.util.FormUtil
import link.limecode.mywallet.app.util.Result
import link.limecode.mywallet.data.model.local.balance.BalanceEntity
import link.limecode.mywallet.data.model.local.preference.PreferenceEntity
import link.limecode.mywallet.data.model.remote.currencyexchangerateapi.CurrencyExChangeRateList
import link.limecode.mywallet.domain.usecase.balance.GetBalanceUseCase
import link.limecode.mywallet.domain.usecase.balance.SaveBalanceUseCase
import link.limecode.mywallet.domain.usecase.balance.UpdateBalanceUseCase
import link.limecode.mywallet.domain.usecase.currencyexchangerate.GetCurrencyExchangeRateUseCase
import link.limecode.mywallet.domain.usecase.preference.GetFreeConversionUseCase
import link.limecode.mywallet.domain.usecase.preference.UpdateFreeConversionUseCase
import link.limecode.mywallet.domain.usecase.stringresource.GetCurrencyFormatUseCase

data class SelectedCurrency(
    val currencyName: String,
    val exchangeRate: Double
)

sealed interface CurrencyConversionUIState {
    val isLoading: Boolean
    val isRefreshing: Boolean
    val errorMessages: List<ErrorMessage>
    val currencyFromTextField: TextFieldValue
    val currencyToTextField: TextFieldValue
    val isConverting: Boolean
    val isConversionSuccessful: String?

    data class IsLoaded(
        override val isLoading: Boolean,
        override val isRefreshing: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val currencyFromTextField: TextFieldValue,
        override val currencyToTextField: TextFieldValue,
        override val isConverting: Boolean,
        override val isConversionSuccessful: String?,

        val currencyExchangeRate: CurrencyExChangeRateList,
        val selectedCurrencyFrom: SelectedCurrency,
        val selectedCurrencyTo: SelectedCurrency,

        val remainingFreeConversion: Flow<PreferenceEntity?>
    ) : CurrencyConversionUIState

    data class IsFailed(
        override val isLoading: Boolean,
        override val isRefreshing: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val currencyFromTextField: TextFieldValue,
        override val currencyToTextField: TextFieldValue,
        override val isConverting: Boolean,
        override val isConversionSuccessful: String?
    ) : CurrencyConversionUIState
}

private data class CurrencyConversionViewModelState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isFailedToLoad: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val currencyExchangeRate: CurrencyExChangeRateList? = null,
    val selectedCurrencyFrom: SelectedCurrency? = null,
    val selectedCurrencyTo: SelectedCurrency? = null,

    val currencyFromTextField: TextFieldValue = TextFieldValue(),
    val currencyToTextField: TextFieldValue = TextFieldValue(),

    val remainingFreeConversion: Flow<PreferenceEntity?> = emptyFlow(),
    val isConverting: Boolean = false,
    val isConversionSuccessful: String? = null
) {

    fun toUiState(): CurrencyConversionUIState =
        @Suppress("ComplexCondition")
        if (!isFailedToLoad && currencyExchangeRate != null
            && selectedCurrencyFrom != null && selectedCurrencyTo != null
        ) {
            CurrencyConversionUIState.IsLoaded(
                isLoading = isLoading,
                isRefreshing = isRefreshing,
                errorMessages = errorMessages,
                currencyExchangeRate = currencyExchangeRate,
                selectedCurrencyFrom = selectedCurrencyFrom,
                selectedCurrencyTo = selectedCurrencyTo,
                currencyFromTextField = currencyFromTextField,
                currencyToTextField = currencyToTextField,
                remainingFreeConversion = remainingFreeConversion,
                isConverting = isConverting,
                isConversionSuccessful = isConversionSuccessful
            )
        } else {
            CurrencyConversionUIState.IsFailed(
                isLoading = isLoading,
                isRefreshing = isRefreshing,
                errorMessages = errorMessages,
                currencyFromTextField = currencyFromTextField,
                currencyToTextField = currencyToTextField,
                isConverting = isConverting,
                isConversionSuccessful = isConversionSuccessful
            )
        }
}

@Suppress("LongParameterList")
class CurrencyConversionViewModel(
    val getCurrencyExchangeRateUseCase: GetCurrencyExchangeRateUseCase,
    val getCurrencyFormatUseCase: GetCurrencyFormatUseCase,
    val getFreeConversionUseCase: GetFreeConversionUseCase,
    val getBalanceUseCase: GetBalanceUseCase,
    val updateBalanceUseCase: UpdateBalanceUseCase,
    val updateFreeConversionUseCase: UpdateFreeConversionUseCase,
    val saveBalanceUseCase: SaveBalanceUseCase,
    var initialCurrencyTo: String? = null
) : ViewModel() {

    private val viewModelState =
        MutableStateFlow(CurrencyConversionViewModelState(isLoading = true))

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    private var computeConversionJob: Job? = null

    init {
        load(toCurrency = null)
    }

    @Suppress("LongMethod")
    fun load(toCurrency: String?) {
        viewModelScope.launch {
            viewModelState.update {
                it.copy(
                    isLoading = true,
                    isRefreshing = false,
                    isFailedToLoad = false,
                    errorMessages = emptyList(),
                    currencyExchangeRate = null,
                    currencyFromTextField = TextFieldValue(),
                    currencyToTextField = TextFieldValue(),
                    remainingFreeConversion = emptyFlow()
                )
            }
            when (val getCurrencyExchangeRate = getCurrencyExchangeRateUseCase.invoke()) {
                is Result.Success -> {
                    val selectedCurrencyTo = SelectedCurrency(
                        currencyName = when (toCurrency) {
                            null -> getCurrencyExchangeRate.data.currencyList[0].currencyName
                            else -> {
                                val filtered = getCurrencyExchangeRate.data
                                    .currencyList.filter { it.currencyName == toCurrency }
                                if (filtered.isNotEmpty()) {
                                    filtered[0].currencyName
                                } else {
                                    getCurrencyExchangeRate.data.currencyList[0].currencyName
                                }
                            }
                        },
                        exchangeRate = when (toCurrency) {
                            null -> getCurrencyExchangeRate.data.currencyList[0].rate ?: 0.0
                            else -> {
                                val filtered = getCurrencyExchangeRate
                                    .data.currencyList.filter { it.currencyName == toCurrency }
                                if (filtered.isNotEmpty()) {
                                    filtered[0].rate ?: 0.0
                                } else {
                                    getCurrencyExchangeRate.data.currencyList[0].rate ?: 0.0
                                }
                            }
                        }
                    )

                    viewModelState.update {
                        it.copy(
                            isLoading = false,
                            currencyExchangeRate = getCurrencyExchangeRate.data,
                            selectedCurrencyFrom = SelectedCurrency(
                                currencyName = getCurrencyExchangeRate.data.base,
                                exchangeRate = 1.0
                            ),
                            selectedCurrencyTo = selectedCurrencyTo,
                            remainingFreeConversion = getFreeConversionUseCase.invoke()
                        )
                    }
                    computeConversion()
                }
                is Result.Error -> {
                    when (getCurrencyExchangeRate.exception) {
                        is NoInternetConnection -> showError(messageId = R.string.error_message_no_internet)
                        else -> showError(messageId = R.string.error_message_something_went_wrong)
                    }

                    viewModelState.update {
                        it.copy(
                            isLoading = false,
                            isFailedToLoad = true
                        )
                    }
                }
            }
        }
    }

    fun fromCurrencyValueChange(value: TextFieldValue) {
        viewModelScope.launch {
            if (!FormUtil.isValidCurrency(value.text)) {
                return@launch
            }

            if (value.text != viewModelState.value.currencyFromTextField.text) {
                viewModelState.update {
                    it.copy(
                        currencyFromTextField = value
                    )
                }
            }

            computeConversion()
        }
    }

    fun toCurrencyValueChange(value: TextFieldValue) {
        viewModelScope.launch {
            viewModelState.update {
                it.copy(
                    currencyToTextField = value
                )
            }
        }
    }

    private fun computeConversion() {
        if (computeConversionJob != null) {
            if (computeConversionJob!!.isActive) {
                computeConversionJob!!.cancel()
            }
        }

        computeConversionJob = viewModelScope.launch {
            if (viewModelState.value.currencyFromTextField.text.isEmpty()) {
                toCurrencyValueChange(value = viewModelState.value.currencyToTextField.copy(text = ""))
                return@launch
            }

            if (viewModelState.value.selectedCurrencyTo == null) {
                showError(R.string.error_message_something_went_wrong)
                return@launch
            }

            if (viewModelState.value.selectedCurrencyTo!!.exchangeRate <= 0) {
                showError(R.string.error_message_something_went_wrong)
                return@launch
            }

            val times = viewModelState.value.currencyFromTextField.text.toDouble()
            val rate = viewModelState.value.selectedCurrencyTo!!.exchangeRate

            toCurrencyValueChange(
                value =
                viewModelState.value.currencyToTextField.copy(
                    text = String.format(getCurrencyFormatUseCase.invoke(), times * rate)
                )
            )
        }
    }

    fun updateSelectedCurrencyTo(currency: String) {
        initialCurrencyTo = currency
        load(toCurrency = currency)
    }

    @Suppress("MagicNumber", "LongMethod", "ComplexMethod")
    fun submit() {
        viewModelScope.launch {
            viewModelState.update {
                it.copy(
                    isConverting = true
                )
            }

            if (viewModelState.value.selectedCurrencyFrom == null
                || viewModelState.value.selectedCurrencyTo == null
            ) {
                viewModelState.update {
                    it.copy(
                        isConverting = false
                    )
                }
                showError(R.string.error_message_something_went_wrong)
                return@launch
            }

            val commissionFeePercentage =
                BuildConfig.COMMISION_FEE_PERCENTAGE.toDoubleOrNull() ?: 0.00
            val fromCurrencyQuantity =
                viewModelState.value.currencyFromTextField.text.toDoubleOrNull() ?: 0.00
            val getFreeConversion =
                getFreeConversionUseCase.invoke().first()
            val remainingFreeConversion = getFreeConversion?.freeConversion ?: 0
            val commissionFee = (commissionFeePercentage * fromCurrencyQuantity) / 100

            var requiredBalance = fromCurrencyQuantity
            if (remainingFreeConversion == 0) {
                requiredBalance += commissionFee
            }

            val getBalance =
                getBalanceUseCase.invoke(
                    param =
                    GetBalanceUseCase.Param(
                        currencyName = viewModelState.value.selectedCurrencyFrom!!.currencyName
                    )
                )

            val balance = getBalance.first()
            val hasEnoughBalance = when (balance) {
                null -> false
                else -> {
                    val remainingBalance = balance.balance ?: 0.00
                    remainingBalance >= requiredBalance
                }
            }

            if (!hasEnoughBalance) {
                viewModelState.update {
                    it.copy(
                        isConverting = false
                    )
                }
                showError(R.string.error_message_not_enough_balance)
                return@launch
            }

            if (balance == null) {
                viewModelState.update {
                    it.copy(
                        isConverting = false
                    )
                }
                showError(R.string.error_message_something_went_wrong)
                return@launch
            }

            val fromNewBalance = BalanceEntity(
                id = balance.balanceId,
                balance = (balance.balance ?: 0.00) - requiredBalance,
                currencyId = balance.id
            )

            updateBalanceUseCase.invoke(balanceEntity = fromNewBalance)

            var isFreeConversion = false
            if (remainingFreeConversion > 0 && getFreeConversion != null) {
                updateFreeConversionUseCase.invoke(
                    preferenceEntity = PreferenceEntity(
                        id = getFreeConversion.id,
                        freeConversion = remainingFreeConversion - 1
                    )
                )
                isFreeConversion = true
            }

            val getBalanceOfToConverted = getBalanceUseCase.invoke(
                param = GetBalanceUseCase.Param(
                    currencyName = viewModelState.value.selectedCurrencyTo!!.currencyName
                )
            ).first()

            if (getBalanceOfToConverted == null) {
                viewModelState.update {
                    it.copy(
                        isConverting = false
                    )
                }
                showError(R.string.error_message_something_went_wrong)
                return@launch
            }

            val newBalanceOfToConverted = BalanceEntity(
                id = getBalanceOfToConverted.balanceId,
                currencyId = getBalanceOfToConverted.id,
                balance = (getBalanceOfToConverted.balance ?: 0.00) +
                        (viewModelState.value.currencyToTextField.text.toDoubleOrNull() ?: 0.00)
            )

            if (getBalanceOfToConverted.balanceId != null) {
                updateBalanceUseCase.invoke(balanceEntity = newBalanceOfToConverted)
            } else {
                saveBalanceUseCase.invoke(balanceEntity = newBalanceOfToConverted)
            }

            viewModelState.update {
                it.copy(
                    isConverting = false,
                    isConversionSuccessful = "You have converted " +
                            "${
                                String.format(
                                    getCurrencyFormatUseCase.invoke(),
                                    fromCurrencyQuantity
                                )
                            } ${viewModelState.value.selectedCurrencyFrom!!.currencyName} to " +
                            "${
                                String.format(
                                    getCurrencyFormatUseCase.invoke(),
                                    viewModelState.value.currencyToTextField.text
                                        .toDoubleOrNull() ?: 0.00
                                )
                            } " +
                            "${viewModelState.value.selectedCurrencyTo!!.currencyName}. " +
                            "Commission Fee - ${
                                if (isFreeConversion) "Free" else "${
                                    String.format(
                                        getCurrencyFormatUseCase.invoke(),
                                        commissionFee
                                    )
                                } ${viewModelState.value.selectedCurrencyFrom!!.currencyName}."
                            }"
                )
            }
        }
    }

    fun resetIsConversionSuccessful() {
        viewModelScope.launch {
            viewModelState.update {
                it.copy(
                    isConversionSuccessful = null
                )
            }
        }
    }

    fun showError(@StringRes messageId: Int) {
        viewModelScope.launch {
            viewModelState.update {
                val errorMessages = it.errorMessages + ErrorMessage(
                    id = UUID.randomUUID().mostSignificantBits,
                    messageId = messageId
                )
                it.copy(errorMessages = errorMessages)
            }
        }
    }

    fun errorShown(errorId: Long) {
        viewModelState.update { currentUiState ->
            val errorMessages = currentUiState.errorMessages.filterNot { it.id == errorId }
            currentUiState.copy(errorMessages = errorMessages)
        }
    }

    companion object {

        @Suppress("LongParameterList")
        fun provideFactory(
            getCurrencyExchangeRateUseCase: GetCurrencyExchangeRateUseCase,
            getCurrencyFormatUseCase: GetCurrencyFormatUseCase,
            getFreeConversionUseCase: GetFreeConversionUseCase,
            getBalanceUseCase: GetBalanceUseCase,
            updateBalanceUseCase: UpdateBalanceUseCase,
            updateFreeConversionUseCase: UpdateFreeConversionUseCase,
            saveBalanceUseCase: SaveBalanceUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CurrencyConversionViewModel(
                    getCurrencyExchangeRateUseCase = getCurrencyExchangeRateUseCase,
                    getCurrencyFormatUseCase = getCurrencyFormatUseCase,
                    getFreeConversionUseCase = getFreeConversionUseCase,
                    getBalanceUseCase = getBalanceUseCase,
                    updateBalanceUseCase = updateBalanceUseCase,
                    updateFreeConversionUseCase = updateFreeConversionUseCase,
                    saveBalanceUseCase = saveBalanceUseCase
                ) as T
            }
        }
    }
}
