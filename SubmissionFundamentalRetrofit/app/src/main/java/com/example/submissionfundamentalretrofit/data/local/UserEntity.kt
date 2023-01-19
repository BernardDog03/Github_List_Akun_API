package com.example.submissionfundamentalretrofit.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favoriteUser")
@Parcelize

data class UserEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey(autoGenerate = false)
    var id: String,

    @field:ColumnInfo(name = "username")
    var username: String,

    @field:ColumnInfo(name = "type")
    var type: String,

    @field:ColumnInfo(name = "avatarUrl")
    var avatar: String,

    @field:ColumnInfo(name = "favorite")
    var isFavorite: Boolean
) : Parcelable