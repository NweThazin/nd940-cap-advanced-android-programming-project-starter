package com.example.android.politicalpreparedness.election.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
//import com.example.android.politicalpreparedness.databinding.ViewholderElectionBinding
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
class ElectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun from(view: ViewGroup): ElectionViewHolder {
            return ElectionViewHolder(view)
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