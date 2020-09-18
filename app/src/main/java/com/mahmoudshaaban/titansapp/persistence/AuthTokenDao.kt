package com.mahmoudshaaban.titansapp.persistence

import androidx.room.*
import com.mahmoudshaaban.titansapp.models.AuthToken

@Dao
interface AuthTokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(authToken: AuthToken): Long

    @Query("UPDATE auth_token SET token = null WHERE account_pk = :pk")
    fun nullifyToken(pk: Int): Int


}

