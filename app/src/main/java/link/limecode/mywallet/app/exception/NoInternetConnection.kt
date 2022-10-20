package link.limecode.mywallet.app.exception

class NoInternetConnection(message: String? = null, cause: Throwable? = null) :
    Exception(message, cause) {
    constructor(cause: Throwable) : this(null, cause)
}
