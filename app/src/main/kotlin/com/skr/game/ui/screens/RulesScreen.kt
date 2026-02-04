package com.skr.game.ui.screens

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding

@Composable
fun RulesScreen(onBack: () -> Unit) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rules") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        ArrowBack(contentDescription = "Back")
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 16.dp),
        ) {
            Text(
                text = "How to play",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = "You and your opponent each put the same amount of SKR into the pot and play best-of-3: first to win 2 rounds wins the match.\n\n" +
                    "Each round you pick Rock, Paper, or Scissors. Your pick is hidden until both players have chosen. When both have chosen, the round resolves automatically.\n\n" +
                    "Rock beats Scissors, Scissors beats Paper, Paper beats Rock. Same choice = draw; the house takes 1% of the pot and the rest stays in play. The match continues until someone wins 2 rounds.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = "Payouts",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = "You win the match: You get 90% of the pot (10% house fee). Your opponent gets nothing.\n\n" +
                    "You lose the match: You get nothing. Winner gets 90% of the pot.\n\n" +
                    "Draw in a round: The house takes 1% of the pot; the rest stays in play. No one wins the round; the match continues until someone wins 2 rounds.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = "Async play",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = "You don't need to be online at the same time. Submit your move when you're ready; when your opponent has moved too, the round resolves and you'll get a notification.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = "Stake tiers",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = "Choose a tier (5, 20, 100, or 500 SKR per player). You're matched with someone on the same tier. The pot is always twice the entry (both players put in the same amount).",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = "Progression",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = "Earn XP and level up, collect avatars and cosmetics from loot crates, and complete daily quests. Cosmetics don't change the game—only how you look.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
