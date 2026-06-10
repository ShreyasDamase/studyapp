package com.example.studyapp.ui.coroutineScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.Button
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.studyapp.domain.model.Book
import com.example.studyapp.domain.model.UserResponse
import com.example.studyapp.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

class CoroutineViewModel() : ViewModel() {

    private val _uiText = MutableStateFlow("Press a button")
    val uiText: StateFlow<String> = _uiText.asStateFlow()
    private val _uiTextTwo = MutableStateFlow("Press a button")
    val uiTextTwo: StateFlow<String> = _uiTextTwo.asStateFlow()
    private val _uiTextThree = MutableStateFlow("Press a button")
    val uiTextThree: StateFlow<String> = _uiTextThree.asStateFlow()
    private var fetchJob: Job? = null
    private fun log(mesage: String) {
        Log.d("Coroutine Demo", mesage)
    }

    fun threadInfo() = Thread.currentThread().name

    //suspending fun non blocking
    suspend fun doNetworkCall(): String {
        println(" [doNetworkCall start on ${threadInfo()}]")
        delay(1000)
        println(" [doNetworkCall after delay ${threadInfo()}]")
        return "Data from network"
    }


    suspend fun doCpuWork(): Int {
        log("[cpu work started on ] ${threadInfo()}")
        var sum = 0
        for (i in 1..5_000_000) {
            sum += i
        }
        log("[cpu work finished on ${threadInfo()}")
        return sum
    }


    //launch async with context
    fun exampleLaunch() {
        log("== example launch (fire and forgot)")
        viewModelScope.launch(Dispatchers.Main) {
            _uiText.value = "launch started on ${threadInfo()}"
            log("launch coroutine started on ${threadInfo()}")
            // simulate background work by swithcing to io
            withContext(Dispatchers.IO) {
                log("Heavy work on ${threadInfo()}")
                delay(2500)
            }
            _uiText.value = "Launch finished on ${threadInfo()}"
            log("Launch coroutine finished on ${threadInfo()}")

        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchAllUser() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch(Dispatchers.Main) {
            _uiText.value = "loading users.."
            try {

                val users =
                    withTimeout(3000L) {
                        withContext(Dispatchers.IO)
                        {
//                            delay(5000) this will add time out error
                            RetrofitClient.apiService.getAllUsers()
                        }
                    }
                val display = users.joinToString("\n") { user ->
                    "${user.id}: ${user.name} : (${user.email} )\n Created : ${user.createdAt}"
                }
                _uiText.value = "User:\n $display"
            } catch (e: kotlinx.coroutines.TimeoutCancellationException) {
                _uiText.value = "Error: Time out"
            } catch (e: HttpException) {
                _uiText.value = "Error: ${e.message}"
            } catch (e: Exception) {
                _uiText.value = "Error: ${e.message}"
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchAlluserAndBooks() {
        viewModelScope.launch(Dispatchers.Main) {

            _uiTextTwo.value = "Fetching user and book parallel... "
            val startTime = System.currentTimeMillis()
            try {
                supervisorScope {
                    val userDeferred = async(Dispatchers.IO) {
                        RetrofitClient.apiService.getAllUsers()
                    }
                    val bookDerferred = async(Dispatchers.IO) {
                        RetrofitClient.apiService.getBooks()
                    }
//                val users = userDeferred.await()
//                val books = bookDerferred.await()
                    val results = awaitAll(userDeferred, bookDerferred)
                    val users = results[0] as List<UserResponse>
                    val books = results[1] as List<Book>
                    val elapsed = System.currentTimeMillis() - startTime

                    val userSummary =
                        "User : (${users.size}) : ${users.take(3).joinToString { it.name }}.."
                    val bookSummary =
                        "Book : (${books.size}) : ${books.take(3).joinToString { it.title }}..."

                    _uiTextTwo.value = "Done in ${elapsed} \n ${userSummary} \n ${bookSummary}"

                }
            } catch (e: HttpException) {
                _uiTextTwo.value = "Error: ${e.message}"
            } catch (e: Exception) {
                _uiTextTwo.value = "Error: ${e.message}"
            }
        }
    }

    fun testGlobalScope() {
        GlobalScope.launch(Dispatchers.Main) {
            _uiTextThree.value = "Global Scope: Started"
            delay(10000)//10 sec
            //this will try to update ui even if activity gone
            _uiTextThree.value = "Global Scope finished"


        }
    }


}