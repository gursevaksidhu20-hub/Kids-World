package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.ForestMint
import com.example.ui.theme.LightBg
import com.example.ui.theme.SoftNavy
import com.example.ui.theme.SunnyYellow
import com.example.ui.theme.WarmGold
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun KidsTopBar(
    title: String,
    stars: Int,
    onBack: (() -> Unit)? = null,
    onHome: (() -> Unit)? = null,
    onSpeakTitle: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 3.dp, shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (onBack != null) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .testTag("nav_back_button")
                            .size(46.dp)
                            .background(LightBg, CircleShape)
                            .border(1.5.dp, Color(0xFFE2E8F0), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = DarkNavy,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }

                if (onHome != null) {
                    IconButton(
                        onClick = onHome,
                        modifier = Modifier
                            .testTag("nav_home_button")
                            .size(46.dp)
                            .background(LightBg, CircleShape)
                            .border(1.5.dp, Color(0xFFE2E8F0), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            tint = DarkNavy,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = DarkNavy
                    ),
                    maxLines = 1
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (onSpeakTitle != null) {
                    IconButton(
                        onClick = onSpeakTitle,
                        modifier = Modifier
                            .testTag("voice_title_button")
                            .size(42.dp)
                            .background(Color(0xFFEBF5FF), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Pronounce",
                            tint = Color(0xFF007AFF),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }

                // Stars Badge Pill
                StarsBadge(stars = stars)
            }
        }
    }
}

@Composable
fun StarsBadge(stars: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .testTag("stars_badge")
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFFFF9DB))
            .border(1.5.dp, WarmGold.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Stars",
                tint = WarmGold,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "$stars",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color(0xFF996500)
            )
        }
    }
}

@Composable
fun VoiceButton(
    onSpeak: () -> Unit,
    modifier: Modifier = Modifier,
    size: Int = 48,
    backgroundColor: Color = Color(0xFFE8F4FD),
    iconColor: Color = Color(0xFF007AFF)
) {
    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()
    var isRippling by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .testTag("voice_speak_button")
            .size(size.dp),
        contentAlignment = Alignment.Center
    ) {
        SpeakingRipples(isSpeaking = isRippling, color = iconColor)

        Box(
            modifier = Modifier
                .size(size.dp)
                .scale(scale.value)
                .clip(CircleShape)
                .background(backgroundColor)
                .shadow(elevation = if (isRippling) 6.dp else 2.dp, shape = CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    scope.launch {
                        isRippling = true
                        scale.animateTo(1.25f, spring(stiffness = Spring.StiffnessHigh))
                        scale.animateTo(1f, spring(stiffness = Spring.StiffnessMedium))
                        delay(2000)
                        isRippling = false
                    }
                    onSpeak()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.VolumeUp,
                contentDescription = "Play Pronunciation",
                tint = iconColor,
                modifier = Modifier.size((size * 0.55).dp)
            )
        }
    }
}

@Composable
fun CelebrationDialog(
    title: String,
    message: String,
    starsEarned: Int,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = ForestMint),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.testTag("celebration_continue_button")
            ) {
                Text("Hooray! Continue 🚀", fontWeight = FontWeight.Bold, color = Color.White)
            }
        },
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(contentAlignment = Alignment.Center) {
                    ConfettiCelebrationOverlay(isActive = true, modifier = Modifier.size(120.dp))
                    Text(text = "🎉 ⭐ 🎉", fontSize = 36.sp)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = DarkNavy,
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = SoftNavy,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                StarsBadge(stars = starsEarned)
            }
        },
        shape = RoundedCornerShape(26.dp),
        containerColor = Color.White
    )
}

@Composable
fun ParentGateDialog(
    onSuccess: () -> Unit,
    onDismiss: () -> Unit
) {
    val num1 = remember { Random.nextInt(4, 9) }
    val num2 = remember { Random.nextInt(3, 9) }
    val expected = num1 + num2
    var input by remember { mutableStateOf("") }
    var hasError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Parent Verification 🔒",
                style = MaterialTheme.typography.titleLarge,
                color = DarkNavy,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Text(
                    "To protect little learners, please ask an adult to solve this question:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SoftNavy
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    "What is $num1 + $num2 = ?",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = DarkNavy
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = input,
                    onValueChange = {
                        input = it
                        hasError = false
                    },
                    label = { Text("Answer") },
                    isError = hasError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("parent_gate_input")
                )
                if (hasError) {
                    Text(
                        "Incorrect answer. Please ask a grown-up!",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (input.trim() == expected.toString()) {
                        onSuccess()
                    } else {
                        hasError = true
                    }
                },
                modifier = Modifier.testTag("parent_gate_submit")
            ) {
                Text("Verify & Proceed")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        shape = RoundedCornerShape(20.dp)
    )
}
