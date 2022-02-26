package net.pxlgames.core.player.name

import net.pxlgames.core.redis.adapter.RedisHashAdapter
import java.util.*

object PlayerNamePool : IPlayerNamePool {

    private val adapter = RedisHashAdapter(String::class.java, "player_names")

    /*
    retrun DefaultPlayerName by name from redis

    @param name - name of player

    @return The DefaultPlayerName from the given name if exists, otherwise null
     */
    override fun getPlayerByName(name: String): String? = adapter.getRaw(name.lowercase())

    /*
    Delete old player with given name from redis.
    Save new player with given name to redis.

    @param uuid - uuid of player
    @param name - name of player
     */
    override fun updatePlayerName(uuid: UUID, name: String) {
        adapter.setRaw(name.lowercase(), uuid.toString())
    }
}