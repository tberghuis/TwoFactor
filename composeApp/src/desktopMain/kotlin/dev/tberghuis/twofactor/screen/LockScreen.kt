package dev.tberghuis.twofactor.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import dev.tberghuis.twofactor.LocalNavController

@Composable
fun LockScreen(
  vm: LockScreenVm = LockScreenVm.getInstance()
) {
  val nav = LocalNavController.current

  fun unlock() {
    vm.unlock {
      nav.navigate(Route.AccountList) {
        popUpTo(Route.Lock) {
          inclusive = true
        }
      }
    }
  }

  Column(
    modifier = Modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    TextField(
      value = vm.password,
      onValueChange = { vm.password = it },
      modifier = Modifier.onKeyEvent { keyEvent ->
        if (keyEvent.key != Key.Enter) return@onKeyEvent false
        if (keyEvent.type == KeyEventType.KeyUp) {
          println("Enter released")
          unlock()
        }
        true
      },
      label = { Text("Enter password") },
      isError = false,
      visualTransformation = PasswordVisualTransformation(),
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
      keyboardActions = KeyboardActions(
        onDone = { println("on done") }
      ),
      singleLine = true,
    )
    Button(onClick = {
      unlock()
    }) {
      // if password is set --set password
      Text("set password / unlock")
    }
  }
}