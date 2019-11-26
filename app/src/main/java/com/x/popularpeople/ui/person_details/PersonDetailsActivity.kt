package com.x.popularpeople.ui.person_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.x.popularpeople.repository.NetworkState
import kotlinx.android.synthetic.main.activity_person_details.*

class PersonDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: PersonDetailsViewModel
    private lateinit var personDetailsRepository: PersonDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_details)

        val personId: Int = 1892

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        personDetailsRepository = PersonDetailsRepository(apiService)

        viewModel = getViewModel(personId)

        viewModel.personDetails.observe(this, Observer {
            bindUI(it)

        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })
    }

    private fun bindUI(it: PersonDetails) {

        name.text = it.name

        val personPosterURL: String = POSTER_BASE_URL + it.profilePath
        Glide.with(this)
            .load(personPosterURL)
            .into(iv_profile_image)
//        Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()

    }

    private fun getViewModel(personId: Int): PersonDetailsViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return PersonDetailsViewModel(personDetailsRepository, personId) as T
            }
        })[PersonDetailsViewModel::class.java]
    }
}
