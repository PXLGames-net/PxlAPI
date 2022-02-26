package net.pxlgames.core.stats

import net.pxlgames.core.redis.adapter.RedisHashAdapter
import net.pxlgames.core.stats.leaderboard.IStatsLeaderboardHandler
import java.util.*

class StatsPool(gamemode: String) : IStatsPool {

    private val adapter = RedisHashAdapter(Stats::class.java, "stats:$gamemode:stats")
    private val leaderboards = ArrayList<IStatsLeaderboardHandler>()

    /*
     * Returns the stats of the given Player
     *
     * @param uuid - The UUID of the Player
     *
     * @return The stats of the Player
     */
    override fun getStats(uuid: UUID) = adapter.get(uuid.toString()) ?: createStats(uuid)

    /*
    * updates the stats of the given Player
    *
    * @param uuid - The UUID of the Player
    * @param stats - The stats of the Player
     */
    override fun updateStats(uuid: UUID, stats: IStats) =
        adapter.set(uuid.toString(), stats as Stats).also { leaderboards.forEach { it.update(uuid, stats) } }

    private fun createStats(uuid: UUID): IStats {
        val stats = Stats(uuid)
        updateStats(uuid, stats)
        return stats
    }

    /*
     * Adds a leaderboard handler to the pool
     *
     * @param handler - The handler to add
     */
    fun addLeaderboardHandler(handler: IStatsLeaderboardHandler) = leaderboards.add(handler)

}