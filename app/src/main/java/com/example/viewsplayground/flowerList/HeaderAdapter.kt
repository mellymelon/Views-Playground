package com.example.viewsplayground.flowerList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.viewsplayground.R

class HeaderAdapter : RecyclerView.Adapter<HeaderAdapter.ViewHolder>() {
    private var flowerCount = 0

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val countTextView = itemView.findViewById<TextView>(R.id.flower_count_text_view)

        fun bind(flowerCount: Int) {
            countTextView.text = flowerCount.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.header_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(flowerCount)
    }

    override fun getItemCount() = 1

    fun updateFlowerCount(updatedCount: Int) {
        flowerCount = updatedCount
        notifyDataSetChanged()
    }
}