package com.x.popularpeople.model


import com.google.gson.annotations.SerializedName

data class PersonDetails(
    @SerializedName("biography")
    val biography: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("place_of_birth")
    val placeOfBirth: String,
    @SerializedName("profile_path")
    val profilePath: String

)
{
    override fun toString(): String =
        "$id $name $placeOfBirth $birthday $gender $knownForDepartment $biography $profilePath"
}