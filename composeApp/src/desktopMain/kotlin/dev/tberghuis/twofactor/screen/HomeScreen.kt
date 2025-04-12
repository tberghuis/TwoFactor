package dev.tberghuis.twofactor.screen

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.tberghuis.twofactor.composable.AccountCard

@Composable
fun HomeScreen(
  vm: HomeScreenVm = HomeScreenVm.Companion.getInstance()
) {
  val accountList by vm.accountList.collectAsState()
  val countdown by vm.countdown.collectAsState()
  val state = rememberLazyListState()

  Column(
    modifier = Modifier,
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text("countdown: $countdown")
    Box(
      modifier = Modifier.fillMaxSize()
    ) {
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = state,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        items(accountList) {
          AccountCard(it)
        }
      }
      VerticalScrollbar(
        modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
        adapter = rememberScrollbarAdapter(
          scrollState = state
        )
      )
    }
  }
}