package com.transfers.transfertracker.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.transfers.transfertracker.model.news.News
import com.transfers.transfertracker.network.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object{
        private const val NEWS_DATA_API_BASE_URL = "https://newsdata.io/api/"
        private const val SPORTS_DATA_API_BASE_URL = "https://v3.football.api-sports.io/"
    }

    @Provides
    @Singleton
    fun provideGson() : Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideNewsService(gson: Gson) : NewsService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(NEWS_DATA_API_BASE_URL)
            .build()
            .create()

    @Provides
    @Singleton
    fun provideCountryService(gson: Gson) : CountryService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(SPORTS_DATA_API_BASE_URL)
            .build()
            .create()

    @Provides
    @Singleton
    fun provideLeagueService(gson: Gson) : LeagueService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(SPORTS_DATA_API_BASE_URL)
            .build()
            .create()

    @Provides
    @Singleton
    fun provideSquadService(gson: Gson) : SquadService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(SPORTS_DATA_API_BASE_URL)
            .build()
            .create()

    @Provides
    @Singleton
    fun provideTeamsService(gson: Gson) : TeamService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(SPORTS_DATA_API_BASE_URL)
            .build()
            .create()

    @Provides
    @Singleton
    fun provideTransferService(gson: Gson) : TransferService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(SPORTS_DATA_API_BASE_URL)
            .build()
            .create()

    @Provides
    @Singleton
    fun provideSportsDataService(gson: Gson) : SportsDataService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(SPORTS_DATA_API_BASE_URL)
            .build()
            .create()
}