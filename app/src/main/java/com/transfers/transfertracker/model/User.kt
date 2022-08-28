package com.transfers.transfertracker.model

import com.google.firebase.firestore.PropertyName
import com.transfers.transfertracker.model.teams.Team

data class User constructor(
    @get: PropertyName("id")
    @set: PropertyName("id")
    var id: String = "",

    @get: PropertyName("displayName")
    @set: PropertyName("displayName")
    var displayName: String = "",

    @get: PropertyName("email")
    @set: PropertyName("email")
    var email: String = "",

    @get: PropertyName("Teams")
    @set: PropertyName("Teams")
    var teams: List<Team>? = null
    )