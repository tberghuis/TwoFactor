package dev.tberghuis.twofactor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.tberghuis.twofactor.screen.HomeScreen
import dev.tberghuis.twofactor.screen.LockScreen
import dev.tberghuis.twofactor.screen.AddAuthUrlScreen
import dev.tberghuis.twofactor.screen.Route
import androidx.navigation.toRoute
import dev.tberghuis.twofactor.screen.EditAccountScreen
import dev.tberghuis.twofactor.screen.EditTotpSecretScreenVm
import dev.tberghuis.twofactor.composable.TwoFactorTopBar

@Composable
fun App(
  navController: NavHostController = rememberNavController()
) {
  CompositionLocalProvider(LocalNavController provides navController) {
    MaterialTheme {
      Scaffold(
        modifier = Modifier,
        topBar = {
          TwoFactorTopBar()
        },
      ) { innerPadding ->
        NavHost(
          navController = navController,
          startDestination = Route.Lock,
          modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
          composable<Route.Lock> {
            LockScreen()
          }
          composable<Route.AccountList> {
            HomeScreen()
          }
          composable<Route.AddTotpSecret> {
            EditAccountScreen(
              EditTotpSecretScreenVm.getInstance(null)
            )
          }
          composable<Route.EditTotpSecret> {
            val id = it.toRoute<Route.EditTotpSecret>().id
            EditAccountScreen(
              EditTotpSecretScreenVm.getInstance(id)
            )
          }
          composable<Route.AddAuthUrl> {
            AddAuthUrlScreen()
          }
        }
      }
    }
  }
}