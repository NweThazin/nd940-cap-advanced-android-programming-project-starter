package com.example.android.politicalpreparedness.representative

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import com.google.android.gms.location.LocationServices
import java.util.Locale

class DetailFragment : Fragment() {

    companion object {
        // Add Constant for Location request
        private const val REQUEST_LOCATION_PERMISSION = 100
    }

    // Declare variables
    private lateinit var viewModel: RepresentativeViewModel
    private lateinit var binding: FragmentRepresentativeBinding
    private lateinit var adapter: RepresentativeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // binding and view model
        binding = FragmentRepresentativeBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(RepresentativeViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.setStates(resources.getStringArray(R.array.states).toList())

        //TODO: Define and assign Representative adapter
        //TODO: Populate Representative adapter
        //TODO: Establish button listeners for field and location search
        setupAdapter()
        attachListeners()
        observeLiveData()
        return binding.root
    }

    private fun observeLiveData() {
        viewModel.errorMessage.observe(viewLifecycleOwner, {
            showToastMessage(it)
        })
        viewModel.representatives.observe(viewLifecycleOwner, { list ->
            if (!list.isNullOrEmpty()) {
                adapter.submitList(list)
            }
        })
    }

    private fun setupAdapter() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        adapter = RepresentativeListAdapter()
        binding.listMyRepresentatives.layoutManager = linearLayoutManager
        binding.listMyRepresentatives.adapter = adapter
    }

    private fun attachListeners() {
        binding.buttonLocation.setOnClickListener { populateCurrentLocation() }
        binding.buttonSearch.setOnClickListener { viewModel.searchRepresentatives() }
    }

    private fun populateCurrentLocation() {
        checkLocationPermissions()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun checkLocationPermissions(): Boolean {
        return if (isPermissionGranted()) {
            getLocation()
            true
        } else {
            requestPermission()
            false
        }
    }

    private fun requestPermission() {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            REQUEST_LOCATION_PERMISSION
        )
    }

    private fun isPermissionGranted(): Boolean {
        // Check if permission is already granted and return (true = granted, false = denied/other)
        return checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        // Get location from LocationServices
        // The geoCodeLocation method is a helper function to change the lat/long location to a human readable street address
        LocationServices.getFusedLocationProviderClient(requireContext())
            .lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                    val address = geoCodeLocation(it)
                    viewModel.setAddress(address)
                } ?: showToastMessage("Location is empty..")
            }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun geoCodeLocation(location: Location): Address {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
            .map { address ->
                Address(
                    address.thoroughfare,
                    address.subThoroughfare,
                    address.locality,
                    address.adminArea,
                    address.postalCode
                )
            }
            .first()
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

}