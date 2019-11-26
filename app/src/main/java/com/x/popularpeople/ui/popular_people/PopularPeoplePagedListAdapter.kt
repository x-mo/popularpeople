package com.x.popularpeople.ui.popular_people

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.x.popularpeople.R
import com.x.popularpeople.api.POSTER_BASE_URL
import com.x.popularpeople.model.People
import com.x.popularpeople.repository.NetworkState
import com.x.popularpeople.ui.person_details.PersonDetailsActivity
import kotlinx.android.synthetic.main.network_state_item.view.*
import kotlinx.android.synthetic.main.people_item.view.*

class PopularPeoplePagedListAdapter(public val context: Context) :
    PagedListAdapter<People, RecyclerView.ViewHolder>(PeopleDiffCallback()) {

    val PEOPLE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == PEOPLE_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.people_item, parent, false)
            return PeopleItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == PEOPLE_VIEW_TYPE) {
            (holder as PeopleItemViewHolder).bind(getItem(position), context)
        } else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }

    }


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1)
            NETWORK_VIEW_TYPE
        else
            PEOPLE_VIEW_TYPE

    }

    class PeopleDiffCallback : DiffUtil.ItemCallback<People>() {
        override fun areItemsTheSame(oldItem: People, newItem: People): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: People, newItem: People): Boolean {
            return oldItem == newItem
        }

    }

    class PeopleItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(people: People?, context: Context) {
            itemView.cv_person_name.text = people?.name
            itemView.cv_person_role.text = people?.knownForDepartment

            val peopleProfileURL: String = POSTER_BASE_URL + people?.profilePath
            Glide.with(itemView.context).load(peopleProfileURL).into(itemView.cv_iv_person_profile)

            itemView.setOnClickListener {
                val intent = Intent(context, PersonDetailsActivity::class.java)
                intent.putExtra("id", people?.id)
                context.startActivity(intent)
            }
        }
    }

    class NetworkStateItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(networkState: NetworkState?) {
            if (networkState != null && networkState == NetworkState.LOADING)
                itemView.progress_bar_item.visibility = View.VISIBLE
            else
                itemView.progress_bar_item.visibility = View.GONE


            if (networkState != null && networkState == NetworkState.ERROR) {
                itemView.error_msg_item.visibility = View.VISIBLE
                itemView.error_msg_item.text = networkState.msg
            } else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
                itemView.error_msg_item.visibility = View.VISIBLE
                itemView.error_msg_item.text = networkState.msg
            } else
                itemView.error_msg_item.visibility = View.GONE

        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState: NetworkState? = this.networkState
        val hadExtraRow: Boolean = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow: Boolean = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }
}