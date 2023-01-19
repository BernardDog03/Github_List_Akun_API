package com.example.submissionfundamentalretrofit.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM favoriteUser WHERE favorite = 1 ")
    fun getFavoriteUser(): Flow<List<UserEntity>>

    @Query("SELECT EXISTS(SELECT * FROM favoriteUser WHERE username = :username AND favorite = 1)")
    suspend fun isFavorite(username: String): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userEntity: UserEntity)

    @Delete
    suspend fun delete(userEntity: UserEntity)

}