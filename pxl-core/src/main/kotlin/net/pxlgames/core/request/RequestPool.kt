package net.pxlgames.core.request

import net.pxlgames.core.redis.adapter.RedisHashAdapter

class RequestPool(requestType: String) : IRequestPool {

    private val adpater = RedisHashAdapter(Request::class.java, "request-$requestType")

    /*
    Retruns the the request from the given players (the order is not important)
    if there is no request, null is returned

    @param player1 - one of the players
    @param player2 - the other of the players

    @returns the request if there is one, null otherwise
     */
    override fun getRequest(player1: String, player2: String): IRequest? {

        val uuids = listOf(player1, player2)

        uuids.sorted()

        return adpater.get(uuids.joinToString(";"))
    }

    /*
    Creates a new request and saves it to the database

    @param from - the player who created the request
    @param to - the player who is requested

    @returns the created request
     */
    override fun sendRequest(from: String, to: String): IRequest {

        val uuids = listOf(from, to)

        uuids.sorted()

        val uudisJoined = uuids.joinToString(";")

        val requestOld = adpater.get(uudisJoined)

        if (requestOld != null) {
            return requestOld
        }

        val request = Request(from, to, RequestStatus.PENDING)

        adpater.set(uudisJoined, request)

        return request
    }

    /*
    Updates the request with the given status

    @param accepter - the player whos accepted the request
    @param requester - the player who created the request
    @param status - the new status of the request

    @returns if successful, true, otherwise false
     */
    override fun updateRequestStatus(accepter: String, requester: String, newStatus: RequestStatus): Boolean {

        val uuids = listOf(accepter, requester)

        uuids.sorted()

        val request = adpater.get(uuids.joinToString(";")) ?: return false

        request.requestStatus = newStatus

        adpater.set(uuids.joinToString(";"), request)

        return true
    }

    /*
    Deletes the request from the database

    @param accepter - the player whos accepted the request
    @param requester - the player who created the request
     */
    override fun removeRequest(requester: String, accepter: String) {

        val uuids = listOf(accepter, requester)

        uuids.sorted()

        adpater.delete(uuids.joinToString(";"))
    }

}