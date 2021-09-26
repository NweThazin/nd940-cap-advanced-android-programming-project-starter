package com.example.android.politicalpreparedness.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

//TODO: Create Factory to generate ElectionViewModel with provided election datasource
class ElectionsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ElectionsViewModel::class.java)) {
            return ElectionsViewModel() as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}