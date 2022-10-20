package link.limecode.mywallet.data.repository

import android.content.Context
import link.limecode.mywallet.R
import link.limecode.mywallet.domain.repository.StringResourceRepository

class StringResourceRepositoryResImpl(
    private  val context: Context
) : StringResourceRepository {

    override fun getCurrencyFormat(): String {
        return context.resources.getString(R.string.format_currency)
    }
}
