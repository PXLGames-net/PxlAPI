package net.pxlgames.core.stats.leaderboard

import net.pxlgames.core.stats.IStats
import java.util.*

interface IStatsLeaderboardHandler {

    /**
     * increases the score of a player
     *
     * @param uuid -  the uuid of the player
     * @param score - the score to increase
     */
    fun addScore(uuid: UUID, score: Int): Double

    /**
     * decreases the score of a player
     *
     * @param uuid -  the uuid of the player
     * @param score - the score to decrease
     */
    fun removeScore(uuid: UUID, score: Int): Double

    /**
     * set the score of a player
     *
     * @param uuid - the uuid of the player
     */
    fun setScore(uuid: UUID, score: Int): Double

    /*
    @returns list of the top players with their score

    @param start - start index (rank to start from)
    @param end - end index (rank to end at)

     */
    fun getTop(start: Long, end: Long): List<IStatsLeaderboardPlayer>

    /*
    @returns the current rank of a player

    @param uuid - the uuid of the player
     */
    fun getRank(uuid: UUID): Int

    /*
    coululates the points/score of a player
    and enters it into the leaderboard

    @param uuid - uuid of the player
    @param stats - stats of the player
     */
    fun update(uuid: UUID, stats: IStats)

}