package com.x.popularpeople.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.x.popularpeople.api.FIRST_PAGE
import com.x.popularpeople.api.TheMovieDBInterface
import com.x.popularpeople.model.People
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PeopleDataSource(
    private val apiService: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable,
    private val query: String
) : PageKeyedDataSource<Int, People>() {

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, People>
    ) {
        networkState.postValue(NetworkState.LOADING)

        if (query == "")
            compositeDisposable.add(
                apiService.getPopularPeople(page)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        callback.onResult(it.peopleList, null, page + 1)
                        networkState.postValue(NetworkState.LOADED)
                    }, {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("PeopleDataSource", "1"+it.message)
                    })
            )
        else
            compositeDisposable.add(
                apiService.searchPopularPeople(page, query)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        callback.onResult(it.peopleList, null, page + 1)
                        networkState.postValue(NetworkState.LOADED)
                    }, {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("PeopleDataSource", "2"+it.message)
                    })
            )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, People>) {

        networkState.postValue(NetworkState.LOADING)

        if (query == "")
            compositeDisposable.add(
                apiService.getPopularPeople(params.key)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        if (it.totalPages >= params.key) {
                            callback.onResult(it.peopleList, params.key + 1)
                            networkState.postValue(NetworkState.LOADED)
                        } else {
                            networkState.postValue(NetworkState.ENDOFLIST)
                        }
                    }, {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("PeopleDataSource", "3"+it.message)
                    })
            )
        else
            compositeDisposable.add(
                apiService.searchPopularPeople(params.key, query)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        if (it.totalPages >= params.key) {
                            callback.onResult(it.peopleList, params.key + 1)
                            networkState.postValue(NetworkState.LOADED)
                        } else {
                            networkState.postValue(NetworkState.ENDOFLIST)
                        }
                    }, {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("PeopleDataSource", "4"+it.message)
                    })
            )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, People>) {

    }


}