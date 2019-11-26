package com.x.popularpeople.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.x.popularpeople.api.TheMovieDBInterface
import com.x.popularpeople.model.Profiles
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfilesDataSource(
    private val apiService: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) {

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    private val _downloadedProfilesResponse = MutableLiveData<Profiles>()
    val downloadedProfilesResponse: LiveData<Profiles>
        get() = _downloadedProfilesResponse

    fun fetchProfiles(personId: Int) {

        networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getPersonProfiles(personId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedProfilesResponse.postValue(it)
                            networkState.postValue(NetworkState.LOADED)
                        }, {
                            networkState.postValue(NetworkState.ERROR)
                            Log.e("ProfilessDataSource", it.message)
                        }
                    )
            )

        } catch (e: Exception) {
            Log.e("ProfilessDataSource", e.message)
        }

    }
}