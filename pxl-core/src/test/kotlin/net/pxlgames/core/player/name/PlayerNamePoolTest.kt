package net.pxlgames.core.player.name

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class PlayerNamePoolTest {

    private val uuid: UUID = UUID.fromString("ff14f182-b750-413a-b20d-a55f0ef12936")

    @Test
    fun updatePlayerName() {
        val pool = PlayerNamePool

        pool.updatePlayerName(uuid, "21OO")

        assertEquals(uuid, UUID.fromString(pool.getPlayerByName("21OO")))
    }

    @Test
    fun getPlayerByName() {

        assertEquals(uuid, UUID.fromString(PlayerNamePool.getPlayerByName("21OO")))
    }

}