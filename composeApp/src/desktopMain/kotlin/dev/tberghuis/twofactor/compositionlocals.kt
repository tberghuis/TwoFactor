package dev.tberghuis.twofactor

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = staticCompositionLocalOf<NavHostController> {
  error("CompositionLocal LocalNavController not present")
}