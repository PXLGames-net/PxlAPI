package net.pxlgames.core.player

import java.util.*

class PxlPlayer(
    override val uuid: UUID,
    override var name: String,
    override var lang: String,
    override var coins: Long,
    override var bannedTil: Long,
    override val properties: MutableMap<String, Any>
) : IPxlPlayer {

    /*
    * @see net.pxlgames.core.player.DefaultPlayerPool.updatePlayer
     */
    fun update() {
        PlayerPool.updatePlayer(this)
    }

    override fun onlineTime() =
        2000L//CloudAPI.instance.getCloudPlayerManager().getCloudPlayer(uuid).get().getOnlineTime()

    override val firstLogin =
        2000L//CloudAPI.instance.getCloudPlayerManager().getCloudPlayer(uuid).get().getFirstLogin()
    override val lastLogin = 2000L//CloudAPI.instance.getCloudPlayerManager().getCloudPlayer(uuid).get().getLastLogin()
}