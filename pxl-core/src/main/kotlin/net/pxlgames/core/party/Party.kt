package net.pxlgames.core.party

class Party(
    override var leader: String,
    override val moderators: ArrayList<String> = arrayListOf(),
    override val members: ArrayList<String> = arrayListOf()
) : IParty {


}