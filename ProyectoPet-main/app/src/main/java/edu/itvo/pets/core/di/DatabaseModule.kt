package edu.itvo.pets.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.itvo.pets.data.local.PetDB
import edu.itvo.pets.data.local.daos.PetDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providePetDao(petDB: PetDB): PetDao {
        return petDB.petDao()
    }

    @Provides
    @Singleton
    fun provideGetDatabase(@ApplicationContext appContext: Context,
                           scope: CoroutineScope
    ): PetDB {
        return PetDB.getDatabase(appContext, scope)
    }

    @Singleton // Provide always the same instance
    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

}
