package link.limecode.mywallet.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import link.limecode.mywallet.data.model.local.balance.Balance
import link.limecode.mywallet.data.model.local.balance.BalanceEntity
import link.limecode.mywallet.data.model.local.currency.CurrencyEntity

@Dao
interface BalanceDao : BaseDao<BalanceEntity> {
    @Query(
        "SELECT c.${CurrencyEntity.COLUMN_ID}, b.${BalanceEntity.COLUMN_ID} as ${BALANCE_ID}, " +
                "c.${CurrencyEntity.COLUMN_CURRENCY_NAME}, " +
                "b.${BalanceEntity.COLUMN_BALANCE} " +
                "FROM ${CurrencyEntity.TABLE_NAME} c LEFT JOIN ${BalanceEntity.TABLE_NAME} b ON " +
                "c.id = b.${BalanceEntity.COLUMN_CURRENCY_ID} " +
                "ORDER BY b.${BalanceEntity.COLUMN_BALANCE} DESC"
    )
    fun getBalanceList(): Flow<List<Balance>>

    @Query(
        "SELECT c.${CurrencyEntity.COLUMN_ID}, b.${BalanceEntity.COLUMN_ID} as ${BALANCE_ID}, " +
                "c.${CurrencyEntity.COLUMN_CURRENCY_NAME}, " +
                "b.${BalanceEntity.COLUMN_BALANCE} " +
                "FROM ${CurrencyEntity.TABLE_NAME} c LEFT JOIN ${BalanceEntity.TABLE_NAME} b ON " +
                "c.${CurrencyEntity.COLUMN_ID} = b.${BalanceEntity.COLUMN_CURRENCY_ID} " +
                "WHERE c.${CurrencyEntity.COLUMN_CURRENCY_NAME} = :currencyName " +
                "ORDER BY b.${BalanceEntity.COLUMN_BALANCE} DESC"
    )
    fun getBalance(currencyName: String): Flow<Balance?>

    companion object {
        const val BALANCE_ID = "balanceId"
    }
}

