package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ViewHolderElectionBinding
import com.example.android.politicalpreparedness.network.models.Election

class ElectionListAdapter(private val clickListener: ElectionListener) :
    ListAdapter<Election, ElectionViewHolder>(ElectionDiffCallback()) {

    //TODO: Add companion object to inflate ViewHolder (from)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(parent)
    }

    //TODO: Bind ViewHolder
    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}

//TODO: Create ElectionViewHolder
class ElectionViewHolder(val binding: ViewHolderElectionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(view: ViewGroup): ElectionViewHolder {
            return ElectionViewHolder(
                ViewHolderElectionBinding.inflate(
                    LayoutInflater.from(view.context),
                    view,
                    false
                )
            )
        }
    }
}

//TODO: Create ElectionDiffCallback
class ElectionDiffCallback : DiffUtil.ItemCallback<Election>() {
    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
        return true
    }

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
        return false
    }

}

//TODO: Create ElectionListener
interface ElectionListener {

}