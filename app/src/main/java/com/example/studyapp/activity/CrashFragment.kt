package com.example.studyapp.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CrashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(requireContext()).apply {
            text = "Fragment loaded. Wait for crash..."
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Scenario 2: Fragment requireContext() crash
        GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            // If the activity is finished and fragment is detached within 3s,
            // requireContext() will throw IllegalStateException
            val context = requireContext()
            println("Context: $context")
        }
    }
}