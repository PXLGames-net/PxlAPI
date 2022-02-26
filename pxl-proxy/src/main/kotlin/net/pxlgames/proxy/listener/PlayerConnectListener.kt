package net.pxlgames.proxy.listener

import com.velocitypowered.api.event.PostOrder
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.connection.LoginEvent
import net.pxlgames.core.player.PlayerPool

class PlayerConnectListener {

    @Subscribe(order = PostOrder.FIRST)
    fun handleJoin(e: LoginEvent) {
        PlayerPool.checkPlayer(e.player.uniqueId, e.player.username)
    }


    @Subscribe(order = PostOrder.LATE)
    fun handleQuit(e: DisconnectEvent) {

    }
}