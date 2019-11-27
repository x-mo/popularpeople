package com.x.popularpeople.ui.search_people

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.x.popularpeople.R
import com.x.popularpeople.api.TheMovieDBClient
import com.x.popularpeople.api.TheMovieDBInterface
import com.x.popularpeople.repository.NetworkState
import com.x.popularpeople.ui.popular_people.PeoplePagedListRepository
import com.x.popularpeople.ui.popular_people.PopularPeoplePagedListAdapter
import com.x.popularpeople.ui.popular_people.PopularPeopleViewModel
import kotlinx.android.synthetic.main.activity_popular_people.*

class SearchPeopleActivity : AppCompatActivity() {

    private lateinit var viewModel: PopularPeopleViewModel

    lateinit var peopleRepository: PeoplePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_people)

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()

        peopleRepository = PeoplePagedListRepository(apiService)

        viewModel = getViewModel("Jack")

        val peopleAdapter = PopularPeoplePagedListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType: Int = peopleAdapter.getItemViewType(position)
                return if (viewType == peopleAdapter.PEOPLE_VIEW_TYPE) 1
                else 2
            }
        }

        rv.layoutManager = gridLayoutManager
        rv.setHasFixedSize(true)
        rv.adapter = peopleAdapter

        viewModel.peoplePagedList.observe(this, Observer {
            peopleAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                peopleAdapter.setNetworkState(it)
            }
        })

    }
    private fun getViewModel(query: String): PopularPeopleViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return PopularPeopleViewModel(peopleRepository,query) as T
            }
        })[PopularPeopleViewModel::class.java]
    }
}
