package com.example.viewsplayground

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText

class AddFlowerFragment : Fragment(R.layout.add_flower_fragment) {
    private lateinit var flowerNameInput: TextInputEditText
    private lateinit var flowerDescriptionInput: TextInputEditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.add_flower_button).setOnClickListener { addFlower() }
        flowerNameInput = view.findViewById(R.id.add_flower_name)
        flowerDescriptionInput = view.findViewById(R.id.add_flower_description)
    }

    private fun addFlower() {
        if (flowerNameInput.text.isNullOrEmpty() || flowerDescriptionInput.text.isNullOrEmpty()) {
            Toast.makeText(
                requireActivity(),
                "Please input flower name and description",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val name = flowerNameInput.text.toString()
            val description = flowerDescriptionInput.text.toString()
            val bundle = bundleOf("flower_name" to name, "flower_description" to description)
            val navController = findNavController()
            setFragmentResult("add_flower", bundle)
            navController.popBackStack()
        }
    }
}