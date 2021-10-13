package com.example.android.politicalpreparedness.election.enums

import androidx.annotation.StringRes
import com.example.android.politicalpreparedness.R

enum class ElectionButtonState(@StringRes val label: Int) {
    FOLLOW_ELECTION(R.string.label_follow_election),
    UNFOLLOW_ELECTION(R.string.label_unfollow_election)
}