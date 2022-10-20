package link.limecode.mywallet.presentation.currencypicker

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import link.limecode.mywallet.app.util.ErrorMessage
import link.limecode.mywallet.data.model.local.currency.Currency
import link.limecode.mywallet.domain.usecase.currency.GetCurrencyListUseCase

sealed interface CurrencyPickerUIState {
    val isLoading: Boolean
    val isRefreshing: Boolean
    val errorMessages: List<ErrorMessage>

    val searchTextFieldValue: TextFieldValue

    data class IsLoaded(
        override val isLoading: Boolean,
        override val isRefreshing: Boolean,
        override val errorMessages: List<ErrorMessage>,

        override val searchTextFieldValue: TextFieldValue,

        val currencyList: Flow<List<Currency>>
    ) : CurrencyPickerUIState

    data class IsFailed(
        override val isLoading: Boolean,
        override val isRefreshing: Boolean,
        override val errorMessages: List<ErrorMessage>,

        override val searchTextFieldValue: TextFieldValue
    ) : CurrencyPickerUIState
}

private data class CurrencyPickerViewModelState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isFailedToLoad: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),

    val searchTextFieldValue: TextFieldValue = TextFieldValue(),

    val currencyList: Flow<List<Currency>> = emptyFlow()
) {

    fun toUiState(): CurrencyPickerUIState =
        if (!isFailedToLoad) {
            CurrencyPickerUIState.IsLoaded(
                isLoading = isLoading,
                isRefreshing = isRefreshing,
                errorMessages = errorMessages,

                searchTextFieldValue = searchTextFieldValue,

                currencyList = currencyList
            )
        } else {
            CurrencyPickerUIState.IsFailed(
                isLoading = isLoading,
                isRefreshing = isRefreshing,
                errorMessages = errorMessages,

                searchTextFieldValue = searchTextFieldValue
            )
        }
}

class CurrencyPickerViewModel(
    val getCurrencyListUseCase: GetCurrencyListUseCase
) : ViewModel() {

    private val viewModelState = MutableStateFlow(CurrencyPickerViewModelState(isLoading = true))

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    private var loadCurrencyListJob : Job? = null

    init {
        load(keyword = "")
    }

    private fun load(keyword: String) {
        if (loadCurrencyListJob != null) {
            if (loadCurrencyListJob!!.isActive) {
                loadCurrencyListJob!!.cancel()
            }
        }

        loadCurrencyListJob = viewModelScope.launch {
            viewModelState.update {
                it.copy(
                    isLoading = false,
                    currencyList = getCurrencyListUseCase.invoke(
                        param = GetCurrencyListUseCase.Param(
                            keyword = keyword
                        )
                    )
                )
            }
        }
    }

    fun onSearchFieldValueChange(textFieldValue: TextFieldValue) {
        viewModelState.update {
            it.copy(
                searchTextFieldValue = textFieldValue
            )
        }

        load(keyword = textFieldValue.text)
    }

    companion object {
        fun provideFactory(
            getCurrencyListUseCase: GetCurrencyListUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CurrencyPickerViewModel(
                    getCurrencyListUseCase = getCurrencyListUseCase
                ) as T
            }
        }
    }
}
