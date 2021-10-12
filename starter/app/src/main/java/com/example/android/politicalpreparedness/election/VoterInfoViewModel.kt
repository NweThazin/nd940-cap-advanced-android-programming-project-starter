package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.common.SingleLiveEvent
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.*
import com.example.android.politicalpreparedness.util.DateTimeUtil
import kotlinx.coroutines.launch

class VoterInfoViewModel(private val dataSource: ElectionDao) : ViewModel() {

    //TODO: Add var and methods to support loading URLs
    //TODO: Add var and methods to populate voter info
    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

    private val repository = ElectionRepository(CivicsApi.retrofitService)

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status

    // Add live data to hold voter info
    private val _voterInfo = MutableLiveData<VoterInfoResponse>()
    val voterInfo: LiveData<VoterInfoResponse> = _voterInfo

    private val _address = MutableLiveData<String>()
    val address: LiveData<String> = _address

    sealed class ActionState {
        class ShowIntentUrl(val url: String) : ActionState()
        class UpdateElectionInfo(val electionDate: String) : ActionState()
        class UpdateStateInfo(val correspondenceHeader: String) : ActionState()
    }


    private val _actionState = SingleLiveEvent<ActionState>()
    val actionState: LiveData<ActionState> = _actionState

    fun populateVoterInfo(electionId: Int, division: Division) {
        loadVoterInfo(
            address = "${division.state},${division.country}",
            electionId = electionId
        )
    }

    private fun loadVoterInfo(address: String, electionId: Int) {
        _status.value = Status.LOADING
        viewModelScope.launch {
            val response = repository.getVoterInfo(address, electionId)
            when (response.status) {
                Status.SUCCESS -> {
                    // populate UI info
                    populateElectionInfo(response.data?.election)
                    populateStateInfo(response.data?.state)

                    _voterInfo.postValue(response.data)
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

    private fun populateElectionInfo(election: Election?) {
        election?.let {
            _actionState.postValue(
                ActionState.UpdateElectionInfo(
                    DateTimeUtil.convertToEDTFormat(it.electionDay)
                )
            )
        }
    }

    private fun populateStateInfo(states: List<State>?) {
        if (!states.isNullOrEmpty()) {
            // state locations
            _address.postValue(states.mapNotNull { state -> state.electionAdministrationBody.correspondenceAddress?.toFormattedString() }
                .joinToString("\n"))

            _actionState.value = ActionState.UpdateStateInfo(
                correspondenceHeader = states[0].electionAdministrationBody.name ?: ""
            )
        }
    }

    private fun getFirstState(states: List<State>?): State? {
        return if (!states.isNullOrEmpty()) {
            states[0]
        } else {
            null
        }
    }

    fun showVotingLocations() {
        getFirstState(voterInfo.value?.state)?.electionAdministrationBody?.votingLocationFinderUrl?.also { url ->
            _actionState.value = ActionState.ShowIntentUrl(url)
        }
    }

    fun showBallotInformation() {
        getFirstState(voterInfo.value?.state)?.electionAdministrationBody?.ballotInfoUrl?.also { url ->
            _actionState.value = ActionState.ShowIntentUrl(url)
        }
    }


}