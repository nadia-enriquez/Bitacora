package edu.itvo.pets.core.utils

import edu.itvo.pets.data.local.entities.PetEntity
import edu.itvo.pets.data.models.PetModel


fun PetEntity.toPetModel() = PetModel(
    id = id,
    name = name,
    birthdate = birthdate,
    race = race,
    description = description,
    image = image,
    type = type,
)

fun PetModel.toEntity() = PetEntity(
    id=id,
    name = name,
    birthdate= birthdate,
    race = race,
    type = type,
    description = description,
    image = image,
)

fun List<PetModel>.toListPetEntity () =
    map {it.toEntity() }

fun List<PetEntity>.toListPetModel () =
    map {it.toPetModel() }