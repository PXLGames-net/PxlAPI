package net.pxlgames.proxy

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.proxy.ProxyServer
import net.pxlgames.core.Initializer
import net.pxlgames.core.redis.RedisHandler
import net.pxlgames.proxy.listener.PlayerConnectListener
import org.slf4j.Logger

class VelocityInitializer @Inject constructor(val server: ProxyServer, val logger: Logger) : Initializer {

    init {
        server.eventManager.register(this, PlayerConnectListener())
    }

    @Subscribe
    fun shutdown(e: ProxyShutdownEvent) {
        RedisHandler.close()
    }
}