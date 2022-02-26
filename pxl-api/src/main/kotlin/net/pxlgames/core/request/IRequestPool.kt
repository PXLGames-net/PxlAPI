package net.pxlgames.core.request

interface IRequestPool {

    fun getRequest(player1: String, player2: String): IRequest?

    fun sendRequest(from: String, to: String): IRequest

    fun updateRequestStatus(accepter: String, requester: String, newStatus: RequestStatus): Boolean

    fun removeRequest(requester: String, accepter: String)

}