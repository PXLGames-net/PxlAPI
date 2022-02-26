package net.pxlgames.core.request

class Request(
    override val requester: String,
    override val requested: String,
    override var requestStatus: RequestStatus
) : IRequest