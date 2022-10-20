package link.limecode.mywallet.data.repository

import android.content.Context
import link.limecode.mywallet.R
import link.limecode.mywallet.domain.repository.SupportedCurrencyRepository

class SupportedCurrencyRepositoryResourceImpl(
    private val context: Context
) : SupportedCurrencyRepository {

    override suspend fun getSupportedCurrencyList(): List<String> {
        return context.resources.getStringArray(R.array.supported_currency).toList()
    }
}

