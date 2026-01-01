package com.example.demosample.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.demosample.data.datasource.remote.ApiService
import com.example.demosample.domain.useCase.GetNewsUseCase
import com.example.demosample.presentation.navigation.RootNavGraph
import com.example.demosample.presentation.theme.MyApplicationDemoTheme
import com.example.demosample.presentation.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var api: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        Log.d("TAG_GetNewsUseCase","useCase "+api)
        setContent {
            val navController = rememberNavController()

            MyApplicationDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(modifier = Modifier.fillMaxSize()){
                        RootNavGraph(
                            navController = navController
                        )
                    }
                }
            }
        }
    }

}
