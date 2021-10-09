package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ViewHolderElectionBinding
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.util.DateTimeUtil

class ElectionListAdapter(private val clickListener: ElectionListener) :
    ListAdapter<Election, ElectionViewHolder>(ElectionDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(parent, clickListener)
    }

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class ElectionViewHolder(
    val binding: ViewHolderElectionBinding,
    private val listener: ElectionListener
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(view: ViewGroup, listener: ElectionListener): ElectionViewHolder {
            return ElectionViewHolder(
                ViewHolderElectionBinding.inflate(
                    LayoutInflater.from(view.context),
                    view,
                    false
                ),
                listener
            )
        }
    }

    fun bind(item: Election) {
        binding.item = item
        binding.tvTime.text = DateTimeUtil.convertToEDTFormat(item.electionDay)
        binding.executePendingBindings()

        binding.layoutElection.setOnClickListener {
            listener.onClickElection(item)
        }
    }
}

class ElectionDiffCallback : DiffUtil.ItemCallback<Election>() {
    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem == newItem
    }

}

interface ElectionListener {
    fun onClickElection(item: Election)
}