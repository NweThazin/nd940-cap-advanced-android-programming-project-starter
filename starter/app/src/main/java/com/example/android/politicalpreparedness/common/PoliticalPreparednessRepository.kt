package com.example.android.politicalpreparedness.common

import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.models.ElectionResponse
import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import com.example.android.politicalpreparedness.network.models.Resource
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import timber.log.Timber

class PoliticalPreparednessRepository(private val apiService: CivicsApiService) {

    suspend fun getElections(): Resource<ElectionResponse> {
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

    suspend fun getVoterInfo(
        address: String,
        electionId: Int
    ): Resource<VoterInfoResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getVoterInfo(
                    address,
                    electionId,
                    false
                ).awaitResponse()
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Resource.success(responseBody)
                    } else {
                        Resource.error(null, "Response body is null")
                    }
                } else {
                    // to print out the error body
                    Resource.error(null, "Unexpected Error Occurred")
                }
            } catch (ex: Exception) {
                Timber.e(ex)
                return@withContext Resource.error(null, ex.message ?: "Unexpected Error Occurred")
            }
        }
    }

    suspend fun getRepresentatives(address: String): Resource<List<Representative>> {
        return withContext(Dispatchers.IO) {
            try {
                val (offices, officials) = apiService.getRepresentatives(address)
                val representatives =
                    offices.flatMap { office -> office.getRepresentatives(officials) }
                Resource.success(representatives)
            } catch (ex: Exception) {
                Timber.e(ex)
                Resource.error(null, ex.message ?: "Unexpected Error Occurred")
            }
        }
    }
}