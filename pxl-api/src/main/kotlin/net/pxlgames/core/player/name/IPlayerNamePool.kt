package net.pxlgames.core.player.name

import java.util.*

interface IPlayerNamePool {

    fun getPlayerByName(name: String): String?

    fun updatePlayerName(uuid: UUID, name: String)

}