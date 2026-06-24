package com.example.studyapp.ui.screens.coroutineScreen

import android.content.Intent
import android.os.Build
import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.studyapp.activity.LegacyActivity
import com.example.studyapp.domain.model.UserResponse
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CoroutineScreen(
    viewModel: CoroutineViewModel,
    modifier: Modifier = Modifier,
    onBackPress: () -> Boolean
) {
    val uiText by viewModel.uiText.collectAsStateWithLifecycle()
    val uiTextTwo by viewModel.uiTextTwo.collectAsStateWithLifecycle()
    val uiTextThree by viewModel.uiTextThree.collectAsStateWithLifecycle()
    val uiTextFour by viewModel.uiTextFoure.collectAsStateWithLifecycle()
    val uiTextFive by viewModel.uiTextFive.collectAsStateWithLifecycle()
    val uiTextSix by viewModel.uiTextSix.collectAsStateWithLifecycle()
    val uiTextSeven by viewModel.uiTextSeven.collectAsStateWithLifecycle()
    val uiTextEight by viewModel.uiTextEight.collectAsStateWithLifecycle()
    val uiTextNine by viewModel.uiTextNine.collectAsStateWithLifecycle()
    var userRespose by remember {
        mutableStateOf(
            UserResponse(
                id = 0,
                name = "",
                email = "",
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        )
    }

    fun test() {
        Log.d("user", userRespose.toString())
        viewModel.getInfo(10) { user ->
            userRespose = user

        }
        Log.d("user", userRespose.toString())
    }

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
        Button(
            onClick = { onBackPress() },

            ) {
            Text("Back")
        }

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
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondaryContainer)
        ) {
            Text(
                text = uiTextFour,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.background
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { viewModel.startHeavyWork() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("4. launch heavy work")
            }
            Button(
                onClick = { viewModel.cancelHeavyWork() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel heavy work")
            }
        }


        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondaryContainer)
        ) {
            Text(
                text = uiTextFive,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.background
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { viewModel.startEnsureActiveWork() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("5. start ensureActiveWork}")
            }
            Button(
                onClick = { viewModel.cancelEnsureActiveWork() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("  cancel ensureActiveWork}")
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondaryContainer)
        ) {
            Text(
                text = uiTextSix,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.background
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { viewModel.cancelFinally() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("  cancel Finally}")
            }
            Button(
                onClick = { viewModel.testFinally() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("6. start Finally}")
            }

        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondaryContainer)
        ) {
            Text(
                text = uiTextSeven,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.background
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { viewModel.cancelNonCancellable() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("  cancel NonCancellable}")
            }
            Button(
                onClick = { viewModel.testNonCancellable() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("7. Non cancellable}")
            }

        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondaryContainer)
        ) {
            Text(
                text = uiTextEight,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.background
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { viewModel.loadSequential() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("8.loadSequential }")
            }

        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondaryContainer)
        ) {
            Text(
                text = uiTextNine,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.background
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { viewModel.loadConcurrent() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("9.loadConcurrent }")
            }
            Button(
                onClick = { viewModel.loadLazy() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("10.Start Lazy }")
            }

        }


        Button(
            onClick = { test() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Test }")
        }
    }

}