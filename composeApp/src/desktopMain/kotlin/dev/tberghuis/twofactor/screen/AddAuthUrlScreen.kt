package dev.tberghuis.twofactor.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.tberghuis.twofactor.accountsRepository
import dev.tberghuis.twofactor.LocalNavController

@Composable
fun AddAuthUrlScreen(
  vm: AddAuthUrlScreenVm = viewModel {
    AddAuthUrlScreenVm(accountsRepository)
  }
) {
  val nav = LocalNavController.current

  Column(
    modifier = Modifier,
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text("add auth url")
    TextField(
      value = vm.authUrl,
      onValueChange = { vm.authUrl = it },
      modifier = Modifier,
      label = { Text("migration url") },
      placeholder = { Text("otpauth-migration://offline?data=...") },
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