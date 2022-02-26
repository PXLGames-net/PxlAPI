package net.pxlgames.core.friend

import java.util.*

interface IFriendPool {

    fun getFriendPlayer(uuid: UUID): IFriendPlayer

    fun acceptFriend(accepter: UUID, requester: UUID): Boolean

    fun acceptFriend(accepter: IFriendPlayer, requester: UUID): Boolean

    fun declineFriend(decliner: UUID, requester: UUID): Boolean

    fun declineFriend(decliner: IFriendPlayer, requester: UUID): Boolean

    fun addFriend(requester: UUID, requested: UUID): Boolean

    fun addFriend(requester: IFriendPlayer, requested: UUID): Boolean

    fun removeFriend(remover: UUID, removed: UUID): Boolean

    fun removeFriend(remover: IFriendPlayer, removed: UUID): Boolean

    fun removeAllFriend(remover: UUID)

    fun removeAllFriend(remover: IFriendPlayer)

}