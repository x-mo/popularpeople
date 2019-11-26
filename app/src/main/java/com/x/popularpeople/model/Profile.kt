package com.x.popularpeople.model


import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("file_path")
    val filePath: String
)