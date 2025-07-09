package com.example.gomachallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.gomachallenge.presentation.screen.CryptoListScreen
import com.example.gomachallenge.presentation.viewmodel.CryptoListViewModel
import com.example.gomachallenge.ui.theme.GomaChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = remember { CryptoListViewModel() }
            GomaChallengeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {

                    CryptoListScreen(viewModel)
                }
            }
        }
    }
}