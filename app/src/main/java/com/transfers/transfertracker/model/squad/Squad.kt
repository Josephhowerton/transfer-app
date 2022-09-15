package com.transfers.transfertracker.model.squad

import com.google.firebase.Timestamp

data class Squad(val squadPlayers: List<SquadPlayer>?, val lastUpdate: Timestamp?) { constructor() : this(mutableListOf(), Timestamp.now()) }
