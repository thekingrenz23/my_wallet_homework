package link.limecode.mywallet.data.model.local.currency

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = CurrencyEntity.TABLE_NAME,
    indices = [Index(CurrencyEntity.COLUMN_ID)]
)
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Int?,

    @ColumnInfo(name = COLUMN_CURRENCY_NAME)
    val currencyName: String
) {
    companion object {
        const val TABLE_NAME = "currency"
        const val COLUMN_ID = "id"
        const val COLUMN_CURRENCY_NAME = "currency_name"
    }
}

