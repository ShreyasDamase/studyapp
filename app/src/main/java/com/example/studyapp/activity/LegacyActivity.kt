package com.example.studyapp.activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.studyapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LegacyActivity : AppCompatActivity() {

    private var tvStatus: TextView? = null // Using nullable to simulate manual nulling

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leagacy)

        tvStatus = findViewById(R.id.tvStatus)

        // Scenario 1: Dialog on Destroyed Activity
        findViewById<Button>(R.id.btnCrashDialog).setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                tvStatus?.text = "Closing activity in 2s... then Dialog will try to show"
                delay(2000)
                finish() // Activity starts finishing
                delay(500) // Wait a bit more for it to be destroyed
                
                // This will crash with WindowManager$BadTokenException
                AlertDialog.Builder(this@LegacyActivity)
                    .setTitle("I will crash")
                    .setMessage("Because the Activity is gone")
                    .show()
            }
        }

        // Scenario 2: Accessing nulled variable
        findViewById<Button>(R.id.btnCrashLateinit).setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                tvStatus?.text = "Closing activity... coroutine will access null soon"
                delay(2000)
                finish()
                // Wait for onDestroy to null out tvStatus
                delay(1000)
                
                // This will crash if we use !! or if we didn't check for null
                // To strictly match your 'lateinit' request, we'll force a crash:
                println(tvStatus!!.text) 
            }
        }

        // Scenario 3: Fragment requireContext() crash
        findViewById<Button>(R.id.btnCrashFragment).setOnClickListener {
            val fragment = CrashFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()

            GlobalScope.launch(Dispatchers.Main) {
                delay(1000)
                tvStatus?.text = "Removing fragment... crash incoming in fragment"
                finish() // Finishing activity detaches the fragment
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Manually nulling to simulate the crash you asked about
        tvStatus = null
    }
}