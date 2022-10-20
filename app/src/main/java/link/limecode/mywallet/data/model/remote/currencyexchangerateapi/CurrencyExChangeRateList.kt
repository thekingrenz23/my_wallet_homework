package link.limecode.mywallet.data.model.remote.currencyexchangerateapi

data class CurrencyExChangeRateList(
    val base: String,
    val currencyList: List<Currency>
)

