package link.limecode.mywallet.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import link.limecode.mywallet.app.util.ErrorMessage
import link.limecode.mywallet.data.model.local.balance.Balance
import link.limecode.mywallet.data.model.local.preference.PreferenceEntity
import link.limecode.mywallet.domain.usecase.balance.GetBalanceListUseCase
import link.limecode.mywallet.domain.usecase.preference.GetFreeConversionUseCase

sealed interface HomeUIState {
    val isLoading: Boolean
    val isRefreshing: Boolean
    val errorMessages: List<ErrorMessage>

    data class IsLoaded(
        override val isLoading: Boolean,
        override val isRefreshing: Boolean,
        override val errorMessages: List<ErrorMessage>,

        val balanceList: Flow<List<Balance>>,
        val freeConversion: Flow<PreferenceEntity?>
    ) : HomeUIState

    data class IsFailed(
        override val isLoading: Boolean,
        override val isRefreshing: Boolean,
        override val errorMessages: List<ErrorMessage>
    ) : HomeUIState
}

private data class HomeViewModelState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isFailedToLoad: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val balanceList: Flow<List<Balance>> = emptyFlow(),
    val freeConversion: Flow<PreferenceEntity?> = emptyFlow()
) {

    fun toUiState(): HomeUIState =
        if (!isFailedToLoad) {
            HomeUIState.IsLoaded(
                isLoading = isLoading,
                isRefreshing = isRefreshing,
                errorMessages = errorMessages,
                balanceList = balanceList,
                freeConversion = freeConversion
            )
        } else {
            HomeUIState.IsFailed(
                isLoading = isLoading,
                isRefreshing = isRefreshing,
                errorMessages = errorMessages
            )
        }
}

class HomeViewModel(
    val getBalanceListUseCase: GetBalanceListUseCase,
    val getFreeConversionUseCase: GetFreeConversionUseCase
) : ViewModel() {

    private val viewModelState = MutableStateFlow(HomeViewModelState(isLoading = true))

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            viewModelState.update {
                it.copy(
                    isLoading = false,
                    isRefreshing = false,
                    balanceList = getBalanceListUseCase.invoke(),
                    freeConversion = getFreeConversionUseCase.invoke()
                )
            }
        }
    }

    companion object {
        fun provideFactory(
            getBalanceListUseCase: GetBalanceListUseCase,
            getFreeConversionUseCase: GetFreeConversionUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(
                    getBalanceListUseCase = getBalanceListUseCase,
                    getFreeConversionUseCase = getFreeConversionUseCase
                ) as T
            }
        }
    }
}

