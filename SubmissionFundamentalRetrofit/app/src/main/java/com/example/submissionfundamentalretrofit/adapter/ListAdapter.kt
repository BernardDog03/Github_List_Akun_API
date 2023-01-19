package com.example.submissionfundamentalretrofit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionfundamentalretrofit.data.remote.UserResponseItem
import com.example.submissionfundamentalretrofit.databinding.ListUserBinding
import com.example.submissionfundamentalretrofit.utils.SearchDiffUtil

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private var listUser = ArrayList<UserResponseItem>()

    private var onItemClickCallback: OnItemClickCallback? = null

    class ListViewHolder(val binding: ListUserBinding): RecyclerView.ViewHolder(binding.root)

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.binding.tvUsername.text = listUser[position].login
        holder.binding.tvType.text = listUser[position].type
        Glide.with(holder.itemView.context)
            .load(listUser[position].avatarUrl)
            .circleCrop()
            .into(holder.binding.imgList)
        holder.itemView.setOnClickListener{
            onItemClickCallback?.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(userResponseItem: UserResponseItem)
    }

    override fun getItemCount(): Int = listUser.size

    fun setData(newUserList: ArrayList<UserResponseItem>){
        val diffUtil = SearchDiffUtil(listUser, newUserList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        listUser = newUserList
        diffResult.dispatchUpdatesTo(this)
    }
}