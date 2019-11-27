package com.x.popularpeople.ui.profiles

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.x.popularpeople.api.POST_PER_PAGE
import com.x.popularpeople.api.TheMovieDBInterface
import com.x.popularpeople.model.People
import com.x.popularpeople.model.PersonDetails
import com.x.popularpeople.model.Profile
import com.x.popularpeople.model.Profiles
import com.x.popularpeople.repository.*
import io.reactivex.disposables.CompositeDisposable

class ProfilesListRepository(private val apiService: TheMovieDBInterface) {

    lateinit var profilesDataSource: ProfilesDataSource

    fun fetchProfiles(
        compositeDisposable: CompositeDisposable,
        personId: Int
    ): LiveData<Profiles> {

        profilesDataSource =
            ProfilesDataSource(apiService, compositeDisposable)
        profilesDataSource.fetchProfiles(personId)

        return profilesDataSource.downloadedProfilesResponse

    }

}