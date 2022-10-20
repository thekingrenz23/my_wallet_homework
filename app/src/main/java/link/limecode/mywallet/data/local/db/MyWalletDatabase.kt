package link.limecode.mywallet.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import link.limecode.mywallet.data.local.dao.BalanceDao
import link.limecode.mywallet.data.local.dao.CurrencyDao
import link.limecode.mywallet.data.local.dao.PreferenceDao
import link.limecode.mywallet.data.model.local.balance.BalanceEntity
import link.limecode.mywallet.data.model.local.currency.CurrencyEntity
import link.limecode.mywallet.data.model.local.preference.PreferenceEntity

@Database(
    entities = [CurrencyEntity::class, BalanceEntity::class, PreferenceEntity::class],
    version = 3,
    exportSchema = false
)
abstract class MyWalletDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun balanceDao(): BalanceDao
    abstract fun preferenceDao(): PreferenceDao

    companion object {
        const val DATABASE_NAME = "my_wallet"
    }
}
