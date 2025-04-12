package dev.tberghuis.twofactor.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.tberghuis.twofactor.GLOBAL_PASSWORD
import dev.tberghuis.twofactor.optionRepository
import dev.tberghuis.twofactor.data.OptionRepository
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64

class LockScreenVm(
  private val optionRepository: OptionRepository
) : ViewModel() {
  var password by mutableStateOf("")

  private fun setPassword() {
    // todo check for invalid password

    val salt = generateSalt()
    optionRepository.insertOption("SALT", salt)
    val passwordHash = hashPassword(password, salt)
    optionRepository.insertOption("SALTED_PASSWORD_HASH", passwordHash)
  }

  fun unlock(onSuccess: () -> Unit) {
    val dbPasswordHash = optionRepository.readOption("SALTED_PASSWORD_HASH")
    if (dbPasswordHash == null) {
      setPassword()
      // todo navigate AccountList
      return
    }
    val salt = optionRepository.readOption("SALT")
    if (salt == null) {
      // todo error
      return
    }

    if (dbPasswordHash == hashPassword(password, salt)) {
      // todo success navigate AccountList
      GLOBAL_PASSWORD = password
      onSuccess()
    } else {
      // todo login fail
    }
  }

  companion object {
    @Composable
    fun getInstance(): LockScreenVm {
      return viewModel {
        LockScreenVm(optionRepository)
      }
    }
  }
}

fun generateSalt(length: Int = 16): String {
  val salt = ByteArray(length)
  SecureRandom().nextBytes(salt)
  return Base64.getEncoder().encodeToString(salt)
}

fun hashPassword(password: String, salt: String): String {
  val digest = MessageDigest.getInstance("SHA-256")
  val saltedPassword = salt + password
  val hash = digest.digest(saltedPassword.toByteArray(Charsets.UTF_8))
  return Base64.getEncoder().encodeToString(hash)
}