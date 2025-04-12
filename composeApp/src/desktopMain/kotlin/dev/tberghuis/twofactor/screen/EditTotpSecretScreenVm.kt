package dev.tberghuis.twofactor.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.tberghuis.twofactor.accountsRepository
import dev.tberghuis.twofactor.data.AccountsRepository

class EditTotpSecretScreenVm(
  // id == null - Add Totp secret screen
  private val id: String?,
  private val accountsRepository: AccountsRepository
) : ViewModel() {

  var name by mutableStateOf("")
  var secret by mutableStateOf("")

  init {
    if (id != null) {
      accountsRepository.getAccount(id).also {
        name = it.name
        secret = it.secret
      }
    }
  }

  fun save() {
    when (id == null) {
      true -> {
        accountsRepository.createAccount(name, secret)
      }

      false -> {
        accountsRepository.updateAccount(id, name, secret)
      }
    }
  }

  companion object {
    @Composable
    fun getInstance(id: String?): EditTotpSecretScreenVm {
      return viewModel {
        EditTotpSecretScreenVm(id, accountsRepository)
      }
    }
  }
}