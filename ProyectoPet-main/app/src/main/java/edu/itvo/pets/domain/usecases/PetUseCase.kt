package edu.itvo.pets.domain.usecases

import edu.itvo.pets.data.models.PetModel
import edu.itvo.pets.data.models.PetResponse
import edu.itvo.pets.domain.PetRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PetUseCase @Inject constructor(private val petRepository: PetRepository) {
    suspend fun getPet(petId:Int): Flow<PetResponse?> = petRepository.getPet(petId)
    suspend fun getPets(): Flow<PetResponse?> = petRepository.getPets()
    suspend fun addPet(petModel: PetModel) = petRepository.addPet(petModel)
    suspend fun delete(petId: Int)= petRepository.deletePet(petId)
    suspend fun updatePet(petModel: PetModel) = petRepository.updatePet(petModel)
}

