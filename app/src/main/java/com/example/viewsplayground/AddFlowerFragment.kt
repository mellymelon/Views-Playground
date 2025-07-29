package com.example.viewsplayground

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.viewsplayground.data.Flower
import com.example.viewsplayground.flowerList.FlowerListViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import kotlin.random.Random

class AddFlowerFragment : Fragment(R.layout.add_flower_fragment), MenuProvider {
    private lateinit var flowerNameInput: TextInputEditText
    private lateinit var flowerDescriptionInput: TextInputEditText
    private lateinit var addOrEditFlowerTitle: TextView
    private var flowerId: Long? = null //nav_graph.xml里默认了-1
    private val viewModel: FlowerListViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        view.findViewById<Button>(R.id.add_flower_button).setOnClickListener { saveFlower() }
        flowerNameInput = view.findViewById(R.id.add_flower_name)
        flowerNameInput.addTextChangedListener {
            requireActivity().invalidateMenu() //触发onPrepareMenu
        }
        flowerDescriptionInput = view.findViewById(R.id.add_flower_description)
        flowerDescriptionInput.addTextChangedListener {
            requireActivity().invalidateMenu()
        }
        addOrEditFlowerTitle = view.findViewById(R.id.add_edit_flower_title)
        flowerId = arguments?.getLong("flower_id")
        if (flowerId != -1L) { //不等于默认的-1
            addOrEditFlowerTitle.text = getString(R.string.edit_flower)
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getFlowerById(flowerId!!).collect { flower ->
                        flowerNameInput.setText(flower.name)
                        flowerDescriptionInput.setText(flower.description)
                    }
                }
            }
        }
    }

    private fun saveFlower() {
        if (!allValid()) {
            Toast.makeText(
                requireActivity(),
                "Please input flower name and description",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            viewModel.insertFlower(
                Flower(
                    id = flowerId ?: Random.nextLong(),
                    name = flowerNameInput.text.toString(),
                    description = flowerDescriptionInput.text.toString(),
                    image = R.drawable.lily
                )
            )
            findNavController().popBackStack()
        }
    }

    private fun allValid(): Boolean {
        return !flowerNameInput.text.isNullOrEmpty() && !flowerDescriptionInput.text.isNullOrEmpty()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_edit_flower_fragment, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem) = when (menuItem.itemId) {
        R.id.action_save -> {
            saveFlower()
            true
        }

        else -> false
    }

    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)
        menu.findItem(R.id.action_save).isEnabled = allValid()
    }
}