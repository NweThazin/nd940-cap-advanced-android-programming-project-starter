package com.example.android.politicalpreparedness.election

import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.models.ElectionResponse
import com.example.android.politicalpreparedness.network.models.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ElectionRepository(private val apiService: CivicsApiService) {

    suspend fun getElections(): Resource<ElectionResponse>? {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.getElections()
                Resource.success(result)
            } catch (ex: Exception) {
                Timber.e(ex)
                Resource.error(null, ex.message ?: "Unexpected Error Occurred")
            }
        }
    }
}