package com.example.studyapp.ui.screens.channel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class ChannelViewModel() : ViewModel() {

    private val _uiText = MutableStateFlow<String>("")
    val uiText = _uiText.asStateFlow()

    val channel = Channel<String>()

    suspend fun checkChannel() {


//        val producer = viewModelScope.launch {
//            _uiText.value = "Sending...."
//            channel.send("Hello from producer")
//            delay(1000)
//            _uiText.value = "sent"
//            delay(1000)

//            channel.send("A")
//            _uiText.value = "channel created: $channel   channel type: ${channel::class.simpleName}"
//
//            channel.send("B")
//            _uiText.value = "channel created: $channel   channel type: ${channel::class.simpleName}"
//
//            channel.close()
//        }
//        val consumer = viewModelScope.launch {
//            _uiText.value = "channel created: $channel   channel type: ${channel::class.simpleName}"
//
//            delay(3000)
//            _uiText.value = "channel created: $channel   channel type: ${channel::class.simpleName}"
//
//            _uiText.value = "Before received "
//            val value = channel.receive()
//
//            _uiText.value = value
//            _uiText.value = "After received "


//        }
//        producer.join()
//        consumer.join()


        viewModelScope.launch {
            channel.send("A")
            Log.d("channel", "A send")
            channel.send("B")
            Log.d("channel", "b send")

            channel.send("C")
            Log.d("channel", "c send")

            _uiText.value = "sent"
        }

        viewModelScope.launch {

            Log.d("channel", "before receive")

            _uiText.value = "Before Receive"

            val value2 = channel.receive()
            Log.d("channel", "A receive")
            _uiText.value = value2
            val value3 = channel.receive()
            Log.d("channel", "B receive")
            _uiText.value = value3
            val value5 = channel.receive()
            Log.d("channel", "C receive")
            _uiText.value = value5
        }

    }

    fun onChannelClick() {
//        _uiText.value = "channel created: $channel   channel type: ${channel::class.simpleName}"
        viewModelScope.launch { checkChannel() }

    }

    fun onChannelClick2() {
//        _uiText.value = "channel created: $channel   channel type: ${channel::class.simpleName}"
        viewModelScope.launch { cancelExample() }

    }

    private fun checkChannel2() {
        viewModelScope.launch {
            println("Before Receive")

            println(channel.receive())

            println("After Receive")
        }

        viewModelScope.launch {
            delay(3000)

            println("Before Send")

            channel.send("A")

            println("After Send")
        }
    }


    fun fastProducerSlowConsumer() {

        val channel = Channel<Int>(3)

        viewModelScope.launch {

            Log.d("channel", "Producer: sending 1")
            channel.send(1)

            Log.d("channel", "Producer: sending 2")
            channel.send(2)

            Log.d("channel", "Producer: sending 3")
            channel.send(3)

            Log.d("channel", "Producer: sending 4")
            channel.send(4)

            Log.d("channel", "Producer: sending 5")
            channel.send(5)

            Log.d("channel", "Producer Finished")
        }

        viewModelScope.launch {

            delay(4000)

            repeat(5) {

                val value = channel.receive()

                Log.d("channel", "Consumer received $value")

                delay(1000)
            }
        }
    }


    fun slowProducerFastConsumer() {

        val channel = Channel<Int>(Channel.CONFLATED, onBufferOverflow = BufferOverflow.DROP_OLDEST)

//        viewModelScope.launch {
//
//            repeat(5) {
//
//                Log.d("channel", "Consumer waiting")
//
//                val value = channel.receive()
//
//                Log.d("channel", "Consumer received $value")
//            }
//        }
//
//        viewModelScope.launch {
//
//            delay(5000)
//
//            repeat(5) {
//
//                Log.d("channel", "Producer sending $it")
//
//                channel.send(it)
//
//                delay(1000)
//            }
//            channel.close()
//            channel.send(22)
//        }

        viewModelScope.launch {

            delay(2000)

            channel.send(10)

            delay(2000)

            channel.send(20)

            channel.close()
        }

        viewModelScope.launch {

            for (item in channel) {
                Log.d("channel", "$item")
            }

            Log.d("channel", "Done")
        }
    }

    fun dropOldestExample() {

        val channel = Channel<Int>(
            capacity = 2,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

        viewModelScope.launch {

            channel.send(1)
            Log.d("channel", "Sent 1")

            channel.send(2)
            Log.d("channel", "Sent 2")

            channel.send(3)
            Log.d("channel", "Sent 3")

            channel.send(4)
            Log.d("channel", "Sent 4")

            channel.close()
        }

        viewModelScope.launch {

            delay(3000)

            for (item in channel) {
                Log.d("channel", "Received $item")
            }
        }
    }

    fun multipleProducers() {

        val channel = Channel<String>(10)

        viewModelScope.launch {
            channel.send("Producer-1")
        }

        viewModelScope.launch {
            channel.send("Producer-2")
        }

        viewModelScope.launch {
            channel.send("Producer-3")
        }

        viewModelScope.launch {

            repeat(3) {
                Log.d("channel", channel.receive())
            }
        }
    }


    fun raceProducers() {

        val channel = Channel<String>(10)

        viewModelScope.launch {
            delay(300)
            channel.send("A")
        }

        viewModelScope.launch {
            delay(100)
            channel.send("B")
        }

        viewModelScope.launch {
            delay(200)
            channel.send("C")
        }

        viewModelScope.launch {

            repeat(3) {
                Log.d("channel", channel.receive())
            }
        }
    }

    fun cancelExample() {

        val channel = Channel<Int>(10)

        viewModelScope.launch {

            channel.send(1)
            channel.send(2)
            channel.send(3)

            Log.d("channel", "Cancel")

            channel.cancel()
        }

        viewModelScope.launch {

            delay(3000)

            try {

                repeat(3) {

                    val value = channel.receive()

                    Log.d("channel", "Received $value")
                }

            } catch (e: Exception) {

                Log.d("channel", "Exception ${e::class.simpleName}")
            }
        }
    }
}