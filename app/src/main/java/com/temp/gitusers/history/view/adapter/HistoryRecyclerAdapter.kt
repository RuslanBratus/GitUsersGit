package com.temp.gitusers.history.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.temp.gitusers.history.model.HistoryUser
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.temp.gitusers.R

class HistoryRecyclerAdapter: ListAdapter<HistoryUser, HistoryRecyclerAdapter.HistoryUserViewHolder>
    (HistoryUsersComparator) {

    inner class HistoryUserViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val image = view.findViewById<ImageView>(R.id.rawImage)
        private val name = view.findViewById<TextView>(R.id.rawLogin)
        private val email = view.findViewById<TextView>(R.id.rawId)

        fun bind(historyUser: HistoryUser) {
            Glide.with(itemView.context)
                .load(historyUser.avatarUrl)
                .into(image)
            name.text = historyUser.name
            email.text = historyUser.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryUserViewHolder {
        return HistoryUserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.raw_simple_user, parent, false))
    }

    override fun onBindViewHolder(holder: HistoryUserViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        val HistoryUsersComparator = object : DiffUtil.ItemCallback<HistoryUser>() {
            override fun areContentsTheSame(oldItem: HistoryUser, newItem: HistoryUser): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: HistoryUser, newItem: HistoryUser): Boolean =
                oldItem == newItem
        }
    }

}