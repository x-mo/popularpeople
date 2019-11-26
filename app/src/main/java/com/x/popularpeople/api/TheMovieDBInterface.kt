package com.x.popularpeople.api

import com.x.popularpeople.model.PersonDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TheMovieDBInterface {

    // https://api.themoviedb.org/3/person/popular?api_key=0a5753edd0bf7cc1e5ca435a17227adf&page=1
    // https://api.themoviedb.org/3/person/1892?api_key=0a5753edd0bf7cc1e5ca435a17227adf
    // https://api.themoviedb.org/3/

    @GET("person/{person_id}")
    fun getPersonDetails(@Path("movie_id") id: Int): Single<PersonDetails>

}