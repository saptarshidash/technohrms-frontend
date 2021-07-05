package com.saptarshidas.technohrms.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.saptarshidas.technohrms.R
import com.saptarshidas.technohrms.data.local.AppPreferencesHelper
import com.saptarshidas.technohrms.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.menu_header.*
import kotlinx.android.synthetic.main.menu_header.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private lateinit var navController: NavController


    private lateinit var viewBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setSupportActionBar(viewBinding.toolbar)


        navController = Navigation.findNavController(this, R.id.fragmentContainerView)

        NavigationUI.setupWithNavController(viewBinding.navMenu, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, viewBinding.drawerLayout)

        val h  = viewBinding.navMenu.getHeaderView(0)
        val txtView = h.findViewById<TextView>(R.id.txtViewLoggedInUser)
        val preferencesHelper = AppPreferencesHelper(this)
        lifecycleScope.launch{
            txtView.setText("Welcome "+preferencesHelper.getUsername().first())
        }

    }



    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, viewBinding.drawerLayout)
    }
}