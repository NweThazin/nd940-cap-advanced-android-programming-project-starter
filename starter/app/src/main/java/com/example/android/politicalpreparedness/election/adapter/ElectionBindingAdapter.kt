package com.example.android.politicalpreparedness.election.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

object ElectionBindingAdapter {
    @BindingAdapter("isVisible")
    @JvmStatic
    fun setIsVisible(v: View, isVisible: Boolean) {
        v.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}


