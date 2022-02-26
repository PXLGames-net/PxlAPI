package net.pxlgames.core.friend

import java.util.*

class FriendPlayer(
    override val uuid: UUID,
    override val friends: ArrayList<UUID>,
    override val friendRequests: ArrayList<UUID>,
    override val friendsCanJump: Boolean,
    override val showFriendRequests: Boolean,
) : IFriendPlayer {

    override fun addFriend(uuid: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeFriend(uuid: UUID) {
        TODO("Not yet implemented")
    }

    override fun acceptFriendRequest(uuid: UUID) {
        TODO("Not yet implemented")
    }

    override fun declineFriendRequest(uuid: UUID) {
        TODO("Not yet implemented")
    }
}