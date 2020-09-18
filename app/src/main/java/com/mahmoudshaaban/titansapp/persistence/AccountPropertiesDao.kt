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
    @Query("SELECT * FROM account_properties WHERE email = :email")
    suspend fun searchByEmail(email: String): AccountProperties?

    @Query("SELECT * FROM account_properties WHERE pk = :pk")
    fun searchByPk(pk: Int): LiveData<AccountProperties>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndReplace(accountProperties: AccountProperties): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOrIgnore(accountProperties: AccountProperties): Long

}