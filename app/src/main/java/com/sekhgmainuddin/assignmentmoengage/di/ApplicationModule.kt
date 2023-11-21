package com.sekhgmainuddin.assignmentmoengage.di

import android.content.Context
import androidx.room.Room
import com.sekhgmainuddin.assignmentmoengage.data.db.SavedNewsDB
import com.sekhgmainuddin.assignmentmoengage.data.db.SavedNewsDao
import com.sekhgmainuddin.assignmentmoengage.data.repository.MainRepositoryImp
import com.sekhgmainuddin.assignmentmoengage.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    @Provides
    @Singleton
    fun provideMainRepository(dao: SavedNewsDao): MainRepository {
        return MainRepositoryImp(dao)
    }

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context): SavedNewsDB {
        return synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                SavedNewsDB::class.java,
                "saved_news_db"
            ).fallbackToDestructiveMigration().build()
        }
    }

    @Provides
    @Singleton
    fun provideDao(savedNewsDB: SavedNewsDB) = savedNewsDB.getDao()

}