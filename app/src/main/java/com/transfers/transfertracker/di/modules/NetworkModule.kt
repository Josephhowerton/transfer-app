package com.transfers.transfertracker.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.transfers.transfertracker.network.service.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val NEWS_DATA_API_BASE_URL = "https://newsdata.io/api/1/"
    private const val SPORTS_DATA_API_BASE_URL = "https://v3.football.api-sports.io/"

    @Provides
    @Singleton
    fun provideGson() : Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOkHttp() : OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsService(gson: Gson, okHttpClient: OkHttpClient) : NewsService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(NEWS_DATA_API_BASE_URL)
            .build()
            .create()

    @Provides
    @Singleton
    fun provideCountryService(gson: Gson, okHttpClient: OkHttpClient) : CountryService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(SPORTS_DATA_API_BASE_URL)
            .build()
            .create()

    @Provides
    @Singleton
    fun provideLeagueService(gson: Gson, okHttpClient: OkHttpClient) : LeagueService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(SPORTS_DATA_API_BASE_URL)
            .build()
            .create()

    @Provides
    @Singleton
    fun provideSquadService(gson: Gson, okHttpClient: OkHttpClient) : SquadService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(SPORTS_DATA_API_BASE_URL)
            .build()
            .create()

    @Provides
    @Singleton
    fun provideTeamsService(gson: Gson, okHttpClient: OkHttpClient) : TeamService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(SPORTS_DATA_API_BASE_URL)
            .build()
            .create()

    @Provides
    @Singleton
    fun provideStatisticsService(gson: Gson, okHttpClient: OkHttpClient) : StatisticsService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(SPORTS_DATA_API_BASE_URL)
            .build()
            .create()
}