package dev.tberghuis.twofactor.data

import dev.tberghuis.twofactor.sqldelight.AppDatabase

class OptionRepository(private val db: AppDatabase) {
  fun insertOption(key: String, value: String) {
    db.optionQueries.insertOption(key, value)
  }

  fun readOption(key: String): String? {
    return db.optionQueries.readOption(key).executeAsOneOrNull()
  }
}