package com.example.viewsplayground

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.viewsplayground.data.Flower
import com.example.viewsplayground.data.FlowerDataSource

class DetailFragment : Fragment(R.layout.detail_fragment), MenuProvider {
    private val viewModel by viewModels<FlowerDetailViewModel> {
        FlowerDetailViewModelFactory(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.detail_text_view)
        val imageView = view.findViewById<ImageView>(R.id.detail_image_view)
        val descriptionView = view.findViewById<TextView>(R.id.flower_detail_description)
        val flowerId = arguments?.getLong("flower_id")
        flowerId?.let {
            val flower = viewModel.getFlowerById(it)
            textView.text = flower?.name
            imageView.setImageResource(flower?.image ?: R.drawable.rose)
            descriptionView.text = flower?.description

            view.findViewById<Button>(R.id.remove_flower_button).setOnClickListener {
                val builder = AlertDialog.Builder(requireActivity())
                builder.setMessage("This flower will be removed from the list")
                builder.setTitle("Remove this flower?")
                builder.setPositiveButton("Confirm") { dialog, which ->
                    if (flower != null) {
                        viewModel.removeFlower(flower)
                        findNavController().popBackStack()
                    }
                }.setNegativeButton("Cancel") { dialog, which ->

                }
                builder.create().show()
            }
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_detail_fragment, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem) = when (menuItem.itemId) {
        R.id.action_detail_edit -> {
            Toast.makeText(requireActivity(), "编辑...", Toast.LENGTH_SHORT).show()
            true
        }

        R.id.action_detail_share -> {
            Toast.makeText(requireActivity(), "分享...", Toast.LENGTH_SHORT).show()
            true
        }

        else -> {
            false
        }
    }
}

class FlowerDetailViewModel(private val dataSource: FlowerDataSource) : ViewModel() {
    fun getFlowerById(id: Long): Flower? {
        return dataSource.getFlowerById(id)
    }

    fun removeFlower(flower: Flower) {
        dataSource.removeFlower(flower)
    }
}

class FlowerDetailViewModelFactory(private val ctx: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(FlowerDetailViewModel::class.java)) {
            return FlowerDetailViewModel(dataSource = FlowerDataSource.getDataSource(ctx.resources)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}