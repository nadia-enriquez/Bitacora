package edu.itvo.pets.data.models

data class PetModel(
    val id: Int,
    val name: String,
    val description: String,
    val type: String,
    val race: String,
    val birthdate: String,
    val image: String
    )
