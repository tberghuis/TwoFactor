package dev.tberghuis.twofactor.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.eygraber.uri.Uri
import dev.tberghuis.twofactor.proto.MigrationPayload
import dev.tberghuis.twofactor.data.AccountsRepository
import io.matthewnelson.encoding.base32.Base32
import io.matthewnelson.encoding.core.Encoder.Companion.encodeToString
import java.util.Base64

class AddAuthUrlScreenVm(
  private val accountsRepository: AccountsRepository
) : ViewModel() {
  var authUrl by mutableStateOf("")
  fun save() {
    // todo deal with invalid url

    println("authUrl $authUrl")
    val uri = Uri.parse(authUrl)
    val data = uri.getQueryParameter("data") ?: return
    val bytes = Base64.getDecoder().decode(data)
    val payload = MigrationPayload.ADAPTER.decode(bytes)
    println("payload $payload")
    payload.otp_parameters.forEach { param ->
      println("param $param")
      val name = "issuer=${param.issuer}, name=${param.name}"
      val secretBase64 = param.secret.base64()
      val secretBytes = Base64.getDecoder().decode(secretBase64)
      val secret = secretBytes.encodeToString(Base32.Default)
      println("secret $secret")
      accountsRepository.createAccount(name, secret)
    }
  }
}