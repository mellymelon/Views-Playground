package com.example.viewsplayground

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle

class DetailFragment : Fragment(R.layout.detail_fragment), MenuProvider {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.detail_text_view)
        textView.text = arguments?.getString("detail_string")

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