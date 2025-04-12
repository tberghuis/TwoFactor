package dev.tberghuis.twofactor

import dev.tberghuis.twofactor.data.createDriver
import dev.tberghuis.twofactor.sqldelight.AppDatabase
import dev.tberghuis.twofactor.data.AccountsRepository
import dev.tberghuis.twofactor.data.OptionRepository

val appDatabase = AppDatabase(createDriver())
val accountsRepository = AccountsRepository(appDatabase)
val optionRepository = OptionRepository(appDatabase)

// doitwrong
// future typesafe nav with query param password???
var GLOBAL_PASSWORD: String? = null
