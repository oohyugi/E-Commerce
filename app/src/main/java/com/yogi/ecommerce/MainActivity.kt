package com.yogi.ecommerce

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yogi.ecommerce.core.base.BaseActivity

class MainActivity : BaseActivity() {


    companion object{
        const val EXTRA_SHOW_FRAG_PURCHASED = "purchased"
        fun startThisActivity(context: Context, isShowPurchased: Boolean = false) {
            val intent  = Intent(context,MainActivity::class.java)
            intent.putExtra(EXTRA_SHOW_FRAG_PURCHASED, isShowPurchased)
            context.startActivity(intent)

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        navView.setupWithNavController(navController)

        if (intent.getBooleanExtra(EXTRA_SHOW_FRAG_PURCHASED, false)) {
            navController.navigate(R.id.navigation_profile)
        }

    }
}
