package net.pxlgames.core.redis.adapter

import com.google.gson.Gson
import net.pxlgames.core.redis.RedisAdapter
import net.pxlgames.core.redis.RedisHandler.asyncCommands


@Suppress("MemberVisibilityCanBePrivate")
class RedisHashAdapter<T : Any>(val clazz: Class<T>, val hash: String) : RedisAdapter {

    private val gson: Gson = Gson()

    fun get(key: String): T? {

        val content = asyncCommands.hget(hash, key).get()

        return gson.fromJson(content, clazz)
    }

    fun set(key: String, value: T) {

        asyncCommands.hdel(hash, key).get()
        asyncCommands.hset(hash, key, gson.toJson(value))
    }

    fun delete(key: String) = asyncCommands.hdel(hash, key).get()

    fun getRaw(key: String): String? = asyncCommands.hget(hash, key).get()

    fun setRaw(key: String, value: String) = asyncCommands.hset(hash, key, value)
}