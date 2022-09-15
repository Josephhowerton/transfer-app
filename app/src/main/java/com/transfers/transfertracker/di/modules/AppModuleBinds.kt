package com.transfers.transfertracker.di.modules

import com.transfers.transfertracker.repo.AuthRepository
import com.transfers.transfertracker.repo.MainRepository
import com.transfers.transfertracker.repo.impl.AuthRepositoryImpl
import com.transfers.transfertracker.repo.impl.MainRepositoryImpl
import com.transfers.transfertracker.source.*
import com.transfers.transfertracker.source.impl.*
import dagger.Binds
import dagger.Module

@Module
abstract class AppModuleBinds {
    @Binds
    abstract fun bindAuthSource(authSource: AuthSourceImpl) : AuthSource
    @Binds
    abstract fun bindCountrySource(countryDataSource: CountryDataSourceImpl) : CountryDataSource
    @Binds
    abstract fun bindLeaguesSource(leaguesSource: LeagueDataSourceImpl) : LeaguesSource
    @Binds
    abstract fun bindNewsSource(newsSource: NewsSourceImpl) : NewsSource
    @Binds
    abstract fun bindSquadSource(squadSource: SquadDataSourceImpl) : SquadSource
    @Binds
    abstract fun bindTeamsSource(teamsSource: TeamsDataSourceImpl) : TeamsSource
    @Binds
    abstract fun bindTransferDataSource(transferDataSource: TransferDataSourceImpl) : TransferDataSource
    @Binds
    abstract fun bindUserSource(authSource: UserSourceImpl) : UserSource
    @Binds
    abstract fun bindStatisticsSource(statsSource: StatisticsSourceImpl) : StatisticsSource
    @Binds
    abstract fun bindMainRepository(mainRepository: MainRepositoryImpl) : MainRepository
    @Binds
    abstract fun bindAuthRepository(authRepository: AuthRepositoryImpl) : AuthRepository
}