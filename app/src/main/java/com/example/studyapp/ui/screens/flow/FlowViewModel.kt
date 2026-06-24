package com.example.studyapp.ui.screens.flow

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch


class FlowViewModel() : ViewModel() {

    val list = listOf<Int>(1, 2, 3, 4, 5, 6)

    val flowList: Flow<Int> = list.asFlow()

    val coldFlow: Flow<Int> = flow {
        Log.d("ColdFlow", "Producer started")
        for (i in 1..3) {
            delay(500)
            emit(i)
            Log.d("ColdFlow", "📤 Emitted: $i")
        }
        Log.d("ColdFlow", "Producer complete")

    }


    // collect one with callback with ui update

    fun collectOnce(onEach: (Int) -> Unit, onComplete: () -> Unit) {
        viewModelScope.launch {

            coldFlow.collect { value ->
                onEach(value)
            }
            onComplete()
        }
    }

    // collect twice Concurrently to demonstrate independent execution
    fun collectTwice(
        firstAction: (Int) -> Unit,
        secondAction: (Int) -> Unit
    ) {

        viewModelScope.launch {


            launch {
                coldFlow.collect { firstAction(it) }
                coldFlow.collect { secondAction(it) }
            }
        }
    }


    fun emitListFlow() {
        viewModelScope.launch {

            flowList.collect { value ->
                delay(1000)
                Log.d("Flow", "list flow $value")
            }
        }
    }


    fun collectFirst() {
        viewModelScope.launch {

            val temp = flow<Int> {
                list.forEach {
                    Log.d("emitting", "emitting $it")
                    emit(it)
                }
            }
            try {
                val first = temp.single()
                Log.d("Received", "Received = $first")
            } catch (e: IllegalArgumentException) {
                Log.d("Exception", "IllegalArgumentException")
            }

            val last = temp.last()
            Log.d("Received", "Received = $last")


        }
    }

    fun flowFold() {
        viewModelScope.launch {
            val accumulatedFlow: Int = flowList.fold(0) { acc, i -> acc + i }
            Log.d("accumulated value", "accumulated value ${accumulatedFlow}")
        }
    }

}