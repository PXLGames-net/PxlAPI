package net.pxlgames.core.stats

import net.pxlgames.core.redis.RedisHandler
import net.pxlgames.core.stats.leaderboard.LeaderboardTime
import net.pxlgames.core.stats.leaderboard.StatsLeaderboardHandler
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class StatsPoolTest {

    private val uuid: UUID = UUID.fromString("ff14f182-b750-413a-b20d-a55f0ef12936")
    private val pool = StatsPool("Test")

    @Test
    fun getStats() {

        val stats = pool.getStats(uuid)

        assertEquals(uuid, stats.uuid)
        assertEquals(0, stats.kills)

    }

    @Test
    fun updateStats() {

        val stats = pool.getStats(uuid)

        stats.kills = 10
        pool.updateStats(uuid, stats)

        assertEquals(10, stats.kills)

        stats.kills = 0
        pool.updateStats(uuid, stats)

        assertEquals(0, stats.kills)

    }

    @Test
    fun testLeaderboard() {

        val stats = pool.getStats(uuid)

        pool.addLeaderboardHandler(StatsLeaderboardHandler("Test", LeaderboardTime.DAY))

        stats.kills = 10
        pool.updateStats(uuid, stats)

        assertEquals(1000.0, RedisHandler.asyncCommands.zscore("stats:Test:lb_DAY", uuid.toString()).get())

        stats.kills = 0
        pool.updateStats(uuid, stats)

        assertEquals(0.0, RedisHandler.asyncCommands.zscore("stats:Test:lb_DAY", uuid.toString()).get())
    }
}