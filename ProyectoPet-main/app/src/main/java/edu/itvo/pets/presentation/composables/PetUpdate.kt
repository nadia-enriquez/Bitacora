package edu.itvo.pets.presentation.composables

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.HdrAuto
import androidx.compose.material.icons.filled.Panorama
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import edu.itvo.pets.data.models.PetModel
import edu.itvo.pets.presentation.viewmodel.PetViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController // Importa NavHostController
import edu.itvo.pets.core.utils.loadJsonFromAssets

@Composable
fun PetUpdateScreen(
    petId: Int,
    navController: NavHostController,
    viewModel: PetViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    context: Context
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var hasUpdated by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val petsType = remember {
        loadJsonFromAssets(context, "pettype.json")
    }

    LaunchedEffect(petId) {
        viewModel.loadPet(petId)
    }

    Card (
        modifier = modifier.fillMaxWidth()
            .height(850.dp)

    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(16.dp) // Padding alrededor del contenido desplazable

        )  {
            OutlinedTextField(
                value = state.name,
                onValueChange = { viewModel.onEvent(PetViewModel.PetEvent.NameChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .padding(top = 100.dp),
                label = {
                    Text(text="nombre: ")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.HdrAuto,
                        contentDescription = null,
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                })
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.description,
                onValueChange = { viewModel.onEvent(PetViewModel.PetEvent.DescriptionChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                }),
                label = {
                    Text(text="Descripción: ")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Description,
                        contentDescription = null,
                    )
                },
            )
            Spacer(modifier = Modifier.height(8.dp))


            SpinnerFromJson(
                items = petsType, viewModel
            )

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.race,
                onValueChange = { viewModel.onEvent(PetViewModel.PetEvent.RaceChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                }),
                label = {
                    Text(text="Raza: ")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Category,
                        contentDescription = null,
                    )
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.birthdate,
                onValueChange = { viewModel.onEvent(PetViewModel.PetEvent.BirthdateChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                }),
                label = {
                    Text(text="Fecha nacimiento: ")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.CalendarMonth,
                        contentDescription = null,
                    )
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.image,
                onValueChange = { viewModel.onEvent(PetViewModel.PetEvent.ImageChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                }),
                label = {
                    Text(text="Foto: ")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Panorama,
                        contentDescription = null,
                    )
                },
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val updatedPet = PetModel(
                        id = petId,
                        name = state.name,
                        description = state.description,
                        type = state.type,
                        race = state.race,
                        birthdate = state.birthdate,
                        image = state.image
                    )
                    viewModel.onEvent(PetViewModel.PetEvent.UpdateClicked(updatedPet))
                    hasUpdated = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Actualizar")
            }

            if (hasUpdated) {
                AlertDialog(
                    onDismissRequest = { hasUpdated = false },
                    confirmButton = {
                        Button(onClick = { hasUpdated = false }) {
                            Text("Aceptar")
                        }
                    },
                    title = { Text("Actualización Exitosa") },
                    text = { Text("La mascota ha sido actualizada correctamente.") }
                )
            }
        }
    }
}
