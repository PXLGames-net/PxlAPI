package net.pxlgames.core.event

import eu.thesimplecloud.api.eventapi.IEvent
import net.pxlgames.core.party.IParty

open class PartyEvent : IEvent

class PartyInviteEvent(invitedPlayer: String, party: IParty) : PartyEvent()

class PartyAcceptEvent(accepter: String, party: IParty) : PartyEvent()

class PartyKickEvent(kickedPlayer: String, party: IParty) : PartyEvent()

class PartyLeaveEvent(levingPlayer: String, party: IParty) : PartyEvent()

class PartyRejectEvent(rejectigPlayer: String, partyInviter: String) : PartyEvent()

class PartyPromoteEvent(promotedPlayer: String, party: IParty) : PartyEvent()

class PartyDemoteEvent(promotedPlayer: String, party: IParty) : PartyEvent()

class PartyNewLeaderEvent(newLeaderPlayer: String, party: IParty) : PartyEvent()

class PartyChangeServerEvent(newServerName: String) : PartyEvent()

class PartyChatEvent(player: String, chat: String, party: IParty) : PartyEvent()