package com.example.android.politicalpreparedness.representative

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.politicalpreparedness.MainApplication
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative

class RepresentativeViewModel() : ViewModel() {

    // Establish live data for representatives and address
    private val _representatives = MutableLiveData<List<Representative>>()
    val representatives: LiveData<List<Representative>> = _representatives

    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address> = _address
    fun setAddress(address: Address) {
        _address.value = address
    }

    private val _states = MutableLiveData<List<String>>()
    val states: LiveData<List<String>> = _states
    fun setStates(list: List<String>) {
        _states.value = list
    }

    val selectedStatePosition = ObservableField<Int>()


    fun searchRepresentatives() {
        selectedStatePosition.get()?.let { position ->
            states.value?.get(position)
        }?.apply {
            address.value?.state = this
        }
        // make api call
        println(address.value)
        println(selectedStatePosition)
    }


    //TODO: Create function to fetch representatives from API from a provided address

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location

    //TODO: Create function to get address from individual fields

}
