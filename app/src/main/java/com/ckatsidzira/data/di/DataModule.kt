package com.ckatsidzira.data.di

import android.content.Context
import androidx.room.Room
import com.ckatsidzira.BuildConfig
import com.ckatsidzira.data.repository.MovieRepositoryImpl
import com.ckatsidzira.data.source.local.dao.CacheDao
import com.ckatsidzira.data.source.local.MovieDatabase
import com.ckatsidzira.data.source.local.dao.FavoritesDao
import com.ckatsidzira.data.source.remote.MovieApi
import com.ckatsidzira.data.source.remote.interceptor.ApiKeyInterceptor
import com.ckatsidzira.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    @Singleton
    fun provideMovieApi(
        okHttpClient: OkHttpClient
    ): MovieApi =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)

    @Provides
    @Singleton
    fun provideMovieRepository(
        api: MovieApi,
        cache: MovieDatabase
    ): MovieRepository =
        MovieRepositoryImpl(api, cache)

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movies.db"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    fun provideCacheDao(db: MovieDatabase): CacheDao = db.cacheDao

    @Provides
    fun provideFavoritesDao(db: MovieDatabase): FavoritesDao = db.favoritesDao
}