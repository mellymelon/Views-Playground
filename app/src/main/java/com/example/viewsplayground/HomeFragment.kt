package com.example.viewsplayground

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment(R.layout.home_fragment) {
    //private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataset = Array(100) { i -> "$i * $i = ${i * i}" }
        val customAdapter = CustomAdapter(dataset) { str ->
            val bundle = bundleOf("detail_string" to str)
            findNavController().navigate(R.id.navigate_to_detail, bundle)
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.numberList)
        recyclerView.adapter = customAdapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }
}