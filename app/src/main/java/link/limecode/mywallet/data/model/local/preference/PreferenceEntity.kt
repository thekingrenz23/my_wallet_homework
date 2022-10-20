package link.limecode.mywallet.data.model.local.preference

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = PreferenceEntity.TABLE_NAME,
    indices = [Index(PreferenceEntity.COLUMN_ID)]
)
data class PreferenceEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Int?,

    @ColumnInfo(name = COLUMN_FREE_CONVERSION)
    val freeConversion: Int
) {
    companion object {
        const val TABLE_NAME = "preference"
        const val COLUMN_ID = "id"
        const val COLUMN_FREE_CONVERSION = "free_conversion"
    }
}
