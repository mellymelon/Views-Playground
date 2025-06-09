package com.example.viewsplayground

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ConcatAdapter
import com.example.viewsplayground.data.Flower
import com.example.viewsplayground.flowerList.FlowerListViewModel
import com.example.viewsplayground.flowerList.FlowerListViewModelFactory
import com.example.viewsplayground.flowerList.FlowersAdapter
import com.example.viewsplayground.flowerList.HeaderAdapter

class HomeFragment : Fragment(R.layout.home_fragment) {
    private val viewModel: FlowerListViewModel by viewModels {
        FlowerListViewModelFactory(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.numberList)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this.context,
                DividerItemDecoration.VERTICAL
            )
        )
        val headerAdapter = HeaderAdapter()
        val flowersAdapter = FlowersAdapter { flower ->
            val bundle = bundleOf("flower_id" to flower.id)
            findNavController().navigate(R.id.navigate_to_detail, bundle)
        }
        recyclerView.adapter = ConcatAdapter(headerAdapter, flowersAdapter)
        viewModel.flowersLiveData.observe(viewLifecycleOwner, {
            it?.let {
                flowersAdapter.submitList(it as MutableList<Flower>)
                headerAdapter.updateFlowerCount(it.size)
            }
        })
    }
}