package com.example.android.politicalpreparedness.election.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter

object ElectionBindingAdapter {
    @BindingAdapter("isVisible")
    @JvmStatic
    fun setIsVisible(v: View, isVisible: Boolean) {
        v.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("textIsVisible")
    @JvmStatic
    fun setTextIsVisible(textView: TextView, text: String?) {
        textView.visibility = if (text.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}


