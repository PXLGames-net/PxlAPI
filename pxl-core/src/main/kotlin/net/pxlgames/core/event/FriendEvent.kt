package net.pxlgames.core.event

import eu.thesimplecloud.api.eventapi.IEvent
import java.util.*

open class FriendEvent(requester: UUID, requested: UUID) : IEvent

open class FriendRequestEvent(requester: UUID, requested: UUID) : FriendEvent(requester, requested)

class FriendAddEvent(adder: UUID, added: UUID) : FriendRequestEvent(adder, added)

class FriendDenyEvent(requester: UUID, requested: UUID) : FriendRequestEvent(requester, requested)

class FriendAcceptEvent(requester: UUID, requested: UUID) : FriendRequestEvent(requester, requested)

class FriendRemoveEvent(player: UUID, removed: UUID) : FriendEvent(player, removed)