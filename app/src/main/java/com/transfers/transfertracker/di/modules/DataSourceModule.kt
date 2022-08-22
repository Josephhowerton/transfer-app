package com.transfers.transfertracker.di.modules

import com.transfers.transfertracker.source.*
import com.transfers.transfertracker.source.impl.*
import dagger.Binds
import dagger.Module

@Module
abstract class DataSourceModule {
    @Binds abstract fun bindAuthSource(authSource: AuthSourceImpl) : AuthSource
    @Binds abstract fun bindCountrySource(countryDataSource: CountryDataSourceImpl) : CountryDataSource
    @Binds abstract fun bindLeaguesSource(leaguesSource: LeagueDataSourceImpl) : LeaguesSource
    @Binds abstract fun bindNewsSource(newsSource: NewsSourceImpl) : NewsSource
    @Binds abstract fun bindSquadSource(squadSource: SquadDataSourceImpl) : SquadSource
    @Binds abstract fun bindTeamsSource(teamsSource: TeamsDataSourceImpl) : TeamsSource
    @Binds abstract fun bindTransferDataSource(transferDataSource: TransferDataSourceImpl) : TransferDataSource
    @Binds abstract fun bindUserSource(authSource: UserSourceImpl) : UserSource
}