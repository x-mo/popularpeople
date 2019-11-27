package com.x.popularpeople.ui.popular_people

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.x.popularpeople.api.POST_PER_PAGE
import com.x.popularpeople.api.TheMovieDBInterface
import com.x.popularpeople.model.People
import com.x.popularpeople.repository.NetworkState
import com.x.popularpeople.repository.PeopleDataSource
import com.x.popularpeople.repository.PeopleDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class PeoplePagedListRepository(private val apiService: TheMovieDBInterface,private var searchQuery: String) {

    lateinit var peoplePagedList: LiveData<PagedList<People>>
    lateinit var searchPeoplePagedList: LiveData<PagedList<People>>
    lateinit var peopleDataSourceFactory: PeopleDataSourceFactory



    fun fetchLivePeoplePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<People>> {

        peopleDataSourceFactory = PeopleDataSourceFactory(apiService, compositeDisposable,searchQuery)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        peoplePagedList = LivePagedListBuilder(peopleDataSourceFactory, config).build()

        return peoplePagedList
    }

    fun searchLivePeoplePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<People>> {

        peopleDataSourceFactory = PeopleDataSourceFactory(apiService, compositeDisposable, searchQuery)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        searchPeoplePagedList = LivePagedListBuilder(peopleDataSourceFactory, config).build()

        return peoplePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<PeopleDataSource, NetworkState>(
            peopleDataSourceFactory.peopleLiveDataSource,
            PeopleDataSource::networkState
        )
    }
}