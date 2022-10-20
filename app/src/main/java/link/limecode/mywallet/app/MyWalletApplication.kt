package link.limecode.mywallet.app

import android.app.Application
import link.limecode.mywallet.app.di.AppContainer
import link.limecode.mywallet.app.di.AppContainerImpl

class MyWalletApplication : Application() {

    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(applicationContext = this)
    }
}
