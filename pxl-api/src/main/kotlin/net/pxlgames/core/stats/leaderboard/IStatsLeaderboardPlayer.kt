package net.pxlgames.core.stats.leaderboard

import java.util.*

interface IStatsLeaderboardPlayer {

    val uuid: UUID
    var score: Double
}