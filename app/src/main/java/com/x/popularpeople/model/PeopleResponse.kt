package com.x.popularpeople.model


import com.google.gson.annotations.SerializedName

data class PeopleResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val peopleList: List<People>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)