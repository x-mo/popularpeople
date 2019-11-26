package com.x.popularpeople.ui.person_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.x.popularpeople.model.PersonDetails
import com.x.popularpeople.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class PersonDetailsViewModel(private val personRepository: PersonDetailsRepository, personId: Int) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val personDetails: LiveData<PersonDetails> by lazy {
        personRepository.fetchSinglePersonDetails(compositeDisposable, personId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        personRepository.getPersonDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}