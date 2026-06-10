package com.example.studyapp.ui.coroutineScreen

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.studyapp.activity.LegacyActivity


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CoroutineLearningScreen(viewModel: CoroutineViewModel, modifier: Modifier = Modifier) {
    val uiText by viewModel.uiText.collectAsStateWithLifecycle()
    val uiTextTwo by viewModel.uiTextTwo.collectAsStateWithLifecycle()
    val uiTextThree by viewModel.uiTextThree.collectAsStateWithLifecycle()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondaryContainer)
        ) {
            Text(
                text = uiText,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.background
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { viewModel.fetchAllUser() }, modifier = Modifier.fillMaxWidth()) {
                Text("1. launch (fire-and-forgot}")
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondaryContainer)
        ) {
            Text(
                text = uiTextTwo,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.background
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { viewModel.fetchAlluserAndBooks() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("2.async await")
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondaryContainer)
        ) {
            Text(
                text = uiTextThree,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.background
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { viewModel.testGlobalScope() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("3. launch  global scope}")
            }
        }

        Button(
            onClick = { context.startActivity(Intent(context, LegacyActivity::class.java)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("4. launch  global scope in activity }")
        }

    }

}