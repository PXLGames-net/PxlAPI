package net.pxlgames.core.player.name

import java.util.*

class PlayerName(override val uuid: UUID, override var name: String) : IPlayerName {

    /*
     * @see net.pxlgames.core.player.name.DefaultPlayerNamePool.updatePlayerName
     */
    fun update() = PlayerNamePool.updatePlayerName(uuid, name)

}