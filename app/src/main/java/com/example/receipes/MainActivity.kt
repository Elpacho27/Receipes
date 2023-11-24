package com.example.receipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment=FirstFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentcont, fragment)
            .commit()
    }


    }


