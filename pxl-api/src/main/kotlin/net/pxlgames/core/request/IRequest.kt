package net.pxlgames.core.request

interface IRequest {

    val requester: String

    val requested: String

    var requestStatus: RequestStatus

}

enum class RequestStatus {

    PENDING,

    ACCEPTED,

    DENIED,

}

enum class RequestFeedback {

    PENDING,

    ACCEPTED,

    DENIED,

    ERROR,

    TIMEOUT,

    NotFound,

    OwnRequest,

}