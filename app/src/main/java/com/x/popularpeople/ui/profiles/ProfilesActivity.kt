package com.x.popularpeople.ui.profiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.x.popularpeople.R
import com.x.popularpeople.api.ORIGINAL_POSTER_BASE_URL
import com.x.popularpeople.api.TheMovieDBClient
import com.x.popularpeople.api.TheMovieDBInterface
import com.x.popularpeople.model.Profiles
import com.x.popularpeople.ui.popular_people.PopularPeoplePagedListAdapter
import kotlinx.android.synthetic.main.activity_popular_people.*
import kotlinx.android.synthetic.main.activity_profiles.*

class ProfilesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiles)

        val imagePath: String? = ORIGINAL_POSTER_BASE_URL + intent.getStringExtra("path")

        Glide.with(this).load(imagePath).into(profile_image)

    }

}
