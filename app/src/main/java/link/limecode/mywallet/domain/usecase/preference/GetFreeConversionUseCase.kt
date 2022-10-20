package link.limecode.mywallet.domain.usecase.preference

import kotlinx.coroutines.flow.Flow
import link.limecode.mywallet.data.model.local.preference.PreferenceEntity
import link.limecode.mywallet.domain.repository.PreferenceRepository

class GetFreeConversionUseCase(
    private val preferenceRepository: PreferenceRepository
) {

    fun invoke(): Flow<PreferenceEntity?> {
        return preferenceRepository.getFreeConversion()
    }
}

