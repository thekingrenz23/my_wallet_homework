package link.limecode.mywallet.domain.repository

import kotlinx.coroutines.flow.Flow
import link.limecode.mywallet.data.model.local.preference.PreferenceEntity

interface PreferenceRepository {

    fun getFreeConversion(): Flow<PreferenceEntity?>

    suspend fun addFreeConversion(preferenceEntity: PreferenceEntity)

    suspend fun updateFreeConversion(preferenceEntity: PreferenceEntity)
}

