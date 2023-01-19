package com.example.submissionfundamentalretrofit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionfundamentalretrofit.data.local.UserEntity
import com.example.submissionfundamentalretrofit.databinding.ListUserBinding
import com.example.submissionfundamentalretrofit.utils.FavDiffUtil

class FavAdapter : RecyclerView.Adapter<FavAdapter.ListViewHolder>() {

    private var listUser = ArrayList<UserEntity>()

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
        holder.binding.tvUsername.text = listUser[position].username
        holder.binding.tvType.text = listUser[position].type
        Glide.with(holder.itemView.context)
            .load(listUser[position].avatar)
            .circleCrop()
            .into(holder.binding.imgList)
        holder.itemView.setOnClickListener{
            onItemClickCallback?.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(userEntity: UserEntity)
    }

    override fun getItemCount(): Int = listUser.size

    fun setData(newUserList: ArrayList<UserEntity>){
        val diffUtil = FavDiffUtil(listUser, newUserList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        listUser = newUserList
        diffResult.dispatchUpdatesTo(this)
    }
}