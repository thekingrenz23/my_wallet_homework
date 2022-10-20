package link.limecode.mywallet.domain.usecase.supportedcurrency

import link.limecode.mywallet.domain.repository.SupportedCurrencyRepository

class GetSupportedCurrencyListUseCase(
    private val supportedCurrencyRepository: SupportedCurrencyRepository
) {

    suspend fun invoke(): List<String> {
        return supportedCurrencyRepository.getSupportedCurrencyList()
    }
}

