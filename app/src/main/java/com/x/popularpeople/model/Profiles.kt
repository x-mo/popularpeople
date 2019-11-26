package com.x.popularpeople.model


import com.google.gson.annotations.SerializedName

data class Profiles(
    @SerializedName("id")
    val id: Int,
    @SerializedName("profiles")
    val profiles: List<Profile>
)