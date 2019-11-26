package com.x.popularpeople.ui.profiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.x.popularpeople.R
import com.x.popularpeople.api.TheMovieDBClient
import com.x.popularpeople.api.TheMovieDBInterface
import com.x.popularpeople.model.Profiles
import com.x.popularpeople.ui.popular_people.PopularPeoplePagedListAdapter
import kotlinx.android.synthetic.main.activity_popular_people.*
import kotlinx.android.synthetic.main.activity_profiles.*

class ProfilesActivity : AppCompatActivity() {

    private lateinit var profilesViewModel: ProfilesViewModel
    private lateinit var profilesListRepository: ProfilesListRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiles)


        val personId: Int = 1245

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        profilesListRepository = ProfilesListRepository(apiService)

        profilesViewModel = getViewModel(personId)

        profilesViewModel.profilesList.observe(this, Observer {
            bindUI(it)

        })


        setupRV()


    }

    val profilesAdapter = ProfilesListAdapter(this)
    fun setupRV(){


        val gridLayoutManager = GridLayoutManager(this, 4)

//        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                val viewType: Int = profilesAdapter.getItemViewType(position)
//                return if (viewType == profilesAdapter.PEOPLE_VIEW_TYPE) 1
//                else 2
//            }
//        }

        rv_profiles.layoutManager = gridLayoutManager
        rv_profiles.setHasFixedSize(true)
        rv_profiles.adapter = profilesAdapter
    }


    private fun bindUI(it: Profiles) {
        Log.d("BindUI", "Arrived")
        Log.d("BindUI", it.profiles[0].filePath)
        Log.d("BindUI", it.profiles[1].filePath)
        Log.d("BindUI", it.profiles[2].filePath)

        profilesAdapter.submitList(it.profiles)

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
