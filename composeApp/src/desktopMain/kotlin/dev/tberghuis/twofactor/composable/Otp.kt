package dev.tberghuis.twofactor.composable

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.buildAnnotatedString
import dev.turingcomplete.kotlinonetimepassword.GoogleAuthenticator
import dev.tberghuis.twofactor.screen.HomeScreenVm

@Composable
fun Otp(
  secret: String,
  vm: HomeScreenVm = HomeScreenVm.getInstance()
) {
  var otp by remember { mutableStateOf("") }
  LaunchedEffect(secret) {
    println("Otp LaunchedEffect secret $secret")
    val authenticator = GoogleAuthenticator(secret.toByteArray())
    try {
      otp = authenticator.generate()
      vm.generateOtpSharedFlow.collect {
        println("generateOtpSharedFlow.collect")
        otp = authenticator.generate()
      }
    } catch (e: IllegalArgumentException) {
      println(e)
      otp = "error"
    }
  }
  val clipboardManager = LocalClipboardManager.current
  Text(
    text = otp,
    modifier = Modifier.clickable {
      clipboardManager.setText(annotatedString = buildAnnotatedString {
        append(text = otp)
      })
    }
  )
}