package com.example.mvvm_assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvm_assignment.databinding.ItemLayoutBinding
import com.example.mvvm_assignment.model.User

class UserAdapter(
    private var users: ArrayList<User>
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(users[position]) {
                binding.textViewUserName.text = this.name
                binding.textViewUserEmail.text = this.email
                Glide.with(binding.imageViewAvatar.context)
                    .load(this.avatar)
                    .into(binding.imageViewAvatar)
            }
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun addData(list: List<User>) {
        users.addAll(list)
    }


}