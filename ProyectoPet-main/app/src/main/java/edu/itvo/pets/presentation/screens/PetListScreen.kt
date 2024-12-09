package edu.itvo.pets.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController // Importa NavHostController para la navegación.
import edu.itvo.pets.presentation.composables.PetCard
import edu.itvo.pets.presentation.viewmodel.ListPetViewModel

@Composable
fun ListPetScreen(
    viewModel: ListPetViewModel = hiltViewModel(),
    navController: NavHostController, // Agrega el controlador de navegación como parámetro.
    modifier: Modifier = Modifier
) {
    val petResponse = viewModel.petsState.collectAsState().value
    val errorMessage = viewModel.errorState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadPets()
    }

    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        errorMessage?.let {
            Text(text = "Error: $it", color = Color.Red)
        }

        if (petResponse == null && errorMessage == null) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            petResponse?.data?.let { pets ->
                if (pets.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.padding(16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(pets) { pet ->
                            PetCard(pet, viewModel, navController) // Pasa el navController a PetCard.
                        }
                    }
                } else {
                    // Si la lista está vacía, muestra un mensaje.
                    Text(text = "No pets available", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}