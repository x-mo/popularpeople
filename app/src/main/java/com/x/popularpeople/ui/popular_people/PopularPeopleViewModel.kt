package com.x.popularpeople.ui.popular_people

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.x.popularpeople.model.People
import com.x.popularpeople.repository.NetworkState
import com.x.popularpeople.ui.person_details.PersonDetailsRepository
import io.reactivex.disposables.CompositeDisposable

class PopularPeopleViewModel(private val peopleRepository: PeoplePagedListRepository) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val peoplePagedList: LiveData<PagedList<People>> by lazy {
        peopleRepository.fetchLivePeoplePagedList(compositeDisposable)
    }

    val searchPeoplePagedList: LiveData<PagedList<People>> by lazy {
        peopleRepository.searchLivePeoplePagedList(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        peopleRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return peoplePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}