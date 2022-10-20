package link.limecode.mywallet.domain.repository

interface SupportedCurrencyRepository {
    suspend fun getSupportedCurrencyList() : List<String>
}
