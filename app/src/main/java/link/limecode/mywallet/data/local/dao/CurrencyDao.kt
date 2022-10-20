package link.limecode.mywallet.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import link.limecode.mywallet.data.model.local.currency.Currency
import link.limecode.mywallet.data.model.local.currency.CurrencyEntity

@Dao
interface CurrencyDao : BaseDao<CurrencyEntity> {

    @Query("SELECT * FROM ${CurrencyEntity.TABLE_NAME} " +
            "WHERE ${CurrencyEntity.COLUMN_CURRENCY_NAME} LIKE '%' || :keyword || '%'")
    fun getCurrencyList(keyword: String): Flow<List<Currency>>

    @Query("SELECT * FROM ${CurrencyEntity.TABLE_NAME} WHERE currency_name = :currencyName")
    fun getCurrency(currencyName: String): Flow<Currency?>
}

