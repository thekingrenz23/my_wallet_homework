package link.limecode.mywallet.data.model.remote.currencyexchangerateapi

data class CurrencyExchangeRate(
    val base: String,
    val date: String,
    val rates: Rates
)

