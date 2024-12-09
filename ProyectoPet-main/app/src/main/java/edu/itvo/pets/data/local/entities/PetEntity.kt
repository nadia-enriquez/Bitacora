package edu.itvo.pets.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "pet")
data class PetEntity (
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("name")
    var name: String = "",

    @SerializedName("birthdate")
    var birthdate: String,

    @SerializedName("race")
    var race: String = "",

    @SerializedName("type")
    var type: String = "",

    @SerializedName("description")
    var description: String = "",

    @SerializedName("image")
    var image: String = "",

    )