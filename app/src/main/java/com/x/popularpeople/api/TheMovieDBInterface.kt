package com.x.popularpeople.api

import com.x.popularpeople.model.PeopleResponse
import com.x.popularpeople.model.PersonDetails
import com.x.popularpeople.model.Profiles
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {

    // https://api.themoviedb.org/3/person/popular?api_key=0a5753edd0bf7cc1e5ca435a17227adf&page=1
    // https://api.themoviedb.org/3/person/1892?api_key=0a5753edd0bf7cc1e5ca435a17227adf
    // https://api.themoviedb.org/3/person/1245/images?api_key=0a5753edd0bf7cc1e5ca435a17227adf&person_id=1245
    // https://api.themoviedb.org/3/
    // https://api.themoviedb.org/3/search/person?api_key=0a5753edd0bf7cc1e5ca435a17227adf&query=Robert&page=1

    @GET("person/popular")
    fun getPopularPeople(@Query("page") page: Int): Single<PeopleResponse>

    @GET("person/{person_id}")
    fun getPersonDetails(@Path("person_id") id: Int): Single<PersonDetails>

    @GET("person/{person_id}/images")
    fun getPersonProfiles(@Path("person_id") id: Int): Single<Profiles>

    @GET("search/person")
    fun searchPopularPeople(@Query("page") page: Int, @Query("query") query: String): Single<PeopleResponse>

}