package com.example.submissionfundamentalretrofit.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.submissionfundamentalretrofit.data.remote.UserResponseItem

class SearchDiffUtil(

    private val oldList: List<UserResponseItem>,
    private val newList: List<UserResponseItem>
): DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].login == newList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val mOldList = oldList[oldItemPosition]
        val mNewList = newList[newItemPosition]

        return mOldList.login == mNewList.login && mOldList.avatarUrl == mNewList.avatarUrl && mOldList.type == mNewList.type
    }
}