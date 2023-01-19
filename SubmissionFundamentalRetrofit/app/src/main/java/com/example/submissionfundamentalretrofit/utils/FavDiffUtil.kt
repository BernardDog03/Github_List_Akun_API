package com.example.submissionfundamentalretrofit.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.submissionfundamentalretrofit.data.local.UserEntity
import com.example.submissionfundamentalretrofit.data.remote.UserResponseItem

class FavDiffUtil(

    private val oldList: List<UserEntity>,
    private val newList: List<UserEntity>
): DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].username == newList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val mOldList = oldList[oldItemPosition]
        val mNewList = newList[newItemPosition]

        return mOldList.username == mNewList.username
    }
}