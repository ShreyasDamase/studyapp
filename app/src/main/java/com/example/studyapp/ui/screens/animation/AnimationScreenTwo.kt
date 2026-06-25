package com.example.studyapp.ui.screens.animation

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studyapp.R
import kotlin.collections.List
import kotlin.math.ceil
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.sqrt


//
//e stand for Coefficient of Restitution (e) Measures how much velocity survives after a bounce
// range is 0 to 1
// calculated by e = v1/v2 where v1:velocity before impact and v2 after

enum class BallType(val e: Float) {
    SUPER_BALL(0.9f), TENNIS_BALL(0.7f), BASKETBALL(0.75f), SOCCER_BALL(0.6f)
}

data class DropValues(val height: Float, val time: Int)
data class AnimationProperties(
    val totalTime: Int,
    val animationPropValues: List<DropValues>
)

@Composable
fun AnimationScreenTwo(
    viewModel: AnimationViewModel,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    bouncyAnimation()

}


@Preview()
@Composable
fun bouncyAnimation() {

    val screenHeight = LocalConfiguration.current.screenHeightDp
    val dropHeight = 5f
    val visualFactor = screenHeight * 0.8f / dropHeight


    var selectedBallType by remember { mutableStateOf(BallType.TENNIS_BALL) }
    var expandedDropdown by remember { mutableStateOf(false) }

    fun animationValues(ballType: BallType, dropHeight: Float): AnimationProperties {
        val visualFactor = screenHeight * 0.8f / dropHeight    //H new = h(prev)*e^n

        val minHeight = 1.0
        val decayFactor = ballType.e * ballType.e

        val totalNumOfBounce = ceil(
            ln(minHeight / dropHeight) /
                    ln(decayFactor.toDouble())
        ).toInt()

        var totalTime = 0
        val dropValuesList: List<DropValues> = List(totalNumOfBounce.toInt() + 1) { i ->
            val time = (
                    sqrt(2.0 * dropHeight / 9.81) *
                            ballType.e.pow(i) *
                            1000
                    ).toInt()
            totalTime += time * 2
            DropValues(
                height = dropHeight * ballType.e.pow(2 * i) * visualFactor,
                time = time
            )


        }

        return AnimationProperties(totalTime, dropValuesList)


    }


    val animationProp = animationValues(selectedBallType, dropHeight)

    val transition = rememberInfiniteTransition()
    val bouncyY by transition.animateFloat(
        initialValue = dropHeight * visualFactor,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(

            animation = keyframes {
                var currentTime = 0
                durationMillis = animationProp.totalTime

                animationProp.animationPropValues[0].height at 0

                currentTime += animationProp.animationPropValues[0].time

                0f at currentTime with EaseIn

                for (i in 1 until animationProp.animationPropValues.size) {

                    val value = animationProp.animationPropValues[i]

                    currentTime += value.time
                    value.height at currentTime with EaseOut

                    currentTime += value.time
                    0f at currentTime with EaseIn
                }


            },
            repeatMode = RepeatMode.Restart
        ),
        label = "bounce"
    )


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {


        Box(
            modifier = Modifier
                .background(Color.LightGray)
                .size(200.dp, 50.dp),
            contentAlignment = Alignment.Center
        ) {
            TextButton(onClick = { expandedDropdown = true }) {
                Text("Ball Type: ${selectedBallType.name}")
            }
            DropdownMenu(
                expanded = expandedDropdown,
                onDismissRequest = { expandedDropdown = false }
            ) {
                BallType.values().forEach { ballType ->
                    DropdownMenuItem(
                        text = { Text("${ballType.name} (e=${ballType.e})") },
                        onClick = {
                            selectedBallType = ballType
                            expandedDropdown = false
                        }
                    )
                }
            }
        }


        Box(
            modifier = Modifier

                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize()

                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                Box(
                    modifier = Modifier

                        .background(Color.White)
                ) {


                    Image(
                        painter = painterResource(R.drawable.ball),
                        contentDescription = null,
                        modifier = Modifier
                            .graphicsLayer(
                                translationY = -bouncyY
                            )
                            .size(30.dp)
                            .align(Alignment.Center)
                    )


                }

                Box(
                    modifier = Modifier .background(Color.Black)
                        .height(20.dp)
                        .fillMaxWidth()

                )

            }
        }

    }
}