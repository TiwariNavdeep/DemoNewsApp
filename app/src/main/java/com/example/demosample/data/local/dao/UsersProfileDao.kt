package com.example.demosample.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.demosample.data.local.entity.UsersProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersProfileDao {
    @Insert(
      onConflict = OnConflictStrategy.REPLACE
    )
    fun insertUsers(list: List<UsersProfileEntity>)

    @Query("SELECT * FROM UsersProfileEntity WHERE invitationStatus IS NOT NULL AND invitationStatus != '' ORDER BY modifiedDate DESC")
    fun getMyChoiceUsers(): Flow<List<UsersProfileEntity>>

    @Query("SELECT * FROM UsersProfileEntity WHERE invitationStatus IS NULL OR invitationStatus == '' ORDER BY modifiedDate DESC")
    fun getAllUsers(): List<UsersProfileEntity>

    @Query("""
    UPDATE USERSPROFILEENTITY
    SET invitationStatus = :status,
        modifiedDate = :modifiedDate
    WHERE id = :id
""")    fun updateInvitationStatus(id: String, status: String,modifiedDate: Long): Int
}