package net.pxlgames.core.friend

import java.util.*

interface IFriendPlayer {

    val uuid: UUID

    val friends: ArrayList<UUID>

    val friendRequests: ArrayList<UUID>

    val friendsCanJump: Boolean

    val showFriendRequests: Boolean

    fun addFriend(uuid: UUID)

    fun removeFriend(uuid: UUID)

    fun acceptFriendRequest(uuid: UUID)

    fun declineFriendRequest(uuid: UUID)
}