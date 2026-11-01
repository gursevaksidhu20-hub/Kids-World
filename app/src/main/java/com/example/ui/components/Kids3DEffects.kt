package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.random.Random

/**
 * 3D Bouncy Click Modifier: shrinks down with realistic toy resistance and springs back
 */
fun Modifier.bounceClick(
    minScale: Float = 0.92f,
    onClick: () -> Unit
): Modifier = composed {
    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    this
        .scale(scale.value)
        .pointerInput(Unit) {
            while (true) {
                awaitPointerEventScope {
                    awaitFirstDown(requireUnconsumed = false)
                    scope.launch {
                        scale.animateTo(minScale, spring(stiffness = Spring.StiffnessHigh))
                    }
                    val up = waitForUpOrCancellation()
                    scope.launch {
                        scale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                    }
                    if (up != null) {
                        onClick()
                    }
                }
            }
        }
}

/**
 * Gentle floating 3D bobbing animation (creates alive, floating toy effect)
 */
fun Modifier.floating3D(
    verticalOffset: Float = 8f,
    rotationDeg: Float = 3f,
    durationMillis: Int = 2400
): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition(label = "floating_3d")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = -verticalOffset,
        targetValue = verticalOffset,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offsetY"
    )
    val rotation by infiniteTransition.animateFloat(
        initialValue = -rotationDeg,
        targetValue = rotationDeg,
        animationSpec = infiniteRepeatable(
            animation = tween((durationMillis * 1.3).toInt(), easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )

    this
        .offset { IntOffset(0, offsetY.roundToInt()) }
        .graphicsLayer {
            rotationZ = rotation
            cameraDistance = 14f * density
        }
}

/**
 * 3D Shake animation (for gentle playful error/hint feedback)
 */
fun Modifier.shake3D(trigger: Boolean): Modifier = composed {
    val anim = remember { Animatable(0f) }
    LaunchedEffect(trigger) {
        if (trigger) {
            anim.animateTo(1f, spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessMedium))
            anim.animateTo(0f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
        }
    }
    this.graphicsLayer {
        rotationZ = (anim.value * 6f) * sin(anim.value * 12f * PI.toFloat())
    }
}

/**
 * 3D Flip Card: smoothly flips 180 degrees with realistic 3D perspective cameraDistance
 */
@Composable
fun FlipCard3D(
    isFlipped: Boolean,
    onFlip: () -> Unit,
    modifier: Modifier = Modifier,
    frontContent: @Composable () -> Unit,
    backContent: @Composable () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(500, easing = FastOutSlowInEasing),
        label = "card_flip_3d"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 14f * density
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onFlip
            )
    ) {
        if (rotation <= 90f) {
            frontContent()
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationY = 180f
                    }
            ) {
                backContent()
            }
        }
    }
}

/**
 * Animated Particle Model for 3D celebrations
 */
data class ConfettiParticle(
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    var size: Float,
    var color: Color,
    var rotation: Float,
    var vRot: Float,
    var shape: Int // 0: Star, 1: Circle, 2: Ribbon
)

@Composable
fun ConfettiCelebrationOverlay(
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    if (!isActive) return

    val particles = remember {
        mutableStateListOf<ConfettiParticle>().apply {
            val colors = listOf(
                Color(0xFFFF3B30), Color(0xFFFF9500), Color(0xFFFFCC00),
                Color(0xFF34C759), Color(0xFF007AFF), Color(0xFFAF52DE),
                Color(0xFFFF2D55), Color(0xFF70C8FF), Color(0xFFFFD166)
            )
            for (i in 0 until 65) {
                val angle = Random.nextFloat() * 2 * PI.toFloat()
                val speed = Random.nextFloat() * 18f + 8f
                add(
                    ConfettiParticle(
                        x = 0.5f,
                        y = 0.4f,
                        vx = cos(angle) * speed,
                        vy = sin(angle) * speed - 6f, // initial upward burst
                        size = Random.nextFloat() * 14f + 8f,
                        color = colors.random(),
                        rotation = Random.nextFloat() * 360f,
                        vRot = Random.nextFloat() * 12f - 6f,
                        shape = Random.nextInt(3)
                    )
                )
            }
        }
    }

    var frame by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(isActive) {
        val startTime = System.currentTimeMillis()
        while (System.currentTimeMillis() - startTime < 3500) {
            particles.forEach { p ->
                p.x += p.vx * 0.003f
                p.y += p.vy * 0.003f
                p.vy += 0.45f // gravity
                p.rotation += p.vRot
            }
            frame++
            delay(16)
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        particles.forEach { p ->
            val px = p.x * w
            val py = p.y * h

            if (px in -50f..(w + 50f) && py in -50f..(h + 50f)) {
                rotate(degrees = p.rotation, pivot = Offset(px, py)) {
                    when (p.shape) {
                        0 -> drawStar(px, py, p.size, p.color)
                        1 -> drawCircle(color = p.color, radius = p.size / 2f, center = Offset(px, py))
                        else -> drawRect(
                            color = p.color,
                            topLeft = Offset(px - p.size, py - p.size / 3f),
                            size = androidx.compose.ui.geometry.Size(p.size * 2f, p.size / 1.5f)
                        )
                    }
                }
            }
        }
    }
}

private fun DrawScope.drawStar(cx: Float, cy: Float, radius: Float, color: Color) {
    val path = Path()
    val innerRadius = radius * 0.45f
    for (i in 0 until 10) {
        val r = if (i % 2 == 0) radius else innerRadius
        val theta = (i * PI / 5 - PI / 2).toFloat()
        val x = cx + r * cos(theta)
        val y = cy + r * sin(theta)
        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()
    drawPath(path, color)
}

/**
 * 3D Ripple Speaking Indicator: Emits 3D expanding concentric rings around voice buttons
 */
@Composable
fun SpeakingRipples(
    isSpeaking: Boolean,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF007AFF)
) {
    if (!isSpeaking) return

    val infiniteTransition = rememberInfiniteTransition(label = "speaking_ripples")
    val wave1 by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave1"
    )
    val alpha1 by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "alpha1"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        drawCircle(
            color = color.copy(alpha = alpha1),
            radius = (size.minDimension / 2f) * wave1
        )
    }
}
