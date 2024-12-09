package edu.itvo.pets.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.itvo.pets.data.models.PetModel
import edu.itvo.pets.domain.usecases.PetUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(private val petUseCase: PetUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(PetState())
    val uiState: StateFlow<PetState> = _uiState.asStateFlow()

    fun loadPet(petId: Int) {
        viewModelScope.launch {
            try {
                petUseCase.getPet(petId).collect { petResponse ->
                    // Verifica si petResponse es exitoso y tiene datos
                    if (petResponse?.success == true && petResponse.data.isNotEmpty()) {
                        val pet = petResponse.data.firstOrNull() // Suponiendo que petId es único y solo hay un objeto
                        pet?.let {
                            _uiState.value = PetState(
                                name = it.name,
                                description = it.description,
                                type = it.type,
                                race = it.race,
                                birthdate = it.birthdate,
                                image = it.image
                            )
                        }
                    } else {
                        Log.e("PetViewModel", "No pet found or response was not successful.")
                    }
                }
            } catch (e: Exception) {
                Log.e("PetViewModel", "Error loading pet: ${e.message}")
            }
        }
    }

    fun onEvent(event: PetEvent) {
        when (event) {
            is PetEvent.NameChanged -> {
                _uiState.value = _uiState.value.copy(name = event.name)
            }
            is PetEvent.DescriptionChanged -> {
                _uiState.value = _uiState.value.copy(description = event.description)
            }
            is PetEvent.ImageChanged -> {
                _uiState.value = _uiState.value.copy(image = event.image)
            }
            is PetEvent.TypeChanged -> {
                _uiState.value = _uiState.value.copy(type = event.type)
            }
            is PetEvent.RaceChanged -> {
                _uiState.value = _uiState.value.copy(race = event.race)
            }
            is PetEvent.BirthdateChanged -> {
                _uiState.value = _uiState.value.copy(birthdate = event.birthdate)
            }
            is PetEvent.UpdateClicked -> { // Evento para manejar la actualización.
                val updatedPet = PetModel(
                    id = event.pet.id, // Asegúrate de pasar el ID correcto.
                    name = event.pet.name,
                    description = event.pet.description,
                    type = event.pet.type,
                    race = event.pet.race,
                    birthdate = event.pet.birthdate,
                    image = event.pet.image
                )
                Log.e("VIEWMODEL", updatedPet.toString())
                viewModelScope.launch(Dispatchers.IO) {
                    petUseCase.updatePet(updatedPet) // Llama al caso de uso para actualizar la mascota.
                }
            }

            is PetEvent.AddClicked  -> {
                val pet = PetModel(
                    id=0,
                    name = event.name, description = event.description,
                    type = event.type, race = event.race, birthdate = event.birthdate,
                    image = event.image,
                )

                Log.e("VIEWMODEL", pet.toString())
                viewModelScope.launch(Dispatchers.IO) {
                    petUseCase.addPet(pet)
                }
            }

            is PetEvent.Reset -> { /* Implementar lógica para reiniciar el estado si es necesario */ }
        }
    }

    data class PetState(
        val name: String = "",
        val description: String = "",
        val image: String = "",
        val type: String = "",
        val race: String = "",
        val birthdate: String = "",
        val isLoading: Boolean = false,
        val error: String = "",
        val success: Boolean = false,
        val hasError: Boolean = false,
    )

    sealed class PetEvent {
        data class NameChanged(val name: String) : PetEvent()
        data class DescriptionChanged(val description: String) : PetEvent()
        data class ImageChanged(val image: String) : PetEvent()
        data class TypeChanged(val type: String) : PetEvent()
        data class RaceChanged(val race: String) : PetEvent()
        data class BirthdateChanged(val birthdate: String) : PetEvent()
        data class UpdateClicked(val pet: PetModel) : PetEvent() // Evento para actualizar.
        data class AddClicked(val name: String,
                              val description: String,
                              val type: String,
                              val race: String,
                              val birthdate: String,
                              val image: String
        ) : PetEvent()
        object Reset : PetEvent()
    }
}