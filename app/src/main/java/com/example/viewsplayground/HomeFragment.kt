package com.example.viewsplayground

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment

class HomeFragment : Fragment(R.layout.home_fragment) {
    //private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val btn = view.findViewById<Button>(R.id.home_button)
//        btn?.setOnClickListener {
//        //跳转后就不能换tab了，不知道原因
//        findNavController().navigate(R.id.shopFragment)
//
//        //如果nav_graph有action就不需要这个
//        (requireActivity() as MainActivity).navigateToShop()
//        }
    }
}