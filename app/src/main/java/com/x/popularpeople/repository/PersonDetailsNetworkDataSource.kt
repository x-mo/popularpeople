package com.x.popularpeople.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.x.popularpeople.api.TheMovieDBInterface
import com.x.popularpeople.model.PersonDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class PersonDetailsNetworkDataSource(
    private val apiService: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedPersonDetailsResponse = MutableLiveData<PersonDetails>()
    val downloadedPersonDetailsResponse: LiveData<PersonDetails>
        get() = _downloadedPersonDetailsResponse

    fun fetchPersonDetails(personId: Int) {

        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getPersonDetails(personId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedPersonDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        }, {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("PersonDetailsDataSource", it.message)
                        }
                    )
            )

        } catch (e: Exception) {
            Log.e("PersonDetailsDataSource", e.message)
        }

    }
}