package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    // Add insert query
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addElection(election: Election): Long

    // Add select all election query
    @Query("SELECT * FROM election_table")
    fun getAllElections(): LiveData<List<Election>>

    // Add select single election query
    @Query("SELECT * FROM election_table WHERE id == :electionId")
    suspend fun getElectionById(electionId: Int): Election?

    // Add delete query
    @Query("DELETE FROM election_table WHERE id == :electionId")
    suspend fun deleteElectionById(electionId: Int): Int

    // Add clear query
    @Query("DELETE FROM election_table")
    suspend fun clearAllElections()

}