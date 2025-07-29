package com.example.viewsplayground

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.viewsplayground.flowerList.FlowerListUiState
import com.example.viewsplayground.flowerList.FlowerListViewModel
import com.example.viewsplayground.flowerList.FlowersAdapter
import com.example.viewsplayground.flowerList.HeaderAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {
    private val viewModel: FlowerListViewModel by viewModels()

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

        viewLifecycleOwner.lifecycleScope.launch {
            //STARTED时执行，STOPPED时停止
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is FlowerListUiState.Success -> {
                            flowersAdapter.submitList(uiState.flowers)
                            headerAdapter.updateFlowerCount(uiState.flowers.size)
                        }

                        is FlowerListUiState.Error -> Toast.makeText(
                            requireActivity(),
                            "Failed to load flowers",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}