package net.pxlgames.proxy.extension

import com.velocitypowered.api.proxy.Player
import eu.thesimplecloud.api.CloudAPI
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.Template
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

fun Player.getMSG(key: String, vararg templates: Pair<String, Component>): Component = MiniMessage.get().parse(
    LanguagePool.PREFIX + LanguagePool.getMessage(this.language, key),
    templates.map { Template.of(it.first, it.second) })

fun Player.getMSGWithoutPrefix(key: String, vararg templates: Pair<String, Component>): Component = MiniMessage.get()
    .parse(LanguagePool.getMessage(this.language, key), templates.map { Template.of(it.first, it.second) })

fun Player.sendMSG(key: String, vararg templates: Pair<String, Component>) = sendMessage(getMSG(key, *templates))

fun Player.sendMSGWithoutPrefix(key: String, vararg templates: Pair<String, Component>) =
    sendMessage(getMSGWithoutPrefix(key, *templates))