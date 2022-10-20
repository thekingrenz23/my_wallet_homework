package link.limecode.mywallet.domain.usecase.preference

import link.limecode.mywallet.data.model.local.preference.PreferenceEntity
import link.limecode.mywallet.domain.repository.PreferenceRepository

class UpdateFreeConversionUseCase(
    private val preferenceRepository: PreferenceRepository
) {

    suspend fun invoke(preferenceEntity: PreferenceEntity) {
        preferenceRepository.updateFreeConversion(preferenceEntity = preferenceEntity)
    }
}
