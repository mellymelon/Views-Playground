package com.example.viewsplayground

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
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.viewsplayground.flowerList.FlowerListViewModel
import kotlinx.coroutines.launch

class DetailFragment : Fragment(R.layout.detail_fragment), MenuProvider {
    private val viewModel: FlowerListViewModel by activityViewModels()
    var flowerId: Long? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.detail_text_view)
        val imageView = view.findViewById<ImageView>(R.id.detail_image_view)
        val descriptionView = view.findViewById<TextView>(R.id.flower_detail_description)
        flowerId = arguments?.getLong("flower_id")
        flowerId?.let {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getFlowerById(it).collect { flower ->
                        textView.text = flower.name
                        imageView.setImageResource(flower.image ?: R.drawable.rose)
                        descriptionView.text = flower.description
                    }
                }
            }
        }
        //绑定删除事件
        flowerId?.let { id ->
            view.findViewById<Button>(R.id.remove_flower_button).setOnClickListener {
                val builder = AlertDialog.Builder(requireActivity())
                builder.setMessage("This flower will be removed from the list")
                builder.setTitle("Remove this flower?")
                builder.setPositiveButton("Confirm") { dialog, which ->
                    viewModel.removeFlower(id)
                    findNavController().popBackStack()
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
            val bundle = bundleOf(
                "flower_id" to flowerId,
                "titleAddOrEditFlower" to getString(R.string.edit_flower)
            )
            findNavController().navigate(R.id.navigate_to_add_flower, bundle)
            true
        }

        R.id.action_detail_share -> {
            Toast.makeText(requireActivity(), "分享...", Toast.LENGTH_SHORT).show()
            true
        }

        else -> false
    }
}