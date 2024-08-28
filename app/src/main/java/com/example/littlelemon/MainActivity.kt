package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.data.reporitory.SectionRepository
import com.example.littlelemon.ui.navigation.LittleLemonNavigation
import com.example.littlelemon.ui.theme.LittleLemonTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navHostController = rememberNavController()
                    LittleLemonNavigation(navHostController = navHostController, SectionRepository.getInstance(this.applicationContext))
                }
            }
        }
    }
}
