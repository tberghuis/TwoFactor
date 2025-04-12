package dev.tberghuis.twofactor.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.tberghuis.twofactor.sqldelight.AppDatabase
import java.io.File
import me.sujanpoudel.utils.paths.appDataDirectory

fun createDriver(): SqlDriver {
  val dbFile = File("${appDataDirectory("dev.tberghuis.twofactor")}/twofactor.db")
  dbFile.parentFile.mkdirs()
  val dbExists: Boolean = dbFile.exists()
  val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:${dbFile.absolutePath}")
  if (!dbExists) {
    AppDatabase.Schema.create(driver)
  }
  return driver
}