package com.example.viewsplayground

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.navigation.NavController

val Any.TAG: String
    get() {
        return javaClass.simpleName
    }

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.the_toolbar))

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //进入非topLevel页面后在导航栏显示返回按钮
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.shopFragment))
        findViewById<Toolbar>(R.id.the_toolbar).setupWithNavController(
            navController,
            appBarConfiguration
        )

        //关联BottomNavigationView和navHostFragment
        val bottomBar = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomBar.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment) {
                Toast.makeText(this, "查看Home", Toast.LENGTH_SHORT).show()
            } else if (destination.id == R.id.shopFragment) {
                Toast.makeText(this, "查看Shop", Toast.LENGTH_SHORT).show()
            }
            bottomBar.isVisible = appBarConfiguration.topLevelDestinations.contains(destination.id)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_create -> {
            navController.navigate(R.id.navigate_to_add_flower)
            true
        }

        R.id.action_search -> {
            Toast.makeText(this, "搜索...", Toast.LENGTH_SHORT).show()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}