package com.x.popularpeople.ui.popular_people

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.x.popularpeople.model.People
import com.x.popularpeople.repository.NetworkState
import com.x.popularpeople.ui.person_details.PersonDetailsRepository
import io.reactivex.disposables.CompositeDisposable

class PopularPeopleViewModel(
    private val peopleRepository: PeoplePagedListRepository,
    private val query: String
) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val peoplePagedList: LiveData<PagedList<People>> by lazy {

        if (query == "")
            peopleRepository.fetchLivePeoplePagedList(compositeDisposable)
        else
            peopleRepository.searchLivePeoplePagedList(compositeDisposable,query)

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