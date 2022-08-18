package com.example.guessnumbernav.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.guessnumbernav.R
import com.example.guessnumbernav.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val vb by viewBinding(
        ActivityMainBinding::bind,
        R.id.main_root
    )

//    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        navController = findNavController(R.id.main_root)
//        setupActionBarWithNavController(navController)
    }


}