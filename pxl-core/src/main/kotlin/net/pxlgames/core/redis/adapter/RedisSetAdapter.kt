package net.pxlgames.core.redis.adapter

import net.pxlgames.core.redis.RedisAdapter
import net.pxlgames.core.redis.RedisHandler.asyncCommands

@Suppress("MemberVisibilityCanBePrivate")
class RedisSetAdapter(val set: String) : RedisAdapter {

    fun increment(addScore: Double, member: String) = asyncCommands.zincrby(set, addScore, member)

    fun set(score: Double, member: String): Double {

        asyncCommands.zrem(set, member)
        return asyncCommands.zadd(set, score, member).get().toDouble()
    }

    fun top(key: String, start: Long, end: Long) = asyncCommands.zrevrange(set, start, end)

    fun topWithScores(start: Long, end: Long) = asyncCommands.zrevrangeWithScores(set, start, end)

    fun worst(start: Long, end: Long) = asyncCommands.zrange(set, start, end)

    fun worstWithScores(start: Long, end: Long) = asyncCommands.zrangeWithScores(set, start, end)

    fun rank(member: String) = asyncCommands.zrevrank(set, member)

    fun expire(ttl: Long) = asyncCommands.expire(set, ttl)

}