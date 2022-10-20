package link.limecode.mywallet.data.model.local.balance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import link.limecode.mywallet.data.model.local.currency.CurrencyEntity

@Entity(
    tableName = BalanceEntity.TABLE_NAME,
    indices = [Index(BalanceEntity.COLUMN_ID), Index(BalanceEntity.COLUMN_CURRENCY_ID)],
    foreignKeys = [
        ForeignKey(
            entity = CurrencyEntity::class,
            parentColumns = [CurrencyEntity.COLUMN_ID],
            childColumns = [BalanceEntity.COLUMN_CURRENCY_ID]
        )
    ]
)
data class BalanceEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Int?,

    @ColumnInfo(name = COLUMN_CURRENCY_ID)
    val currencyId: Int,

    @ColumnInfo(name = COLUMN_BALANCE)
    val balance: Double
) {
    companion object {
        const val TABLE_NAME = "balance"
        const val COLUMN_ID = "id"
        const val COLUMN_CURRENCY_ID = "currency_id"
        const val COLUMN_BALANCE = "balance"
    }
}

