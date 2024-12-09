package edu.itvo.pets.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.itvo.pets.data.PetRepositoryImpl
import edu.itvo.pets.data.local.PetLocalDataSource
import edu.itvo.pets.data.local.PetLocalDataSourceImpl
import edu.itvo.pets.domain.PetRepository


@Module
@InstallIn(SingletonComponent::class)
abstract class PetRepositoryModule {

    @Binds
    abstract fun bindPetRepository(petRepositoryImpl: PetRepositoryImpl):
            PetRepository

    @Binds
    abstract fun bindPetLocalDataSource(petLocalDataSourceImpl: PetLocalDataSourceImpl):
            PetLocalDataSource

}