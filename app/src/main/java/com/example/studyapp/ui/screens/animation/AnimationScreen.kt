package com.example.studyapp.ui.screens.animation

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import  androidx.compose.runtime.getValue

import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studyapp.R
import kotlin.random.Random

@Composable
fun AnimationScreen(
    viewModel: AnimationViewModel,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    var heightTOSet by remember { mutableStateOf(10) }
    var widthTOSet by remember { mutableStateOf(10) }

    val groundOffsetPx = with(LocalDensity.current) {
        50.dp.toPx()
    }

    fun randomFloat(
        min: Float,
        max: Float
    ): Float {
        return Random.nextFloat() * (max - min) + min
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged {
                heightTOSet = it.height
                widthTOSet = it.width
            }
    ) {

        Box(
            modifier = Modifier.weight(1f)
        ) {
            repeat(5) {
                FallingLeaf(
                    screenHeight = heightTOSet.toFloat() -
                            Random.nextDouble(
                                (groundOffsetPx - 5).toDouble(),
                                (groundOffsetPx + 5).toDouble()
                            ).toFloat(),

                    startX = randomFloat(
                        (widthTOSet / 3f) + 100f,
                        widthTOSet.toFloat() - 100f
                    )
                )
            }
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )
    }
}


@Composable
fun FallingLeaf(screenHeight: Float, startX: Float) {
    val infiniteTransition = rememberInfiniteTransition(label = "leaf")

    // Randomize animation timing per leaf
    val fallDuration = remember { Random.nextInt(4000, 7000) }  // 4-7s
    val driftDuration = remember { Random.nextInt(1000, 1800) }  // 1-1.8s
    val rotateDuration = remember { Random.nextInt(700, 1200) }  // 700-1200ms

    // Randomize drift range [ left vs right lean ]
    val driftRange = remember { Random.nextInt(30, 60).toFloat() }  // 30-60px
    val driftDirection = remember { if (Random.nextBoolean()) 1f else -1f }

    // Randomize rotation range
    val rotationRange = remember { Random.nextInt(20, 45).toFloat() }  // 20-45°

    // Randomize starting Y (not always mid-screen)
    val startY = remember { Random.nextFloat() * (screenHeight * 0.3f) }


    //Random mirror this will mirror leaf randomly to look more natural
    val mirrorRandom = remember { if (Random.nextBoolean()) 1f else -1f }
    val fallY by infiniteTransition.animateFloat(
        initialValue = startY,
        targetValue = screenHeight + 100,
        animationSpec = infiniteRepeatable(
            animation = tween(fallDuration, easing = EaseIn),
        ),
        label = "fall"
    )

    val driftX by infiniteTransition.animateFloat(
        initialValue = -driftRange,
        targetValue = driftRange * driftDirection,
        animationSpec = infiniteRepeatable(
            animation = tween(driftDuration, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "drift"
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = -rotationRange,
        targetValue = rotationRange,
        animationSpec = infiniteRepeatable(
            animation = tween(rotateDuration, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotate"
    )

    Image(
        painter = painterResource(R.drawable.leaf),
        contentDescription = null,
        modifier = Modifier
            .size(32.dp)
            .graphicsLayer {
                translationY = fallY
                translationX = startX + driftX
                rotationZ = rotation
                scaleX = mirrorRandom
            }
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun AnimationScreenPreview() {
    var heightTOSet by remember { mutableStateOf(10) }
    var widthTOSet by remember { mutableStateOf(10) }

    val groundOffsetPx = with(LocalDensity.current) {
        50.dp.toPx()
    }

    fun randomFloat(
        min: Float,
        max: Float
    ): Float {
        return Random.nextFloat() * (max - min) + min
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged { it ->
                heightTOSet = it.height
                widthTOSet = it.width
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Box(modifier = Modifier.weight(1f))
        {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                repeat(5) {
                    FallingLeaf(
                        screenHeight = heightTOSet.toFloat() - Random.nextDouble(
                            from = (groundOffsetPx - 5).toDouble(),
                            until = (groundOffsetPx + 5).toDouble()
                        ).toFloat(),
                        startX = randomFloat(
                            (widthTOSet / 3 + 100).toFloat(),
                            (widthTOSet - 100).toFloat()
                        )
                    )

                }


            }
        }


        Spacer(modifier = Modifier.height(30.dp))


    }
}