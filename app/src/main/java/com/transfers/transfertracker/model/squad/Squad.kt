package com.transfers.transfertracker.model.squad

import com.google.firebase.Timestamp
import com.transfers.transfertracker.model.player.Player

data class Squad(val players: List<Player>, val lastUpdate: Timestamp) { constructor() : this(mutableListOf(), Timestamp.now()) }
