package com.skr.game.ui.screens

import com.skr.game.core.GameEngine
import com.skr.game.core.Move
import com.skr.game.core.RoundResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ArrowBack
import androidx.compose.material3.Button
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

@Composable
fun GameScreen(
    entryStake: Int,
    onBack: () -> Unit,
    onMatchEnd: (won: Boolean) -> Unit,
) {
    val initialPot = GameEngine.potForStake(entryStake).toDouble()
    var roundNumber by remember { mutableStateOf(1) }
    var scorePlayer by remember { mutableStateOf(0) }
    var scoreOpponent by remember { mutableStateOf(0) }
    var currentPot by remember { mutableStateOf(initialPot) }
    var lastRoundResult by remember { mutableStateOf<RoundResult?>(null) }
    var lastPlayerMove by remember { mutableStateOf<Move?>(null) }
    var lastOpponentMove by remember { mutableStateOf<Move?>(null) }
    var matchOver by remember { mutableStateOf(false) }
    var winner by remember { mutableStateOf<RoundResult?>(null) }
    var winnerPayout by remember { mutableStateOf<Double?>(null) }

    fun playRound(playerMove: Move) {
        val opponentMove = Move.entries.random()
        val result = GameEngine.resolveRound(playerMove, opponentMove)
        when (result) {
            RoundResult.DRAW -> currentPot = GameEngine.potAfterDraw(currentPot)
            RoundResult.PLAYER_A_WINS -> scorePlayer++
            RoundResult.PLAYER_B_WINS -> scoreOpponent++
        }
        lastRoundResult = result
        lastPlayerMove = playerMove
        lastOpponentMove = opponentMove
        if (GameEngine.isMatchOver(scorePlayer, scoreOpponent)) {
            matchOver = true
            winner = result
            winnerPayout = GameEngine.winnerPayout(currentPot)
        } else {
            roundNumber++
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (matchOver) "Match over" else "Round $roundNumber",
                    )
                },
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "You $scorePlayer – $scoreOpponent Opponent",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = "Pot: ${currentPot.toInt()} SKR",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (matchOver) {
                val won = winner == RoundResult.PLAYER_A_WINS
                Text(
                    text = if (won) "You win!" else "You lose!",
                    style = MaterialTheme.typography.headlineSmall,
                    color = if (won)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.error,
                )
                if (won) {
                    winnerPayout?.let { payout ->
                        Text(
                            text = "You get ${"%.2f".format(payout)} SKR",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = { onMatchEnd(winner == RoundResult.PLAYER_A_WINS) }) {
                    Text("End match")
                }
            } else {
                lastRoundResult?.let { result ->
                    lastPlayerMove?.let { pm ->
                        lastOpponentMove?.let { om ->
                            Text(
                                text = "You played ${pm.name.lowercase()}, opponent played ${om.name.lowercase()}.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Text(
                                text = when (result) {
                                    RoundResult.PLAYER_A_WINS -> "You win this round."
                                    RoundResult.PLAYER_B_WINS -> "Opponent wins this round."
                                    RoundResult.DRAW -> "Draw. Pot reduced 1%."
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
                Text(
                    text = "Select your move",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Move.entries.forEach { move ->
                        OutlinedButton(onClick = { playRound(move) }) {
                            Text(move.name.lowercase().replaceFirstChar { it.uppercase() })
                        }
                    }
                }
            }
        }
    }
}
