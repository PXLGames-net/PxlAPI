package net.pxlgames.core

import net.pxlgames.core.player.PlayerPool
import net.pxlgames.core.player.name.PlayerNamePool
import net.pxlgames.core.redis.RedisHandler

interface Initializer {

    val redisHandler: RedisHandler
        get() = RedisHandler

    val playerPool: PlayerPool
        get() = PlayerPool

    val playerNamePool: PlayerNamePool
        get() = PlayerNamePool

}