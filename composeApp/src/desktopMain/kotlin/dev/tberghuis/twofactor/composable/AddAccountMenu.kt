package dev.tberghuis.twofactor.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tberghuis.twofactor.LocalNavController
import dev.tberghuis.twofactor.screen.Route

@Composable
fun AddAccountMenu() {
  val nav = LocalNavController.current
  var expanded by remember { mutableStateOf(false) }
  Box(
    modifier = Modifier
      .padding(16.dp)
  ) {
    IconButton(onClick = { expanded = !expanded }) {
      Icon(Icons.Default.Add, contentDescription = "add account")
    }
    DropdownMenu(
      expanded = expanded,
      onDismissRequest = { expanded = false }
    ) {
      DropdownMenuItem(
        onClick = { nav.navigate(Route.AddTotpSecret) }
      ) {
        Text("TOTP Secret")
      }
      DropdownMenuItem(
        onClick = { nav.navigate(Route.AddAuthUrl) }
      ) {
        Text("otpauth-migration:// url")
      }
      DropdownMenuItem(
        onClick = { /* Do something... */ }
      ) {
        Text("todo - QR code webcam")
      }
    }
  }
}