package net.pxlgames.proxy.extension

import com.velocitypowered.api.proxy.Player
import eu.thesimplecloud.api.CloudAPI
import net.kyori.adventure.text.Component
import net.pxlgames.core.event.PlayerLanguageUpdateEvent
import net.pxlgames.core.lang.LanguagePool
import net.pxlgames.core.player.PlayerPool
import net.pxlgames.core.player.PxlPlayer

@JvmName("PlayerExtension")

fun Player.getPxlPlayer(): PxlPlayer = PlayerPool.getPlayer(this.uniqueId) as PxlPlayer

var Player.language: String
    get() = this.getPxlPlayer().lang
    set(value) {
        val player = getPxlPlayer()
        player.lang = value
        player.update()
        CloudAPI.instance.getEventManager().call(PlayerLanguageUpdateEvent(player))
    }

fun Player.getMSG(key: String): String = LanguagePool.PREFIX + LanguagePool.getMessage(this.language, key)

fun Player.getRawMSG(key: String): String = LanguagePool.getMessage(this.language, key)

fun Player.sendMSG(key: String) = sendMessage(Component.text(getMSG(key)))

fun Player.sendRawMSG(key: String) = sendMessage(Component.text(getRawMSG(key)))
