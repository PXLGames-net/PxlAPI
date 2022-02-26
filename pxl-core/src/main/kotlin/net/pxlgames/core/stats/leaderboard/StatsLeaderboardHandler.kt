package net.pxlgames.core.stats.leaderboard

import net.pxlgames.core.redis.adapter.RedisSetAdapter
import net.pxlgames.core.stats.IStats
import java.util.*

class StatsLeaderboardHandler(gamemode: String, time: LeaderboardTime) : IStatsLeaderboardHandler {

    private val apdapter = RedisSetAdapter("stats:${gamemode}:lb_${time.name}")

    /**
     * increases the score of a player
     *
     * @param uuid -  the uuid of the player
     * @param score - the score to increase
     */
    override fun addScore(uuid: UUID, score: Int): Double = apdapter.increment(score.toDouble(), uuid.toString()).get()

    /**
     * decreases the score of a player
     *
     * @param uuid -  the uuid of the player
     * @param score - the score to decrease
     */
    override fun removeScore(uuid: UUID, score: Int): Double =
        apdapter.increment(-score.toDouble(), uuid.toString()).get()

    /**
     * set the score of a player
     *
     * @param uuid - the uuid of the player
     */
    override fun setScore(uuid: UUID, score: Int) = apdapter.set(score.toDouble(), uuid.toString())


    /*
    @returns list of the top players with their score

    @param start - start index (rank to start from)
    @param end - end index (rank to end at)

     */
    override fun getTop(start: Long, end: Long): List<IStatsLeaderboardPlayer> {
        val scores = apdapter.topWithScores(start, end).get()
        val list = ArrayList<IStatsLeaderboardPlayer>()

        for (i in 0 until scores.size step 2) {
            list.add(StatsLeaderboardPlayer(UUID.fromString(scores[i].value), scores[i + 1].value.toDouble()))
        }

        return list
    }

    /*
    @returns the current rank of a player

    @param uuid - the uuid of the player
     */
    override fun getRank(uuid: UUID) = apdapter.rank(uuid.toString()).get().toInt() + 1

    /*
    coululates the points/score of a player
    and enters it into the leaderboard

    @param uuid - uuid of the player
    @param stats - stats of the player
     */
    override fun update(uuid: UUID, stats: IStats) {

        var newpoints = stats.wins * 250L
        newpoints += stats.kills * 100L
        newpoints += stats.special * 150L

        setScore(uuid, newpoints.toInt())

    }

    init {
        //set key to expire at the specified time
        apdapter.expire(time.ttl)
    }
}

enum class LeaderboardTime(val ttl: Long) {
    DAY(86400),
    WEEK(604800),
    MONTH(2629746),
    YEAR(31556952),
    GLOBAL(-1)
}