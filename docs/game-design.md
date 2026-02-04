# SKR Token Game – Game Design (Single Source of Truth)

This document locks gameplay, economy, and progression for the Android build. Use it as the canonical reference for rules and economy.

---

## 1. Locked design decisions

| Decision | Choice | Notes |
|----------|--------|--------|
| **Draw rule** | **Refund** (Option A) | Pot refunded to both players minus a 1% house fee for the round. No winner, no loser. |
| **Match length** | **Best-of-3** | First to 2 round wins wins the match. May add best-of-5 or "first to X" later. |
| **Moves** | **3 options** | Rock, Paper, Scissors (or reskin: e.g. Blades / Shield / Spell). Classic matrix only. |

---

## 2. Economy: stakes and house cut

### Stake tiers (SKR)

| Tier | Entry per player | Pot |
|------|------------------|-----|
| Low | 5 SKR | 10 SKR |
| Mid | 20 SKR | 40 SKR |
| High | 100 SKR | 200 SKR |
| Top | 500 SKR | 1000 SKR |

Matchmaking pairs players in the same tier only.

### House cut

- **When there is a winner**: **10%** of the pot. Winner receives **90%** of the pot. Loser receives 0.
- **When a round is a draw**: The house takes **1%** of the current pot. The remaining pot stays in play; no round winner is declared. Play continues until one player reaches 2 round wins. When the match ends, the winner gets 90% of whatever the pot is at that time (after any draw deductions).

### Payout examples (10% house on wins)

- **Best-of-3, you win 2–0**: Pot 20 SKR → you receive 18 SKR (profit 8 SKR).
- **Best-of-3, you lose 0–2**: You receive 0 SKR.
- **One round drawn, then you win 2–1**: After draw, pot = 19.8 SKR (1% to house). You receive 90% of 19.8 = 17.82 SKR.

Always show **pot**, **house %**, and **you get** before the player confirms a match.

---

## 3. Match and round structure (async, pre-selected moves)

- **Match**: One opponent, one stake tier, best-of-3 (first to 2 round wins).
- **Round**: Each player submits **one move** (Rock / Paper / Scissors) before the round resolves. Submissions are **commit-reveal** (or encrypted) so the other player cannot see the move until resolution.
- **Async**: When **both** players have submitted for a round, the round **resolves automatically**. Players do not need to be online at the same time.
- **Flow**: Round 1 opens → both submit → resolve → show result, update score → repeat until one player has 2 round wins.

---

## 4. Cosmetic types (crates and collection)

All progression is **cosmetic only**. No pay-to-win.

### Avatars (12)

- Default, Warrior, Mage, Rogue, Knight, Ninja, Pirate, Phoenix, Dragon, Robot, Alien, Ghost.

### Move skins (9)

- **Rock**: Classic, Stone Fist, Meteor, Crystal.
- **Paper**: Classic, Scroll, Leaf, Silk.
- **Scissors**: Classic, Blades, Claws, Scythe.

(Same three outcomes; only visuals change.)

### Titles (12)

- Newbie, Brawler, Champion, Legend, Lucky, Unstoppable, Underdog, Sharpshooter, Veteran, Master, Grandmaster, SKR King.

### Badges (12)

- First Win, 10 Wins, 100 Wins, Win Streak 3, Win Streak 5, Win Streak 10, Daily Player, Weekly Warrior, Crate Opener, Collector, Big Spender, Loyal.

**Total**: 12 avatars + 9 move skins + 12 titles + 12 badges = **45 collectibles** for crates and collection. Duplicates can convert to a small SKR or “dust” currency for the shop.

---

## 5. Progression: levels, crates, quests

- **Levels**: XP per match (play + win bonus). Level gates unlock avatars, crate tiers, and/or stake tiers. No effect on RPS outcome.
- **Loot crates**: Earned by playing (e.g. 1 crate per N games or per level-up). Optional: purchase with SKR or fiat. Contents: cosmetics only (avatars, move skins, titles, badges).
- **Daily quests**: e.g. “Win 2 matches,” “Play 5 rounds” → reward SKR or a crate.
- **Streaks**: e.g. “Win 3 in a row” → bonus XP or a crate.
- **Duplicate handling**: Duplicate crate items → small SKR or dust for the shop to buy specific cosmetics.

---

## 6. Rules screen (in-app copy)

Use this as the one-page “Rules” screen text in the app.

---

**How to play**

- You and your opponent each put the same amount of **SKR** into the pot and play **best-of-3**: first to win **2 rounds** wins the match.
- Each round you pick **Rock**, **Paper**, or **Scissors**. Your pick is hidden until both players have chosen. When both have chosen, the round resolves automatically.
- **Rock** beats Scissors, **Scissors** beats Paper, **Paper** beats Rock. Same choice = **draw**; the round is refunded (minus a small fee) and you play another round until someone wins 2.

**Payouts**

- **You win the match**: You get **90%** of the pot (10% house fee). Your opponent gets nothing.
- **You lose the match**: You get nothing. Winner gets 90% of the pot.
- **Draw in a round**: The house takes 1% of the pot; the rest stays in play. No one wins the round; the match continues until someone wins 2 rounds.

**Async play**

- You don’t need to be online at the same time. Submit your move when you’re ready; when your opponent has moved too, the round resolves and you’ll get a notification.

**Stake tiers**

- Choose a tier (e.g. 5, 20, 100, or 500 SKR per player). You’re matched with someone on the same tier. The pot is always twice the entry (both players put in the same amount).

**Progression**

- Earn XP and level up, collect avatars and cosmetics from loot crates, and complete daily quests. Cosmetics don’t change the game—only how you look.

---

*End of game design document.*
