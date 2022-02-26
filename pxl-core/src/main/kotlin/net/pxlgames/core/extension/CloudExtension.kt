package net.pxlgames.core.extension

import eu.thesimplecloud.api.CloudAPI
import eu.thesimplecloud.api.eventapi.IEvent

fun IEvent.call() {

    if (CloudAPI.isAvailable()) {
        CloudAPI.instance.getEventManager().call(this)
    } else {
        println("Disabled event call for Testing")
    }
}