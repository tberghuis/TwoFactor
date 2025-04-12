package dev.tberghuis.twofactor.screen

import kotlinx.serialization.Serializable

sealed interface Route {
  @Serializable
  data object Lock : Route
  @Serializable
  data object AccountList : Route
  @Serializable
  data object AddAuthUrl : Route

  @Serializable
  data object AddTotpSecret : Route
  @Serializable
  data class EditTotpSecret(val id: String) : Route
}