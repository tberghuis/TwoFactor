package dev.tberghuis.twofactor

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.compose.reload.DevelopmentEntryPoint

fun main() = application {
  val state = rememberWindowState(
    // during dev, todo get position from envvar
//    position = WindowPosition.Absolute(2291.0.dp, 306.0.dp),
  )
  Window(
    onCloseRequest = ::exitApplication,
    state = state,
    title = "TwoFactor",
  ) {
    DevelopmentEntryPoint {
      App()
    }
  }
}