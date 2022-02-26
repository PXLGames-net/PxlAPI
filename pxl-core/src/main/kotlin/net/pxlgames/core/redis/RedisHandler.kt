package net.pxlgames.core.redis

import com.redis.lettucemod.RedisModulesClient
import com.redis.lettucemod.api.StatefulRedisModulesConnection
import com.redis.lettucemod.api.async.RedisModulesAsyncCommands
import io.lettuce.core.RedisURI
import net.pxlgames.core.redis.config.RedisConnectionConfig

object RedisHandler {

    private val connectionInfo = RedisConnectionConfig()

    private val redisClient: RedisModulesClient = RedisModulesClient.create(
        RedisURI.Builder.redis(connectionInfo.host, connectionInfo.port)
            .also { if (connectionInfo.password != "") it.withPassword(connectionInfo.password.toCharArray()) }
            .withDatabase(connectionInfo.database).build()
    )

    private val connection: StatefulRedisModulesConnection<String, String> = redisClient.connect()
    val asyncCommands: RedisModulesAsyncCommands<String, String> = connection.async()

    init {

        if (asyncCommands.ping().get() != "PONG") {
            throw Exception("Redis connection failed!")
        } else {
            println("Redis connection successful!")
        }

    }

    fun close() = connection.close()
}