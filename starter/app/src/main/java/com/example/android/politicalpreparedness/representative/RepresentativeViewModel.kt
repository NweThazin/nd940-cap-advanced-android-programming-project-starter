package com.example.android.politicalpreparedness.representative

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.common.PoliticalPreparednessRepository
import com.example.android.politicalpreparedness.common.SingleLiveEvent
import com.example.android.politicalpreparedness.common.model.ButtonState
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.network.models.Status
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch

class RepresentativeViewModel() : ViewModel() {

    private val repository = PoliticalPreparednessRepository(CivicsApi.retrofitService)

    // Establish live data for representatives and address
    private val _representatives = MutableLiveData<List<Representative>>()
    val representatives: LiveData<List<Representative>> = _representatives

    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address> = _address

    // Create function get address from geo location
    fun setAddress(address: Address) {
        _address.value = address
    }

    private val _states = MutableLiveData<List<String>>()
    val states: LiveData<List<String>> = _states
    fun setStates(list: List<String>) {
        _states.value = list
    }

    val selectedStatePosition = ObservableField<Int>()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _buttonState = SingleLiveEvent<ButtonState>().apply { value = ButtonState.ENABLE }
    val buttonState: LiveData<ButtonState> = _buttonState

    fun searchRepresentatives() {
        if (address.value == null) {
            _errorMessage.value = "Please add address"
            return
        }

        _buttonState.value = ButtonState.DISABLE
        selectedStatePosition.get()?.let { position ->
            states.value?.get(position)
        }?.apply {
            address.value?.state = this
        }
        // make api call
        address.value?.let {
            fetchRepresentatives(it)
        }
    }

    // Create function to fetch representatives from API from a provided address
    private fun fetchRepresentatives(address: Address) {
        viewModelScope.launch {
            val response = repository.getRepresentatives(address.toFormattedString())
            when (response.status) {
                Status.SUCCESS -> {
                    _representatives.postValue(response.data)
                    _buttonState.postValue(ButtonState.ENABLE)
                }
                Status.ERROR -> {
                    _errorMessage.postValue(response.message)
                    _buttonState.postValue(ButtonState.ENABLE)
                }
                else -> {
                    _buttonState.postValue(ButtonState.ENABLE)
                }
            }

        }
    }

    //TODO: Create function to get address from individual fields

}
