package net.pxlgames.core.player

import net.pxlgames.core.player.name.PlayerNamePool
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class PxlPlayerPoolTest {

    private val pool = PlayerPool
    private val uuid: UUID = UUID.fromString("ff14f182-b750-413a-b20d-a55f0ef12936")

    @Test
    fun getPlayer() {
        assertEquals("21OO", pool.getPlayer(UUID.fromString("ff14f182-b750-413a-b20d-a55f0ef12936")).name)
    }

    @Test
    fun getPlayerByName() {
        assertEquals(uuid, UUID.fromString(PlayerNamePool.getPlayerByName("21OO")))
    }

    @Test
    fun updatePlayer() {
        val player = pool.getPlayer(uuid) as PxlPlayer

        player.coins = 1000L
        player.update()
        assertEquals(1000L, pool.getPlayer(uuid).coins)

        player.coins = 0L
        player.update()
        assertEquals(0L, pool.getPlayer(uuid).coins)
    }

}