package net.pxlgames.core.stats

import java.util.*

interface IStats {

    val uuid: UUID

    var kills: Long
    var deaths: Long
    var special: Long

    var losses: Long
    var wins: Long

}