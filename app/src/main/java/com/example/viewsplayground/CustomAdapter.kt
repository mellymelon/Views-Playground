package com.example.viewsplayground

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val dataSet: Array<String>, private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View, val onClick: (String) -> Unit) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_text_view)
        var curStr: String? = null

        init {
            view.setOnClickListener {
                curStr?.let { onClick(it) }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.text_row_item, parent, false)
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.curStr = dataSet[position]
        holder.textView.text = dataSet[position]
    }

    override fun getItemCount() = dataSet.size
}