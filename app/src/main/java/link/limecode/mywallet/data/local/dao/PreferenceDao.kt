package link.limecode.mywallet.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import link.limecode.mywallet.data.model.local.preference.PreferenceEntity

@Dao
interface PreferenceDao : BaseDao<PreferenceEntity> {

    @Query("SELECT * FROM ${PreferenceEntity.TABLE_NAME} WHERE id = 1")
    fun getFreeConversion(): Flow<PreferenceEntity?>
}
