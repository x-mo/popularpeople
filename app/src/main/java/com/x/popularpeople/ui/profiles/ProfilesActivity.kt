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

//    private lateinit var profilesViewModel: ProfilesViewModel
//    private lateinit var profilesListRepository: ProfilesListRepository
//
//    val profilesAdapter = ProfilesListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiles)


//        val personId: Int = 1245
//
//        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
//        profilesListRepository = ProfilesListRepository(apiService)
//
//        profilesViewModel = getViewModel(personId)
//
//        profilesViewModel.profilesList.observe(this, Observer {
//            profilesAdapter.submitList(it.profiles)
//
//        })
//
//        setupRV()

    }


//    fun setupRV(){
//
//        val gridLayoutManager = GridLayoutManager(this, 4)
//
//        rv_profiles.layoutManager = gridLayoutManager
//        rv_profiles.setHasFixedSize(true)
//        rv_profiles.adapter = profilesAdapter
//    }

//    private fun getViewModel(personId: Int): ProfilesViewModel {
//        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
//            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//                return ProfilesViewModel(profilesListRepository, personId) as T
//            }
//        })[ProfilesViewModel::class.java]
//    }

}
