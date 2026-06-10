package com.example.studyapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.studyapp.ui.theme.StudyappTheme
import androidx.compose.material3.Surface
import com.example.studyapp.ui.coroutineScreen.CoroutineLearningScreen
import com.example.studyapp.ui.coroutineScreen.CoroutineViewModel
 

class MainActivity : ComponentActivity() {
    //    lateinit var coroutineViewModel: CoroutineViewModel 2nd way
    val viewModel: CoroutineViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        coroutineViewModel = ViewModelProvider(this)[CoroutineViewModel::class.java] 2nd way

        enableEdgeToEdge()
        setContent {
            StudyappTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Surface(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        CoroutineLearningScreen(viewModel = viewModel, modifier = Modifier)
                    }
                }
            }
        }
    }
}
