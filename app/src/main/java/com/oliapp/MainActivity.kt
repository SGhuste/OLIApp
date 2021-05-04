package com.oliapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userID = intent.getStringExtra("userID")
        val emailID = intent.getStringExtra("emailID")
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragment)

        val appbarConfiguration = AppBarConfiguration(setOf(R.id.firstFragment, R.id.secondFragment, R.id.thirdFragment, R.id.fourthFragment))
        setupActionBarWithNavController(navController, appbarConfiguration)

        bottomNavigationView.setupWithNavController(navController)

        //mainUserID.text = "User ID: $userID"
       // mainEmailID.text = "Email ID: $emailID"

//        mainBTNLogout.setOnClickListener {
//            FirebaseAuth.getInstance().signOut()
//
//            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
//            finish()
//        }
    }
}