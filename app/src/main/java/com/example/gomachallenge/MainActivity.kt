package com.example.gomachallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.gomachallenge.data.remote.BinanceWebSocketClient
import com.example.gomachallenge.data.repository.CryptoRepositoryImpl
import com.example.gomachallenge.presentation.viewmodel.CryptoListViewModel
import com.example.gomachallenge.ui.theme.GomaChallengeTheme
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {

            // Initialize dependencies
            val okHttpClient = remember {
                OkHttpClient.Builder()
                    .pingInterval(20, TimeUnit.SECONDS)
                    .build()
            }

            val webSocketClient = remember {
                BinanceWebSocketClient(okHttpClient)
            }

            val repository = remember {
                CryptoRepositoryImpl(webSocketClient)
            }
            val listViewModel = remember {
                CryptoListViewModel(
                    repository = repository, webSocketClient = webSocketClient
                )
            }

            GomaChallengeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {

                    AppNavigation(listViewModel, repository)

                }
            }
        }
    }
}