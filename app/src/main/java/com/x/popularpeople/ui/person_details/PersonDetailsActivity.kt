package com.x.popularpeople.ui.person_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.x.popularpeople.R
import com.x.popularpeople.api.POSTER_BASE_URL
import com.x.popularpeople.api.TheMovieDBClient
import com.x.popularpeople.api.TheMovieDBInterface
import com.x.popularpeople.model.PersonDetails
import com.x.popularpeople.repository.NetworkState
import com.x.popularpeople.ui.profiles.ProfilesListAdapter
import com.x.popularpeople.ui.profiles.ProfilesListRepository
import com.x.popularpeople.ui.profiles.ProfilesViewModel
import kotlinx.android.synthetic.main.activity_person_details.*
import kotlinx.android.synthetic.main.activity_person_details.rv_profiles
import kotlinx.android.synthetic.main.activity_profiles.*

class PersonDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: PersonDetailsViewModel
    private lateinit var personDetailsRepository: PersonDetailsRepository

    private lateinit var profilesViewModel: ProfilesViewModel
    private lateinit var profilesListRepository: ProfilesListRepository

    val profilesAdapter = ProfilesListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_details)

        val personId: Int = intent.getIntExtra("id", 1)

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        personDetailsRepository = PersonDetailsRepository(apiService)

        viewModel = getPersonViewModel(personId)

        viewModel.personDetails.observe(this, Observer {
            bindUI(it)

        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })

        profilesListRepository = ProfilesListRepository(apiService)

        profilesViewModel = getProfilesViewModel(personId)

        profilesViewModel.profilesList.observe(this, Observer {
            profilesAdapter.submitList(it.profiles)

            Log.d("RV", "Bound" + it.profiles[0].filePath)

        })

        setupRV()

    }

    fun setupRV() {

        val gridLayoutManager = GridLayoutManager(this, 4)

        rv_profiles.layoutManager = gridLayoutManager
        rv_profiles.setHasFixedSize(false)
        rv_profiles.adapter = profilesAdapter
    }

    private fun bindUI(it: PersonDetails) {
        Log.d("BindUI", "Arrived");
        name.text = it.name
        known_for.text = it.knownForDepartment
        birth_day.text = it.birthday
        birth_place.text = it.placeOfBirth
        biography.text = it.biography
        gender.text = if (it.gender == 2) "Male" else "Female"


        val personPosterURL: String = POSTER_BASE_URL + it.profilePath
        Glide.with(this)
            .load(personPosterURL)
            .into(iv_profile_image)
//        Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()

    }

    private fun getPersonViewModel(personId: Int): PersonDetailsViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return PersonDetailsViewModel(personDetailsRepository, personId) as T
            }
        })[PersonDetailsViewModel::class.java]
    }

    private fun getProfilesViewModel(personId: Int): ProfilesViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ProfilesViewModel(profilesListRepository, personId) as T
            }
        })[ProfilesViewModel::class.java]
    }
}
