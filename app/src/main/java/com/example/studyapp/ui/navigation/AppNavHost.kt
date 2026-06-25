package com.example.studyapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.studyapp.ui.screens.channel.ChannelScreen
import com.example.studyapp.ui.screens.channel.ChannelViewModel
import com.example.studyapp.ui.screens.coroutineScreen.CoroutineScreen
import com.example.studyapp.ui.screens.animation.AnimationScreen
import com.example.studyapp.ui.screens.animation.AnimationScreenTwo
import com.example.studyapp.ui.screens.animation.AnimationViewModel
import com.example.studyapp.ui.screens.coroutineScreen.CoroutineViewModel
import com.example.studyapp.ui.screens.flow.FlowScreen
import com.example.studyapp.ui.screens.flow.FlowViewModel
import com.example.studyapp.ui.screens.home.HomeScreen
import com.example.studyapp.ui.screens.home.HomeViewModel
import com.example.studyapp.ui.screens.sharedFlow.SharedFlowScreen
import com.example.studyapp.ui.screens.sharedFlow.SharedFlowViewModel
import com.example.studyapp.ui.screens.stateFlow.StateFlowScreen
import com.example.studyapp.ui.screens.stateFlow.StateFlowViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost() {

    val backStack = rememberNavBackStack(HomeKye)
    val viewModelCoroutine: CoroutineViewModel = viewModel()
    val viewModelChannel: ChannelViewModel = viewModel()
    val stateFlow: StateFlowViewModel = viewModel()
    val sharedFlow: SharedFlowViewModel = viewModel()
    val flowViewModel: FlowViewModel = viewModel()
    val animationViewModel: AnimationViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<HomeKye> {
                HomeScreen(
                    homeViewModel = homeViewModel,
                    onChannelPress = { backStack.add(ChannelKey) },
                    onCoroutinePress = { backStack.add(CoroutineKey) },
                    onStateFlowPress = { backStack.add(StateFlowKey) },
                    onSharedFlowPress = { backStack.add(SharedFlowKey) },
                    onFlowPress = { backStack.add(FlowKey) },
                    onAnimationPress = { backStack.add(AnimationKey) },
                    onAnimationTwoPress = { backStack.add(AnimationTwoKey) }

                )
            }
            entry<CoroutineKey> {
                CoroutineScreen(
                    viewModel = viewModelCoroutine,
                    modifier = Modifier,
                    onBackPress = { backStack.remove(CoroutineKey) }
                )
            }
            entry<ChannelKey> {
                ChannelScreen(
                    viewModel = viewModelChannel,
                    modifier = Modifier,
                    onBackPress = { backStack.remove(ChannelKey) }
                )
            }
            entry<StateFlowKey> {
                StateFlowScreen(
                    viewModel = stateFlow,
                    modifier = Modifier,
                    onBackPress = { backStack.remove(StateFlowKey) }
                )
            }
            entry<SharedFlowKey> {
                SharedFlowScreen(
                    viewModel = sharedFlow,
                    modifier = Modifier,
                    onBackPress = { backStack.remove(SharedFlowKey) }
                )
            }
            entry<FlowKey> {
                FlowScreen(
                    viewModel = flowViewModel,
                    onBackPress = { backStack.remove(FlowKey) }
                )
            }
            entry<AnimationKey> {
                AnimationScreen(
                    viewModel = animationViewModel,
                    onBackPress = { backStack.remove(AnimationKey) }
                )
            }
            entry<AnimationTwoKey> {
                AnimationScreenTwo(
                    viewModel = animationViewModel,
                    onBackPress = { backStack.remove(AnimationTwoKey) }
                )
            }

        })
}