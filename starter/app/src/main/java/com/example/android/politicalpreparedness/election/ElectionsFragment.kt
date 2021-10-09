package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import com.example.android.politicalpreparedness.network.models.Election

class ElectionsFragment : Fragment() {

    private lateinit var viewModel: ElectionsViewModel

    private lateinit var binding: FragmentElectionBinding

    private lateinit var adapter: ElectionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_election, container, false)

        // Add ViewModel values and create ViewModel
        viewModel = ViewModelProvider(
            this,
            ElectionsViewModelFactory()
        ).get(ElectionsViewModel::class.java)

        binding.lifecycleOwner = this

        // Add binding values
        binding.viewModel = viewModel

        //TODO: Link elections to voter info

        // Initiate recycler adapters
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = ElectionListAdapter(object : ElectionListener {
            override fun onClickElection(item: Election) {
                println(item)
            }
        })
        binding.listUpcomingElections.layoutManager = layoutManager
        binding.listUpcomingElections.adapter = adapter

        val savedLayoutManager = LinearLayoutManager(requireContext())
        val savedElectionAdapter = ElectionListAdapter(object : ElectionListener {
            override fun onClickElection(item: Election) {
                println("do something")
            }
        })
        binding.listSavedElections.layoutManager = savedLayoutManager
        binding.listSavedElections.adapter = savedElectionAdapter

        //TODO: Populate recycler adapters
        fetchElections()
        observeLiveData()

        return binding.root
    }

    private fun fetchElections() {
        viewModel.fetchElections()
    }

    private fun observeLiveData() {
        viewModel.upcomingElections.observe(viewLifecycleOwner, { list ->
            adapter.submitList(list)
        })
    }

    //TODO: Refresh adapters when fragment loads

}