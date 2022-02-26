package net.pxlgames.core.stats.leaderboard

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class LeaderboardHandlerTest {

    private val uuid: UUID = UUID.fromString("ff14f182-b750-413a-b20d-a55f0ef12936")

    @Test
    fun testGetLeaderboard() {
        val leaderboard = StatsLeaderboardHandler("Test", LeaderboardTime.MONTH)
        assertEquals(1, leaderboard.getRank(uuid))
    }

}