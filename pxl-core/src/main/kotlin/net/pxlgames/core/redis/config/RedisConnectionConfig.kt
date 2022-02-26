package net.pxlgames.core.redis.config

data class RedisConnectionConfig(
    val host: String = System.getenv("REDIS_HOST") ?: "localhost",
    val port: Int = System.getenv("REDIS_PORT")?.toInt() ?: 6379,
    val password: String = System.getenv("REDIS_PASSWORD") ?: "",
    val database: Int = (System.getenv("REDIS_DATABASE") ?: 0) as Int
)
