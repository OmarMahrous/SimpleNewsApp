package com.itworxeducation.simplenewsapp.di

import android.app.Application
import androidx.room.Room
import com.itworxeducation.simplenewsapp.data.source.local.database.FavouritesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

    @Provides
    @Singleton
    fun provideDatabase(app:Application) =
        Room.databaseBuilder(app, FavouritesDatabase::class.java, "favourite_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideSourceDao(database: FavouritesDatabase) = database.sourceDao()

    @Provides
    fun provideArticleDao(database: FavouritesDatabase) = database.articleDao()
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope