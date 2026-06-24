package com.example.studyapp.ui.screens.flow

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun FlowScreen(
    viewModel: FlowViewModel,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier
) {

    val scope = rememberCoroutineScope()
    var counter by remember { mutableStateOf(0) }
    val counterText by remember {
        derivedStateOf {
            "counter here ${counter}"
        }
    }

    var output1 by remember { mutableStateOf("Idl") }
    var output2 by remember { mutableStateOf("Idl") }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Flow Screen", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = onBackPress) {
            Text("Back to Home")
        }
//single collection
        Button(
            onClick = {
                scope.launch {
                    output1 = "Collecting"
                    viewModel.coldFlow.collect { value ->
                        output1 = "Got : $value"
                        Log.d("ColdFlow", "$output1")
                        output1 = "Done"
                        Log.d("ColdFlow", "$output1")
                    }
                }
            }
        ) {
            Text("Collect once")
        }
        Text("Output 1 = $output1")



        Spacer(modifier = modifier.height(10.dp))
        //sequencial collection

        Button(
            onClick = {
                viewModel.collectOnce(

                    onEach = {
                        output2 = "value $it"
                        Log.d("ColdFlow", "$output2")
                    },
                    onComplete = {
                        output2 = "Finished"
                        Log.d("ColdFlow", "$output2")
                    }
                )

            }
        ) {
            Text("Collect via ViewModel")
        }
        Text("Output Two  = $output2")






        Spacer(modifier = modifier.height(10.dp))
        //CONCURRENTLY Two collection

        Button(
            onClick = {

                output1 = "Two collectors running"
                output2 = "Two collectors running"

                viewModel.collectTwice(

                    firstAction = {
                        output1 = "First $it"


                        Log.d("ColdFlow", "$output1")
                    },
                    secondAction = {
                        output2 = "Second $it"


                        Log.d("ColdFlow", "$output2")
                    }
                )

            }
        ) {
            Text("Run Two Collectors Concurrently")

        }
        Button(onClick = {
            output1 = "Idle"
            output2 = "Idle"
        }) {
            Text("Reset")
        }


        Button(onClick = { viewModel.emitListFlow() }) {

            Text("emit list")
        }
        Button(onClick = { viewModel.collectFirst() }) {

            Text("emit loop")
        }
        Button(onClick = { viewModel.flowFold() }) {

            Text("emit flowFold")
        }
        Button(onClick = { counter++ }
        ) {
            Text("increase counter  ")

        }
        Text("increase counter $counterText")

    }
}