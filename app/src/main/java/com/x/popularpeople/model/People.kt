package com.x.popularpeople.model


import com.google.gson.annotations.SerializedName

data class People(
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String
)