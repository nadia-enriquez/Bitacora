package edu.itvo.pets.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit // Importar el icono de editar.
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import edu.itvo.pets.data.models.PetModel
import edu.itvo.pets.presentation.viewmodel.ListPetViewModel
import androidx.navigation.NavHostController // Importa NavHostController para la navegación.

@Composable
fun PetCard(pet: PetModel, viewModel: ListPetViewModel, navController: NavHostController) { // Agrega navController como parámetro.
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pet.image)
                    .crossfade(true)  // Activar el efecto de transición de carga.
                    .build(),
                contentDescription = pet.name,
                modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(8.dp)), // Esquinas redondeadas en la imagen.
                contentScale = ContentScale.Crop // Recortar la imagen para ajustarse al área.
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = pet.name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = pet.description,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = pet.type,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Text(
                text = "Race: ${pet.race}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = "Birthdate: ${pet.birthdate}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    imageVector = Icons.Default.Edit, // Icono de editar.
                    contentDescription = "Icono de editar",
                    modifier = Modifier.size(24.dp).clickable {
                        navController.navigate("pet_update/${pet.id}") // Navegar a la pantalla de actualización.
                    },
                    colorFilter = ColorFilter.tint(Color.Blue) // Color del icono.
                )

                Spacer(modifier = Modifier.width(16.dp)) // Espacio entre los iconos.

                Image(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Icono de borrar",
                    modifier = Modifier.size(24.dp).clickable {
                        viewModel.onDeleteClicked(pet.id) // Llama a tu función cuando se haga clic.
                    },
                    colorFilter = ColorFilter.tint(Color.Red)
                )
            }
        }
    }
}