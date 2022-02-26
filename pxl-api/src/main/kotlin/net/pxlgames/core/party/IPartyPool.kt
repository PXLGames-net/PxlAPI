package net.pxlgames.core.party

import net.pxlgames.core.request.RequestFeedback

interface IPartyPool {

    fun invitePlayer(inviter: String, invited: String): Boolean

    fun acceptInvite(accepter: String, inviter: String): RequestFeedback

    fun rejectInvite(inviter: String, invited: String)

    fun removePlayer(player: String, party: IParty)

    fun promotePlayer(player: String, party: IParty): Boolean

    fun demotePlayer(player: String, party: IParty): Boolean

    fun getParty(player: String): IParty?

    fun createParty(player: String): IParty

}