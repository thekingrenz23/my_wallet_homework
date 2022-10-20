package link.limecode.mywallet.data.model.local.currency

import androidx.room.ColumnInfo

data class Currency(
    @ColumnInfo(name = CurrencyEntity.COLUMN_ID)
    val id: Int,

    @ColumnInfo(name = CurrencyEntity.COLUMN_CURRENCY_NAME)
    val currencyName: String
)

