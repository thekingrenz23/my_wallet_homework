package link.limecode.mywallet.data.repository

import kotlinx.coroutines.flow.Flow
import link.limecode.mywallet.data.local.dao.PreferenceDao
import link.limecode.mywallet.data.model.local.preference.PreferenceEntity
import link.limecode.mywallet.domain.repository.PreferenceRepository

class PreferenceRepositoryRoomImpl(
    private val dao: PreferenceDao
) : PreferenceRepository {

    override fun getFreeConversion(): Flow<PreferenceEntity?> {
        return dao.getFreeConversion()
    }

    override suspend fun addFreeConversion(preferenceEntity: PreferenceEntity) {
        dao.insert(data = preferenceEntity)
    }

    override suspend fun updateFreeConversion(preferenceEntity: PreferenceEntity) {
        dao.update(data = preferenceEntity)
    }
}


