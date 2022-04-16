package com.myapplication

import MainScreen
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import theme.bottomNavBarDemoTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            bottomNavBarDemoTheme {
                MainScreen()
            }
        }
    }
}