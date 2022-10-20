package link.limecode.mywallet.data.model.local.balance

import androidx.room.ColumnInfo
import link.limecode.mywallet.data.local.dao.BalanceDao
import link.limecode.mywallet.data.model.local.currency.CurrencyEntity

data class Balance(
    @ColumnInfo(name = CurrencyEntity.COLUMN_ID) val id: Int,
    @ColumnInfo(name = BalanceDao.BALANCE_ID) val balanceId: Int?,
    @ColumnInfo(name = CurrencyEntity.COLUMN_CURRENCY_NAME) val currencyName: String,
    @ColumnInfo(name = BalanceEntity.COLUMN_BALANCE) val balance: Double?
)

