package dev.tberghuis.twofactor.screen

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.tberghuis.twofactor.accountsRepository
import dev.tberghuis.twofactor.data.Account
import dev.tberghuis.twofactor.data.AccountsRepository
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeScreenVm(private val accountsRepository: AccountsRepository) : ViewModel() {
  val countdown = MutableStateFlow<Long?>(null)
  val accountList: MutableStateFlow<List<Account>> = MutableStateFlow(listOf())
  val generateOtpSharedFlow = MutableSharedFlow<Unit>()

  init {
    println("HomeScreenVm init $HomeScreenVm")

    viewModelScope.launch {
      // is there a better idiomatic kotlin way of doing this?
      accountsRepository.allAccountsFlow().collect {
        accountList.value = it
      }
    }

    viewModelScope.launch {
      val timer = Timer()
      val wait = 1000 - (System.currentTimeMillis() % 1000)
      timer.scheduleAtFixedRate(wait, 1000) {
        countdown.value = 30 - ((System.currentTimeMillis() / 1000) % 30)
      }
    }

    viewModelScope.launch {
      countdown.collect {
        if (it == 30L) {
          generateOtpSharedFlow.emit(Unit)
          println("generateOtpSharedFlow.emit(Unit)")
        }
      }
    }
  }

  fun deleteAccount(account: Account) {
    accountsRepository.deleteAccount(account)
  }

  companion object {
    @Composable
    fun getInstance(): HomeScreenVm {
      return viewModel {
        HomeScreenVm(accountsRepository)
      }
    }
  }
}