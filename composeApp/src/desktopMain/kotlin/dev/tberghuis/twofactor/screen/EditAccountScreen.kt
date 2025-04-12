package dev.tberghuis.twofactor.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.tberghuis.twofactor.LocalNavController

@Composable
fun EditAccountScreen(
  vm: EditTotpSecretScreenVm
) {
  val nav = LocalNavController.current
  Column(
    modifier = Modifier,
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    TextField(
      value = vm.name,
      onValueChange = {
        vm.name = it
      },
      label = { Text("name") },
      singleLine = true,
    )
    TextField(
      value = vm.secret,
      onValueChange = {
        vm.secret = it
      },
      label = { Text("secret") },
      singleLine = true,
    )
    Button(onClick = {
      vm.save()
      nav.navigateUp()
    }) {
      Text("save")
    }
  }
}