package net.pxlgames.core.redis

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RedisHandlerTest {

    @Test
    fun test() {

        assertEquals("PONG", RedisHandler.asyncCommands.ping().get())
    }
}