package com.example.android.politicalpreparedness.election


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.ElectionResponse
import com.example.android.politicalpreparedness.network.models.Resource
import com.example.android.politicalpreparedness.network.models.Status
import kotlinx.coroutines.launch
import retrofit2.Call

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel : ViewModel() {

    private val repository = ElectionRepository(CivicsApi.retrofitService)

    // Create live data val for upcoming elections
    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>> = _upcomingElections

    // Create live data val for saved elections
    private val _savedElections = MutableLiveData<List<Election>>()
    val savedElections: LiveData<List<Election>> = _savedElections

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    fun fetchElections() {
        _status.value = Status.LOADING
        viewModelScope.launch {
            repository.getElections()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        _upcomingElections.postValue(result.data?.elections)
                        _status.postValue(Status.SUCCESS)
                    }
                    Status.ERROR -> {
                        _status.postValue(Status.ERROR)
                    }
                    else -> {
                        // do nothing
                    }
                }
            }
        }
    }

    //TODO: Create functions to navigate to saved or upcoming election voter info

}