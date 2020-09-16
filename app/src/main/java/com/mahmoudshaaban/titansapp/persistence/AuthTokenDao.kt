package com.mahmoudshaaban.titansapp.persistence

import androidx.room.*
import com.mahmoudshaaban.titansapp.models.AuthToken

@Dao
interface AuthTokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuth(authToken: AuthToken)

    @Query("UPDATE auth_token SET token = NULL WHERE account_pk = :pk")
    fun nullifyToken(pk: Int): Int

}