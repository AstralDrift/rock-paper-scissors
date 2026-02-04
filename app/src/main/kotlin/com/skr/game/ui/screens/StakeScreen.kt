package com.skr.game.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val STAKE_TIERS = listOf(
    5 to "Low (5 SKR each · 10 SKR pot)",
    20 to "Mid (20 SKR each · 40 SKR pot)",
    100 to "High (100 SKR each · 200 SKR pot)",
    500 to "Top (500 SKR each · 1000 SKR pot)",
)

@Composable
fun StakeScreen(
    onBack: () -> Unit,
    onMatchmaking: () -> Unit,
) {
    var selectedStake by remember { mutableStateOf(5) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Choose stake") },
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
                .padding(20.dp),
        ) {
            Text(
                text = "House takes 10% of the pot when there's a winner. You're matched with players on the same tier.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(24.dp))
            STAKE_TIERS.forEach { (stake, label) ->
                val isSelected = selectedStake == stake
                Card(
                    onClick = { selectedStake = stake },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.surfaceVariant,
                    ),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        if (isSelected) {
                            Text(
                                text = "✓",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedButton(
                onClick = onMatchmaking,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Find match (${selectedStake} SKR)")
            }
        }
    }
}
