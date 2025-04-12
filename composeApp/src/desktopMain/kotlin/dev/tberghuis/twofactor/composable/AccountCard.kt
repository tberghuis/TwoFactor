package dev.tberghuis.twofactor.composable

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tberghuis.twofactor.data.Account
import dev.tberghuis.twofactor.screen.HomeScreenVm
import dev.tberghuis.twofactor.LocalNavController
import dev.tberghuis.twofactor.screen.Route

@Composable
fun AccountCard(
  account: Account,
  vm: HomeScreenVm = HomeScreenVm.getInstance()
) {
  val nav = LocalNavController.current
  Card {
    Row(
      modifier = Modifier,
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(account.name)
      Otp(account.secret)
      IconButton(
        onClick = { nav.navigate(Route.EditTotpSecret(account.id)) },
      ) {
        Icon(
          imageVector = Icons.Filled.Edit,
          contentDescription = "edit"
        )
      }
      IconButton(
        onClick = { vm.deleteAccount(account) },
      ) {
        Icon(
          imageVector = Icons.Filled.Delete,
          contentDescription = "add"
        )
      }
    }
  }
}