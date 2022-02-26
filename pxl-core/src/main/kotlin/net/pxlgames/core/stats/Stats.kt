package net.pxlgames.core.stats

import java.util.*

class Stats(
    override val uuid: UUID,
    override var kills: Long = 0,
    override var deaths: Long = 0,
    override var special: Long = 0,
    override var losses: Long = 0,
    override var wins: Long = 0,
) : IStats