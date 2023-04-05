package com.temp.gitusers.main.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.temp.gitusers.R
import com.temp.gitusers.main.model.RawUser
import com.temp.gitusers.main.view.adapter.clickListener.OnUserClickListener

class MainRecyclerAdapter(private val clickListener: OnUserClickListener) :
    PagingDataAdapter<RawUser, MainRecyclerAdapter.UsersViewHolder>(UsersComparator) {


    inner class UsersViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val login: TextView = view.findViewById(R.id.rawLogin)
        private val image: ImageView = view.findViewById(R.id.rawImage)
        private val id: TextView = view.findViewById(R.id.rawId)

        fun bindImage(item: RawUser) {
            login.text = item.login
            id.text = itemView.context.getString(R.string.user_id_placeholder, item.id)
            Glide.with(itemView.context)
                .load(item.avatarUrl)
                .into(image)

            itemView.setOnClickListener {
                clickListener.onClick(item.login)
            }
        }
    }

    companion object {
        val UsersComparator = object : DiffUtil.ItemCallback<RawUser>() {
            override fun areContentsTheSame(oldItem: RawUser, newItem: RawUser): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: RawUser, newItem: RawUser): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindImage(it) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.raw_simple_user, parent, false))
    }
}