package net.pxlgames.core.party

import net.pxlgames.core.event.*
import net.pxlgames.core.extension.call
import net.pxlgames.core.redis.adapter.RedisHashAdapter
import net.pxlgames.core.request.RequestFeedback
import net.pxlgames.core.request.RequestPool

object PartyPool : IPartyPool {

    private val partyMap = RedisHashAdapter(Party::class.java, "party")
    private val requestPool = RequestPool("party")

    override fun invitePlayer(inviter: String, invited: String): Boolean {

        getParty(inviter) ?: createParty(inviter)

        requestPool.sendRequest(inviter, invited)

        return true
    }

    override fun acceptInvite(accepter: String, inviter: String): RequestFeedback {

        // not request
        val request = requestPool.getRequest(inviter, accepter) ?: return RequestFeedback.NotFound

        if (request.requester == accepter) return RequestFeedback.OwnRequest

        val party = getParty(inviter) ?: return RequestFeedback.NotFound

        party.members.add(accepter)

        PartyAcceptEvent(accepter, party).call()

        partyMap.set(party.leader, party as Party)

        return RequestFeedback.ACCEPTED

    }

    override fun rejectInvite(inviter: String, invited: String) {

        requestPool.removeRequest(inviter, invited)
        PartyRejectEvent(invited, inviter).call()
    }

    override fun removePlayer(player: String, party: IParty) {

        party.members.remove(player)
        partyMap.set(party.leader, party as Party)
    }

    override fun promotePlayer(player: String, party: IParty): Boolean {

        if (party.leader == player) return false

        if (party.moderators.contains(player)) {
            party.moderators.remove(player)

            val oldLeader = party.leader

            party.leader = player

            partyMap.set(party.leader, party as Party)

            partyMap.delete(oldLeader)

            PartyNewLeaderEvent(player, party).call()

            return true
        }

        party.members.remove(player)
        party.moderators.add(player)

        partyMap.set(party.leader, party as Party)

        PartyPromoteEvent(player, party).call()

        return true
    }

    override fun demotePlayer(player: String, party: IParty): Boolean {

        if (!party.moderators.contains(player)) return false

        party.moderators.remove(player)

        party.members.add(player)

        partyMap.set(party.leader, party as Party)

        PartyDemoteEvent(player, party).call()

        return true
    }


    override fun getParty(player: String): IParty? {
        return partyMap.get(player)
    }

    override fun createParty(player: String): IParty {
        val party = Party(player)

        partyMap.set(player, party)

        return party
    }


}