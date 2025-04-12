package dev.tberghuis.twofactor.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import dev.tberghuis.twofactor.GLOBAL_PASSWORD
import dev.tberghuis.twofactor.sqldelight.AppDatabase
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountsRepository(private val db: AppDatabase) {

  @OptIn(ExperimentalUuidApi::class)
  fun createAccount(name: String, secret: String): Account {
    val uuid = Uuid.random().toString()
    val encryptedSecret = AESEncryption.encrypt(secret, GLOBAL_PASSWORD!!)
    db.accountQueries.saveAccount(uuid, name, encryptedSecret)
    return Account(uuid, name, secret)
  }

  fun updateAccount(id: String, name: String, secret: String) {
    val encryptedSecret = AESEncryption.encrypt(secret, GLOBAL_PASSWORD!!)
    db.accountQueries.saveAccount(id, name, encryptedSecret)
  }

  fun allAccountsFlow(): Flow<List<Account>> {
    return db.accountQueries.readAllAccount().asFlow().mapToList(Dispatchers.IO).map {
      it.map { accountTable ->
        val secret = AESEncryption.decrypt(accountTable.secret, GLOBAL_PASSWORD!!)
        Account(accountTable.id, accountTable.name, secret)
      }
    }
  }

  fun getAccount(id: String): Account {
    val at = db.accountQueries.selectById(id).executeAsOne()
    val secret = AESEncryption.decrypt(at.secret, GLOBAL_PASSWORD!!)
    return Account(at.id, at.name, secret)
  }

  fun deleteAccount(account: Account) {
    db.accountQueries.deleteAccount(account.id)
  }
}