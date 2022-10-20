package link.limecode.mywallet.domain.usecase.preference

import link.limecode.mywallet.data.model.local.preference.PreferenceEntity
import link.limecode.mywallet.domain.repository.PreferenceRepository

class AddFreeConversionUseCase(
    private val preferenceRepository: PreferenceRepository
) {

    suspend operator fun invoke(preferenceEntity: PreferenceEntity) {
        preferenceRepository.addFreeConversion(preferenceEntity = preferenceEntity)
    }
}

