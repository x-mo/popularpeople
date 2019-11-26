package com.x.popularpeople.ui.profiles

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.x.popularpeople.model.People
import com.x.popularpeople.model.Profile
import com.x.popularpeople.model.Profiles
import com.x.popularpeople.repository.NetworkState
import com.x.popularpeople.ui.popular_people.PeoplePagedListRepository
import io.reactivex.disposables.CompositeDisposable

class ProfilesViewModel (private val profilesListRepository: ProfilesListRepository, personId: Int) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val profilesList: LiveData<Profiles> by lazy {
        profilesListRepository.fetchProfiles(compositeDisposable,personId)
    }

//    val networkState: LiveData<NetworkState> by lazy {
//        peopleRepository.getNetworkState()
//    }

//    fun listIsEmpty(): Boolean {
//        return peoplePagedList.value?.isEmpty() ?: true
//    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}