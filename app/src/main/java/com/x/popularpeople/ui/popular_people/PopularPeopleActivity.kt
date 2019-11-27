package com.x.popularpeople.ui.popular_people

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.x.popularpeople.R
import com.x.popularpeople.api.TheMovieDBClient
import com.x.popularpeople.api.TheMovieDBInterface
import com.x.popularpeople.repository.NetworkState
import kotlinx.android.synthetic.main.activity_popular_people.*


class PopularPeopleActivity : AppCompatActivity() {

    private lateinit var viewModel: PopularPeopleViewModel

    lateinit var peopleRepository: PeoplePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_people)

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()

        peopleRepository = PeoplePagedListRepository(apiService)

        var searchQuery =
            if (intent.getStringExtra("query") == null) ""
            else intent.getStringExtra("query")

        viewModel = getViewModel(searchQuery)

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

        search.setOnClickListener {
            showSearchDialog()
        }

    }

    private fun showSearchDialog() {
        val editText = EditText(this)
        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle("Enter person name")
            .setView(editText)
            .setPositiveButton(
                "Search"
            ) { _, _ ->
                val query = editText.text.toString()
                val intent = Intent(this, PopularPeopleActivity::class.java)
                intent.putExtra("query", query)
                startActivity(intent)
            }
            .create()
        dialog.show()
    }


    private fun getViewModel(query: String): PopularPeopleViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return PopularPeopleViewModel(peopleRepository, query) as T
            }
        })[PopularPeopleViewModel::class.java]
    }
}