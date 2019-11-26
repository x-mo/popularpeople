package com.x.popularpeople.ui.profiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.x.popularpeople.R
import com.x.popularpeople.api.POSTER_BASE_URL
import com.x.popularpeople.api.TheMovieDBClient
import com.x.popularpeople.api.TheMovieDBInterface
import com.x.popularpeople.model.PersonDetails
import com.x.popularpeople.model.Profiles
import com.x.popularpeople.repository.NetworkState
import com.x.popularpeople.ui.person_details.PersonDetailsRepository
import com.x.popularpeople.ui.person_details.PersonDetailsViewModel
import kotlinx.android.synthetic.main.activity_person_details.*

class ProfilesActivity : AppCompatActivity() {

    private lateinit var viewModel: ProfilesViewModel
    private lateinit var profilesListRepository: ProfilesListRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiles)


        val personId: Int = 1245

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        profilesListRepository = ProfilesListRepository(apiService)

        viewModel = getViewModel(personId)

        viewModel.profilesList.observe(this, Observer {
            bindUI(it)

        })

//        viewModel.networkState.observe(this, Observer {
//            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
//            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
//
//        })


    }


    private fun bindUI(it: Profiles) {
        Log.d("BindUI", "Arrived")
        Log.d("BindUI", it.profiles[0].filePath)
        Log.d("BindUI", it.profiles[1].filePath)
        Log.d("BindUI", it.profiles[2].filePath)


//        name.text = it.name
//        known_for.text = it.knownForDepartment
//        birth_day.text = it.birthday
//        birth_place.text = it.placeOfBirth
//        biography.text = it.biography
//        gender.text = if (it.gender == 2) "Male" else "Female"


//        val personPosterURL: String = POSTER_BASE_URL + it.profilePath
//        Glide.with(this)
//            .load(personPosterURL)
//            .into(iv_profile_image)
//        Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()

    }

    private fun getViewModel(personId: Int): ProfilesViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ProfilesViewModel(profilesListRepository, personId) as T
            }
        })[ProfilesViewModel::class.java]
    }

}
