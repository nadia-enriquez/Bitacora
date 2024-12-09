package edu.itvo.pets.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.itvo.pets.data.models.PetResponse
import edu.itvo.pets.domain.usecases.PetUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPetViewModel @Inject constructor(private val petUseCase: PetUseCase) : ViewModel() {

    private val _petsState = MutableStateFlow<PetResponse?>(null)
    val petsState: StateFlow<PetResponse?> = _petsState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun loadPets() {
        viewModelScope.launch {
            try {
                petUseCase.getPets().collect { petResponse ->
                    _petsState.value = petResponse
                }
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }

    fun loadPet(petId: Int) {
        viewModelScope.launch {
            try {
                petUseCase.getPet(petId).collect { petResponse ->
                    // Aquí puedes manejar la respuesta y actualizar el estado según sea necesario.
                    // Por ejemplo, si tienes un estado específico para una mascota:
                    // _petDetailState.value = petResponse
                }
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }

    fun onDeleteClicked(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            petUseCase.delete(petId = id)
        }
        loadPets()
    }
}