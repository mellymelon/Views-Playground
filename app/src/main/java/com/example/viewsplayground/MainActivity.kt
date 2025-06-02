package com.example.viewsplayground

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

val Any.TAG: String
    get() {
        return javaClass.simpleName
    }

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        //用nav_graph.xml就不需要这个
//        navController.graph =
//            navController.createGraph(startDestination = "home") {
//                fragment<HomeFragment>("home") {
//                    label = resources.getString(R.string.home_label)
//                }
//                fragment<ShopFragment>("shop") {
//                    label = resources.getString(R.string.shop_label)
//                }
//            }

        //关联BottomNavigationView和navHostFragment
        findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment) {
                Toast.makeText(this, "查看Home", Toast.LENGTH_SHORT).show()
            } else if (destination.id == R.id.shopFragment) {
                //toolbar.visibility = View.VISIBLE
                //bottomNavigationView.visibility = View.VISIBLE
                Toast.makeText(this, "查看Shop", Toast.LENGTH_SHORT).show()
            }
        }

        //配置好nav_graph就不需要这个
        // Logic to load the starting destination when the Activity is first created
//        if (savedInstanceState == null) {
//            val homeFragment = HomeFragment()
//            supportFragmentManager.beginTransaction()
//                .add(R.id.nav_host_fragment, homeFragment, homeFragment.TAG)
//                .commit()
//        }
    }

    //如果nav_graph有action就不再需要这个方法
//    fun navigateToShop() {
//        val fragment = ShopFragment()
//        val args = Bundle().apply {
//            putInt("KEY_PRODUCT_ID", 123)
//        }
//        fragment.arguments = args
//
//        supportFragmentManager.beginTransaction()
//            .addToBackStack(fragment.TAG)
//            .replace(R.id.shopFragment, fragment, fragment.TAG)
//            .commit()
//    }
}