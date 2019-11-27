package com.x.popularpeople.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.x.popularpeople.api.TheMovieDBInterface
import com.x.popularpeople.model.People
import io.reactivex.disposables.CompositeDisposable

class PeopleDataSourceFactory(
    private val apiService: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable,
    private val searchQuery: String
) : DataSource.Factory<Int, People>() {

    val peopleLiveDataSource = MutableLiveData<PeopleDataSource>()

    override fun create(): DataSource<Int, People> {

        val peopleDataSource = PeopleDataSource(apiService, compositeDisposable,searchQuery)
        peopleLiveDataSource.postValue(peopleDataSource)

        return peopleDataSource

    }

}