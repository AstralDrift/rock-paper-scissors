package com.skr.game.data

/**
 * Cosmetic types and catalog from docs/game-design.md §4.
 * 45 total: 12 avatars, 9 move skins, 12 titles, 12 badges.
 */
enum class CollectibleType { Avatar, MoveSkin, Title, Badge }

data class Collectible(
    val id: String,
    val name: String,
    val type: CollectibleType,
)

object Catalog {

    private val avatars = listOf(
        "Default", "Warrior", "Mage", "Rogue", "Knight", "Ninja",
        "Pirate", "Phoenix", "Dragon", "Robot", "Alien", "Ghost",
    )
    private val moveSkins = listOf(
        "Rock: Classic", "Rock: Stone Fist", "Rock: Meteor",
        "Paper: Classic", "Paper: Scroll", "Paper: Leaf",
        "Scissors: Classic", "Scissors: Blades", "Scissors: Claws",
    )
    private val titles = listOf(
        "Newbie", "Brawler", "Champion", "Legend", "Lucky", "Unstoppable",
        "Underdog", "Sharpshooter", "Veteran", "Master", "Grandmaster", "SKR King",
    )
    private val badges = listOf(
        "First Win", "10 Wins", "100 Wins", "Win Streak 3", "Win Streak 5", "Win Streak 10",
        "Daily Player", "Weekly Warrior", "Crate Opener", "Collector", "Big Spender", "Loyal",
    )

    val all: List<Collectible> = buildList {
        addAll(avatars.mapIndexed { i, n -> Collectible("avatar_$i", n, CollectibleType.Avatar) })
        addAll(moveSkins.mapIndexed { i, n -> Collectible("move_$i", n, CollectibleType.MoveSkin) })
        addAll(titles.mapIndexed { i, n -> Collectible("title_$i", n, CollectibleType.Title) })
        addAll(badges.mapIndexed { i, n -> Collectible("badge_$i", n, CollectibleType.Badge) })
    }

    private val byId = all.associateBy { it.id }

    fun get(id: String): Collectible? = byId[id]

    fun random(): Collectible = all.random()
}
