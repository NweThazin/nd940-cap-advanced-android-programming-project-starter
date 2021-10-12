package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding

class VoterInfoFragment : Fragment() {

    private lateinit var binding: FragmentVoterInfoBinding
    private lateinit var viewModel: VoterInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Add ViewModel values and create ViewModel
        viewModel = ViewModelProvider(
            this,
            VoterInfoViewModelFactory(ElectionDatabase.getInstance(requireContext()).electionDao)
        ).get(VoterInfoViewModel::class.java)

        // Add binding values
        binding = FragmentVoterInfoBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupParamsValue()
        observeLiveData()
        attachedListeners()

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
         */
        //TODO: Handle loading of URLs
        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks

        return binding.root
    }

    private fun attachedListeners() {
        binding.stateLocations.setOnClickListener {
            viewModel.showVotingLocations()
        }
        binding.stateBallot.setOnClickListener {
            viewModel.showBallotInformation()
        }
        binding.followElectionButton.setOnClickListener {
            // do to
        }
    }

    private fun setupParamsValue() {
        arguments?.let {
            val electionId = VoterInfoFragmentArgs.fromBundle(it).argElectionId
            val division = VoterInfoFragmentArgs.fromBundle(it).argDivision
            viewModel.populateVoterInfo(electionId, division)
        }
    }

    private fun observeLiveData() {
        viewModel.actionState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is VoterInfoViewModel.ActionState.ShowIntentUrl -> {
                    openIntentUrl(state.url)
                }
                is VoterInfoViewModel.ActionState.UpdateElectionInfo -> {
                    binding.electionDate.text = state.electionDate
                }
                is VoterInfoViewModel.ActionState.UpdateStateInfo -> {
                    binding.stateCorrespondenceHeader.isVisible =
                        state.correspondenceHeader.isNotEmpty()
                    binding.stateCorrespondenceHeader.text = state.correspondenceHeader
                }
                else -> {
                    // do nothing
                }
            }

        })
    }

    //TODO: Create method to load URL intents
    private fun openIntentUrl(url: String) {
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
            startActivity(this)
        }
    }
}


