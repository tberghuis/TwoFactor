package dev.tberghuis.twofactor.composable

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import dev.tberghuis.twofactor.LocalNavController
import dev.tberghuis.twofactor.screen.Route
import androidx.navigation.NavDestination.Companion.hasRoute
import kotlin.reflect.KClass

@Composable
fun TwoFactorTopBar(
) {
  val nav = LocalNavController.current
  val destination = nav.currentBackStackEntryAsState().value?.destination ?: return
  when {
    destination.hierarchyHasRoute(Route.AccountList::class) -> {
      TopAppBar(
        title = { Text("Accounts") },
        modifier = Modifier,
        navigationIcon = {},
        actions = {
          AddAccountMenu()
        },
      )
    }

    destination.hierarchyHasRoute(Route.AddTotpSecret::class) -> {
      TopAppBar(
        title = { Text("Add Account") },
        navigationIcon = { NavBack() },
      )
    }

    destination.hierarchyHasRoute(Route.EditTotpSecret::class) -> {
      TopAppBar(
        title = { Text("Edit Account") },
        navigationIcon = { NavBack() },
      )
    }

    destination.hierarchyHasRoute(Route.Lock::class) -> {

    }

    destination.hierarchyHasRoute(Route.AddAuthUrl::class) -> {
      TopAppBar(
        title = { Text("Add Auth Url") },
        navigationIcon = {
          NavBack()
        },
      )
    }
  }
}

@Composable
fun NavBack() {
  val nav = LocalNavController.current
  IconButton(onClick = { nav.navigateUp() }) {
    Icon(
      imageVector = Icons.AutoMirrored.Filled.ArrowBack,
      contentDescription = "back"
    )
  }
}

fun <T : Any> NavDestination.hierarchyHasRoute(route: KClass<T>): Boolean {
  return hierarchy.any {
    it.hasRoute(route = route)
  } == true
}