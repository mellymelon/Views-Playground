package com.example.viewsplayground

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment(R.layout.home_fragment) {
    //private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataset = Array(100) { i -> "$i * $i = ${i * i}" }
        val customAdapter = CustomAdapter(dataset)
        val recyclerView = view.findViewById<RecyclerView>(R.id.numberList)
        recyclerView.adapter = customAdapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this.context,
                DividerItemDecoration.VERTICAL
            )
        )
//        ViewCompat.setOnApplyWindowInsetsListener(recyclerView) { v, insets ->
//            val innerPadding = insets.getInsets(
//                WindowInsetsCompat.Type.systemBars()
//                        or WindowInsetsCompat.Type.displayCutout()
//                // If using EditText, also add
//                // "or WindowInsetsCompat.Type.ime()" to
//                // maintain focus when opening the IME
//            )
//            Log.d(TAG, "padding分别是$innerPadding")
//            v.setPadding(
//                innerPadding.left,
//                innerPadding.top,
//                innerPadding.right,
//                innerPadding.bottom
//            )
//            insets //或者WindowInsetsCompat.CONSUMED（如果不希望影响子元素）
//        }
    }
}