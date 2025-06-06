package com.example.viewsplayground.flowerList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.viewsplayground.data.Flower
import com.example.viewsplayground.R

class FlowersAdapter(private val onClick: (Flower) -> Unit) :
    ListAdapter<Flower, FlowersAdapter.FlowerViewHolder>(FlowerDiffCallback) {
    class FlowerViewHolder(itemView: View, val onClick: (Flower) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.flower_text)
        private val imageView: ImageView = itemView.findViewById(R.id.flower_image)
        private var curFlower: Flower? = null

        init {
            itemView.setOnClickListener { curFlower?.let { onClick(it) } }
        }

        fun bind(flower: Flower) {
            curFlower = flower
            textView.text = flower.name
            imageView.setImageResource(flower.image ?: R.drawable.rose)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flower_item, parent, false)
        return FlowerViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: FlowerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object FlowerDiffCallback : DiffUtil.ItemCallback<Flower>() {
    override fun areItemsTheSame(oldItem: Flower, newItem: Flower): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Flower, newItem: Flower): Boolean {
        return oldItem.id == newItem.id
    }
}