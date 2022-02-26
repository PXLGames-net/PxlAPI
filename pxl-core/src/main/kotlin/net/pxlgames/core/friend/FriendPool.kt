package net.pxlgames.core.friend

import net.pxlgames.core.event.FriendAcceptEvent
import net.pxlgames.core.event.FriendAddEvent
import net.pxlgames.core.event.FriendDenyEvent
import net.pxlgames.core.event.FriendRemoveEvent
import net.pxlgames.core.extension.call
import net.pxlgames.core.redis.adapter.RedisHashAdapter
import net.pxlgames.core.request.RequestPool
import net.pxlgames.core.request.RequestStatus
import java.util.*

object FriendPool : IFriendPool {

    private val apdapter = RedisHashAdapter(FriendPlayer::class.java, "friends")
    private val requestPool = RequestPool("friends")

    override fun getFriendPlayer(uuid: UUID) = apdapter.get(uuid.toString()) ?: createFriendPlayer(uuid)

    override fun acceptFriend(accepter: UUID, requester: UUID): Boolean {

        val request = requestPool.getRequest(requester.toString(), accepter.toString()) ?: return false
        if (accepter.toString() == request.requester) return false

        val accepterPlayer = getFriendPlayer(accepter)
        val requesterPlayer = getFriendPlayer(requester)

        accepterPlayer.addFriend(requester)
        requesterPlayer.addFriend(accepter)

        requestPool.removeRequest(requester.toString(), accepter.toString())

        //call FriendAcceptEvent
        FriendAcceptEvent(accepter, requester).call()

        return true

    }

    override fun acceptFriend(accepter: IFriendPlayer, requester: UUID): Boolean {

        val request = requestPool.getRequest(requester.toString(), accepter.uuid.toString()) ?: return false
        if (accepter.uuid.toString() == request.requester) return false

        val requesterPlayer = getFriendPlayer(requester)

        accepter.addFriend(requester)
        requesterPlayer.addFriend(accepter.uuid)

        requestPool.removeRequest(requester.toString(), accepter.uuid.toString())

        //call FriendAcceptEvent
        FriendAcceptEvent(accepter.uuid, requester).call()

        return true
    }

    override fun declineFriend(decliner: UUID, requester: UUID): Boolean {

        val request = requestPool.getRequest(requester.toString(), decliner.toString()) ?: return false
        if (decliner.toString() == request.requester) return false

        requestPool.updateRequestStatus(requester.toString(), decliner.toString(), RequestStatus.DENIED)

        //call FriendDenyEvent
        FriendDenyEvent(decliner, requester).call()

        return true

    }

    override fun declineFriend(decliner: IFriendPlayer, requester: UUID): Boolean {

        val request = requestPool.getRequest(requester.toString(), decliner.uuid.toString()) ?: return false
        if (decliner.uuid.toString() == request.requester) return false

        requestPool.updateRequestStatus(requester.toString(), decliner.uuid.toString(), RequestStatus.DENIED)

        //call FriendDenyEvent
        FriendDenyEvent(decliner.uuid, requester).call()

        return true
    }

    override fun addFriend(requester: UUID, requested: UUID): Boolean {

        if (getFriendPlayer(requester).friends.contains(requested)) return false

        val request = requestPool.sendRequest(requester.toString(), requested.toString())

        if (request.requestStatus != RequestStatus.PENDING) return false

        //call FriendAddEvent
        FriendAddEvent(requester, requested).call()

        return true
    }

    override fun addFriend(requester: IFriendPlayer, requested: UUID): Boolean {

        if (requester.friends.contains(requested)) return false

        val request = requestPool.sendRequest(requester.uuid.toString(), requested.toString())

        if (request.requestStatus != RequestStatus.PENDING) return false

        //call FriendAddEvent
        FriendAddEvent(requester.uuid, requested).call()

        return true
    }

    override fun removeFriend(remover: UUID, removed: UUID): Boolean {
        val removerPlayer = getFriendPlayer(remover)

        if (!removerPlayer.friends.contains(removed)) return false

        removerPlayer.friends.remove(removed)
        update(removerPlayer)

        //call FriendRemovedEvent
        FriendRemoveEvent(remover, removed).call()

        return true
    }

    override fun removeFriend(remover: IFriendPlayer, removed: UUID): Boolean {

        if (!remover.friends.contains(removed)) return false

        remover.friends.remove(removed)
        update(remover as FriendPlayer)

        //call FriendRemovedEvent
        FriendRemoveEvent(remover.uuid, removed).call()

        return true
    }

    override fun removeAllFriend(remover: UUID) {
        val removerPlayer = getFriendPlayer(remover)

        removerPlayer.friends.clear()
        update(removerPlayer)
    }

    override fun removeAllFriend(remover: IFriendPlayer) {
        remover.friends.clear()
        update(remover as FriendPlayer)
    }

    fun update(player: FriendPlayer) = apdapter.set(player.uuid.toString(), player)

    private fun createFriendPlayer(uuid: UUID) =
        FriendPlayer(uuid, arrayListOf(), arrayListOf(), true, showFriendRequests = true)
}