package com.mvvm.ui.activity.main

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mvvm.data.model.UserList
import com.mvvm.databinding.ItemUsersBinding
import com.mvvm.ui.activity.user.UserActivity

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.MainViewHolder>() {

    var movieList = mutableListOf<UserList>()

    @SuppressLint("NotifyDataSetChanged")
    fun setUsers(movies: List<UserList>) {
        this.movieList = movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUsersBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val users = movieList[position]
        holder.setData(users)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MainViewHolder(itemBinding: ItemUsersBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val binding = itemBinding

        fun setData(users: UserList) {
            binding.name.text = users.name
            Glide.with(itemView.context).load(users.avatar).into(binding.imageview)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, UserActivity::class.java)
                intent.putExtra("LOGIN", users.name)
                itemView.context.startActivity(intent)
            }
        }
    }
}

