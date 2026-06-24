package com.example.studyapp.ui.screens.coroutineScreen

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
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.time.LocalDateTime

class CoroutineViewModel() : ViewModel() {

    private val _uiText = MutableStateFlow("Press a button")
    val uiText: StateFlow<String> = _uiText.asStateFlow()
    private val _uiTextTwo = MutableStateFlow("Press a button")
    val uiTextTwo: StateFlow<String> = _uiTextTwo.asStateFlow()
    private val _uiTextThree = MutableStateFlow("Press a button")
    val uiTextThree: StateFlow<String> = _uiTextThree.asStateFlow()
    private val _uitextFoure = MutableStateFlow<String>("")

    val uiTextFoure = _uitextFoure.asStateFlow()

    private val _uiTextFive = MutableStateFlow<String>("")
    val uiTextFive = _uiTextFive.asStateFlow()

    private val _uiTextSix = MutableStateFlow<String>("")
    val uiTextSix = _uiTextSix.asStateFlow()

    private val _uiTextSeven = MutableStateFlow<String>("")
    private val _uiTextEight = MutableStateFlow<String>("")

    var uiTextEight = _uiTextEight.asStateFlow()
    val uiTextSeven = _uiTextSeven.asStateFlow()


    private val _uiTextNine = MutableStateFlow<String>("")
    val uiTextNine = _uiTextNine.asStateFlow()
    private var fetchJob: Job? = null
    private var heavtjob: Job? = null
    private var ensureActiceWork: Job? = null
    private var finally: Job? = null
    private var nonCancellable: Job? = null

    @RequiresApi(Build.VERSION_CODES.O)
    fun getInfo(id: Long, onComplete: (UserResponse) -> Unit) {
        val user = UserResponse(
            id = id,
            name = "shreyas",
            email = "example @gmail.com",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        onComplete(user)
    }

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
            } catch (e: TimeoutCancellationException) {
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


    // corporative cancellation using isActive

    fun startHeavyWork() {
        heavtjob?.cancel()
        _uitextFoure.value = "Heavy work started"


        heavtjob = viewModelScope.launch(context = Dispatchers.Default)
        {
            for (i in 1..1000_000_000) {
                if (!isActive) {
                    _uitextFoure.value = "heavy work cancel at iteration $i "
                    return@launch
                }
                //simulate cpu work
                if (i % 1000_000 == 0) {
                    _uitextFoure.value = "Progress :${i / 1_000_000}M"
                }

            }
            withContext(Dispatchers.Main) {
                _uitextFoure.value = "heavy work completed successfully "
            }
        }

    }

    fun cancelHeavyWork() {
        heavtjob?.cancel()
        _uitextFoure.value = "cancel requested"
    }


    // using ensureActive() (throws CancellationException)
    fun startEnsureActiveWork() {
        ensureActiceWork?.cancel()
        ensureActiceWork = viewModelScope.launch(Dispatchers.Default) {
            _uiTextFive.value = "ensureActiveWork started"
            for (i in 1..1000_000_00) {
                ensureActive() //throws  exception if  ensureActiceWork already  get canceled

                //simulate cpu work
                if (i % 1000_000 == 0) {
                    _uiTextFive.value = "Progress :${i / 1_000_000}M"
                }

            }
            _uiTextFive.value = " ensureActiveWork completed  "
        }.also { job ->//store job if you  want manually collection
        }
    }

    fun cancelEnsureActiveWork() {
        ensureActiceWork?.cancel()
        _uiTextFive.value = "cancel requested"
    }


    // finally block  - always run even on cancel
    fun testFinally() {
        finally?.cancel()

        finally = viewModelScope.launch(Dispatchers.IO) {
            var simulatedResource: String? = "OPENED"
            try {
                _uiTextSix.value = "Resourced opened. Working..."
                repeat(20) { i ->
                    if (!isActive) {

                        _uiTextSix.value = "work cancelled at iteration ${i}"

                    }
                    delay(100)
                }
                _uiTextSix.value = "work completed normally"

            } finally {
                simulatedResource = null
                _uiTextSix.value = "${_uiTextSix.value} \n Resourced closed finally"
            }
        }
    }

    fun cancelFinally() {
        finally?.cancel()
        _uiTextSix.value = "${_uiTextSix.value} \t cancel requested"
    }


    //test NonCancellable - critical section that can not be canceled
    fun testNonCancellable() {
        nonCancellable?.cancel()
        nonCancellable = viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiTextSeven.value = "Starting cancellable job "
                repeat(10) { i ->
                    ensureActive()//thorws cancelation exception if it got canceled
                    delay(100)
                    _uiTextSeven.value = "Step $i"

                }
            } finally {
                //this block will not cancel even if outer job is canceled
                withContext(NonCancellable) {
                    _uiTextSeven.value = "Saving critical data (can not be canceled )..."
                    delay(1000) //simulate disk write
                    _uiTextSeven.value = "Disk write is finished"

                }
            }
            _uiTextSeven.value = " ${_uiTextSeven.value}  \n All done "

        }
    }

    fun cancelNonCancellable() {
        nonCancellable?.cancel()
        _uiTextSeven.value = "${_uiTextSeven.value} \t cancel requested"
    }


    private suspend fun simulateProfile(): String {
        delay(2000)
        return "shreyas@exmple.com"

    }

    private suspend fun simulatePost(): String {
        delay(2000)
        return "5 posts avialable"
    }

    fun loadSequential() {


        viewModelScope.launch(Dispatchers.Main) {

            _uiTextEight.value = "Load sequential started"
            val startTime = System.currentTimeMillis()
            val profile = withContext(Dispatchers.IO) {
                simulateProfile()
            }
            val post = withContext(Dispatchers.IO) {
                simulatePost()
            }

            val elapsed = System.currentTimeMillis() - startTime
            _uiTextEight.value = "Load sequential finished with ${elapsed} :  ${profile} : ${post}"


        }
    }

    // concurrent loading

    fun loadConcurrent() {
        viewModelScope.launch(Dispatchers.Main) {
            _uiTextNine.value = "Concurrent loading started"
            val start = System.currentTimeMillis()


            val result =
                withContext(Dispatchers.IO) {
                    coroutineScope {
                        val profile = async { simulateProfile() }
                        val post = async { simulatePost() }

                        "${profile.await()} : ${post.await()}"
                    }
                }
            val elapsed = System.currentTimeMillis() - start

            _uiTextNine.value = " ${result} : ${elapsed}"
        }


    }


    // lazy loading
    fun loadLazy() {
        viewModelScope.launch(Dispatchers.Main) {
            _uiTextNine.value = "Concurrent Lazy started"
            val start = System.currentTimeMillis()


            val result =
                withContext(Dispatchers.IO) {
                    coroutineScope {
                        val profile = async(start = CoroutineStart.LAZY) { simulateProfile() }
                        val post = async(start = CoroutineStart.LAZY) { simulatePost() }
                        profile.start()
                        "${profile.await()} : ${post.await()}"
                    }
                }
            val elapsed = System.currentTimeMillis() - start

            _uiTextNine.value = " $result : $elapsed"
        }


    }
}