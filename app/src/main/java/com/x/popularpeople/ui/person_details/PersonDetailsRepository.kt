package com.x.popularpeople.ui.person_details

import androidx.lifecycle.LiveData
import com.x.popularpeople.api.TheMovieDBInterface
import com.x.popularpeople.model.PersonDetails
import com.x.popularpeople.repository.NetworkState
import com.x.popularpeople.repository.PersonDetailsNetworkDataSource
import io.reactivex.disposables.CompositeDisposable

class PersonDetailsRepository(private val apiService: TheMovieDBInterface) {

    lateinit var personDetailsNetworkDataSource: PersonDetailsNetworkDataSource
    fun fetchSinglePersonDetails(
        compositeDisposable: CompositeDisposable,
        personId: Int
    ): LiveData<PersonDetails> {

        personDetailsNetworkDataSource =
            PersonDetailsNetworkDataSource(apiService, compositeDisposable)
        personDetailsNetworkDataSource.fetchPersonDetails(personId)

        return personDetailsNetworkDataSource.downloadedPersonDetailsResponse

    }

    fun getPersonDetailsNetworkState(): LiveData<NetworkState> {
        return personDetailsNetworkDataSource.networkState
    }
}