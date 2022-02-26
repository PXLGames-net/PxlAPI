package net.pxlgames.core.event

import eu.thesimplecloud.api.eventapi.IEvent
import net.pxlgames.core.player.PxlPlayer

open class PlayerEvent : IEvent

/**
 * This event is called when a player gets updated by the PlayerPool.
 */
class PlayerUpdateEvent(val player: PxlPlayer) : PlayerEvent()

/**
 * This event is called when a players language gets updated by the the server or proxy.
 */
class PlayerLanguageUpdateEvent(val player: PxlPlayer) : PlayerEvent()