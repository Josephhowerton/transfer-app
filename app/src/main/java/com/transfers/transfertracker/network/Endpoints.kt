package com.transfers.transfertracker.network

object CountryEndpoints {
    const val KEY_NAME = "name"
    const val KEY_CODE = "code"
    const val KEY_SEARCH = "search"

    const val DEFAULT_VALUE_NAME = "england"
    const val DEFAULT_VALUE_CODE = "GB"
    const val DEFAULT_VALUE_SEARCH = "eng"
}

object LeagueEndpoints {
    const val KEY_LEAGUE_ID = "id"
    const val KEY_NAME = "name"
    const val KEY_COUNTRY = "country"
    const val KEY_COUNTRY_CODE = "code"
    const val KEY_SEASON = "season"
    const val KEY_TEAM = "team"
    const val KEY_TYPE = "type"
    const val KEY_CURRENT = "current"
    const val KEY_SEARCH = "search"
    const val KEY_LAST = "last"

    const val DEFAULT_VALUE_LEAGUE_ID = "39"
    const val DEFAULT_VALUE_NAME = "Premier League"
    const val DEFAULT_VALUE_COUNTRY = "england"
    const val DEFAULT_VALUE_COUNTRY_CODE = "GB"
    const val DEFAULT_VALUE_SEASON = "2021"
    const val DEFAULT_VALUE_TEAM = "33"
    const val DEFAULT_VALUE_TYPE = "league"
    const val DEFAULT_VALUE_CURRENT = "true"
    const val DEFAULT_VALUE_SEARCH = "england"
    const val DEFAULT_VALUE_LAST = "5"
}

object SquadEndpoints {
    const val KEY_TEAM = "team"
    const val KEY_PLAYER = "player"

    const val DEFAULT_VALUE_TEAM = "33"
    const val DEFAULT_VALUE_PLAYER = "35202"
}

object TeamsEndpoints {
    const val KEY_TEAM_ID = "id"
    const val KEY_NAME = "name"
    const val KEY_LEAGUE = "league"
    const val KEY_SEASON = "season"
    const val KEY_COUNTRY = "country"
    const val KEY_SEARCH = "search"
    const val KEY_LIMIT_DATE = "date"

    const val DEFAULT_VALUE_TEAM_ID = "33"
    const val DEFAULT_VALUE_NAME = "Manchester United"
    const val DEFAULT_VALUE_LEAGUE = "Premier League"
    const val DEFAULT_VALUE_SEASON = "2021"
    const val DEFAULT_VALUE_COUNTRY = "England"
    const val DEFAULT_VALUE_SEARCH = "Man U"
    const val DEFAULT_VALUE_LIMIT_DATE = "2020-08-08"
}

object TransferEndpoints {
    const val KEY_TEAM = "team"
    const val KEY_PLAYER = "player"

    const val DEFAULT_VALUE_CURRENT = "33"
    const val DEFAULT_VALUE_TYPE = "35202"
}