package com.x.popularpeople.ui.profiles

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.x.popularpeople.R
import com.x.popularpeople.api.POSTER_BASE_URL
import com.x.popularpeople.model.People
import com.x.popularpeople.model.Profile
import com.x.popularpeople.repository.NetworkState
import com.x.popularpeople.ui.person_details.PersonDetailsActivity
import kotlinx.android.synthetic.main.network_state_item.view.*
import kotlinx.android.synthetic.main.people_item.view.*

class ProfilesListAdapter(private val context: Context) :
    ListAdapter<Profile, RecyclerView.ViewHolder>(ProfileDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View

        view = layoutInflater.inflate(R.layout.profile_item, parent, false)
        return ProfileItemViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as ProfileItemViewHolder).bind(getItem(position), context)

    }


    class ProfileDiffCallback : DiffUtil.ItemCallback<Profile>() {
        override fun areItemsTheSame(oldItem: Profile, newItem: Profile): Boolean {
            return oldItem.filePath == newItem.filePath
        }

        override fun areContentsTheSame(oldItem: Profile, newItem: Profile): Boolean {
            return oldItem == newItem
        }

    }

    class ProfileItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(profile: Profile?, context: Context) {

            val profileURL: String = POSTER_BASE_URL + profile?.filePath
            Glide.with(itemView.context).load(profileURL).into(itemView.cv_iv_person_profile)

            itemView.setOnClickListener {
                val intent = Intent(context, ProfilesActivity::class.java)
                intent.putExtra("path", profile?.filePath)
                context.startActivity(intent)
            }
        }
    }
}