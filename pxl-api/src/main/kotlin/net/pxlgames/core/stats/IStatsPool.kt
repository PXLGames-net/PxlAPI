package net.pxlgames.core.stats

import java.util.*

interface IStatsPool {

    /*
     * Returns the stats of the given Player
     *
     * @param uuid - The UUID of the Player
     *
     * @return The stats of the Player
     */
    fun getStats(uuid: UUID): IStats

    /*
    * updates the stats of the given Player
    *
    * @param uuid - The UUID of the Player
    * @param stats - The stats of the Player
     */
    fun updateStats(uuid: UUID, stats: IStats)

}