package link.limecode.mywallet.domain.usecase.stringresource

import link.limecode.mywallet.domain.repository.StringResourceRepository

class GetCurrencyFormatUseCase(
    private val stringResourceRepository: StringResourceRepository
) {

    fun invoke(): String {
        return stringResourceRepository.getCurrencyFormat()
    }
}

