package com.mahmoudshaaban.titansapp.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahmoudshaaban.titansapp.models.AccountProperties


// For email , userName , password , Unique authToken , primaryKey For user
@Dao
interface AccountPropertiesDao {

    // Replace mean if the row already exists replace it with the data we come with that
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(accountProperties: AccountProperties): Long

    // Ignore mean if the row already exists Ignore the data we come from
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOrIgnore(accountProperties: AccountProperties): Long

    @Query("SELECT * FROM account_properties WHERE pk = :pk")
    fun searchByPk(pk: Int): AccountProperties?

    @Query("SELECT * FROM account_properties WHERE email = :email")
    fun searchByEmail(email: String): AccountProperties?

}