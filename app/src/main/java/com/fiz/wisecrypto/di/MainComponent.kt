package com.fiz.wisecrypto.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.data.database.Database
import com.fiz.wisecrypto.data.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

private const val NAME_DATABASE = "database"

@Module
@InstallIn(SingletonComponent::class)
class MainComponent {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            context.getString(R.string.preferences),
            0x0000
        )
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): Database {
        return Room.databaseBuilder(
            context.applicationContext,
            Database::class.java,
            NAME_DATABASE
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: Database): UserDao = database.userDao()

    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.Default

}