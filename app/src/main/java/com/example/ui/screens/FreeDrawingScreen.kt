package com.example.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Redo
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DrawingStroke
import com.example.model.SavedArtwork
import com.example.model.StampPlacement
import com.example.ui.components.CelebrationDialog
import com.example.ui.components.KidsTopBar
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.DeepSkyBlue
import com.example.ui.theme.ForestMint
import com.example.ui.theme.LightBg
import com.example.ui.theme.SoftNavy

@Composable
fun FreeDrawingScreen(
    stars: Int,
    onBack: () -> Unit,
    onSaveArtwork: (SavedArtwork) -> Unit,
    onSpeak: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Colors palette
    val colors = remember {
        listOf(
            0xFFFF3B30, // Red
            0xFFFF9500, // Orange
            0xFFFFCC00, // Yellow
            0xFF34C759, // Green
            0xFF00C7BE, // Teal
            0xFF007AFF, // Blue
            0xFF5856D6, // Indigo
            0xFFAF52DE, // Purple
            0xFFFF2D55, // Pink
            0xFF8B572A, // Brown
            0xFF1C1C1E, // Black
            0xFFFFFFFF  // Eraser
        )
    }

    // Stamps
    val stampEmojis = remember { listOf("⭐", "❤️", "🌸", "☀️", "😊", "🌈", "🎈", "🐥", "🚗", "🚀") }

    // Canvas background options
    val canvasBgOptions = remember {
        listOf(
            0xFFFFFFFF to "White",
            0xFFFFFBEA to "Cream",
            0xFFEBF8FF to "Sky",
            0xFFF0FDF4 to "Mint",
            0xFFFDF2F8 to "Pink",
            0xFF1E293B to "Night"
        )
    }

    var selectedColor by remember { mutableStateOf(colors[0]) }
    var selectedBg by remember { mutableStateOf(canvasBgOptions[0].first) }
    var selectedStamp by remember { mutableStateOf<String?>(null) } // null = Brush mode, string = Stamp mode
    var brushSize by remember { mutableFloatStateOf(16f) }
    var showClearDialog by remember { mutableStateOf(false) }
    var showSavedDialog by remember { mutableStateOf(false) }

    // Strokes & Stamps state
    val strokes = remember { mutableStateListOf<DrawingStroke>() }
    val undoneStrokes = remember { mutableStateListOf<DrawingStroke>() }
    val stamps = remember { mutableStateListOf<StampPlacement>() }

    var currentPoints by remember { mutableStateOf<List<Pair<Float, Float>>>(emptyList()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        KidsTopBar(
            title = "Free Drawing Studio ✏️",
            stars = stars,
            onBack = onBack,
            onSpeakTitle = { onSpeak("Free Drawing Studio. Draw with your finger, add stamps, and create your art!") }
        )

        // Action controls strip: Brush / Stamp / Undo / Redo / Clear / Save
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Mode buttons
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Button(
                    onClick = {
                        selectedStamp = null
                        onSpeak("Brush mode")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedStamp == null) DeepSkyBlue else Color.White
                    ),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(14.dp)).testTag("drawing_brush_mode")
                ) {
                    Icon(
                        Icons.Default.Brush,
                        contentDescription = "Brush",
                        tint = if (selectedStamp == null) Color.White else DarkNavy,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "Brush",
                        color = if (selectedStamp == null) Color.White else DarkNavy,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                // Quick Stamp Picker trigger
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (selectedStamp != null) DeepSkyBlue else Color.White)
                        .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(14.dp))
                        .clickable {
                            if (selectedStamp == null) selectedStamp = stampEmojis[0]
                            onSpeak("Stamp mode. Tap canvas to place stamps!")
                        }
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Stamps ${selectedStamp ?: "⭐"}",
                        color = if (selectedStamp != null) Color.White else DarkNavy,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }

            // Undo / Redo / Clear / Save
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        if (strokes.isNotEmpty()) {
                            undoneStrokes.add(strokes.removeAt(strokes.lastIndex))
                        } else if (stamps.isNotEmpty()) {
                            stamps.removeAt(stamps.lastIndex)
                        }
                    },
                    enabled = strokes.isNotEmpty() || stamps.isNotEmpty(),
                    modifier = Modifier.size(36.dp).background(Color.White, CircleShape).testTag("drawing_undo")
                ) {
                    Icon(Icons.AutoMirrored.Filled.Undo, contentDescription = "Undo", tint = DarkNavy, modifier = Modifier.size(18.dp))
                }

                IconButton(
                    onClick = {
                        if (undoneStrokes.isNotEmpty()) {
                            strokes.add(undoneStrokes.removeAt(undoneStrokes.lastIndex))
                        }
                    },
                    enabled = undoneStrokes.isNotEmpty(),
                    modifier = Modifier.size(36.dp).background(Color.White, CircleShape).testTag("drawing_redo")
                ) {
                    Icon(Icons.AutoMirrored.Filled.Redo, contentDescription = "Redo", tint = DarkNavy, modifier = Modifier.size(18.dp))
                }

                IconButton(
                    onClick = { showClearDialog = true },
                    modifier = Modifier.size(36.dp).background(Color.White, CircleShape).testTag("drawing_clear")
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Clear", tint = Color.Red, modifier = Modifier.size(18.dp))
                }

                Button(
                    onClick = {
                        val artwork = SavedArtwork(
                            id = "drawing_${System.currentTimeMillis()}",
                            title = "Free Drawing Art",
                            timestamp = System.currentTimeMillis(),
                            isColoring = false,
                            previewEmoji = "🎨"
                        )
                        onSaveArtwork(artwork)
                        showSavedDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ForestMint),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("drawing_save_button")
                ) {
                    Icon(Icons.Default.Save, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Save", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }

        // Stamps Bar (visible when stamp mode is active)
        if (selectedStamp != null) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(stampEmojis) { emoji ->
                    val isSel = selectedStamp == emoji
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(if (isSel) Color(0xFFFFF9DB) else Color.White)
                            .border(1.5.dp, if (isSel) Color(0xFFFFAA00) else Color(0xFFE2E8F0), CircleShape)
                            .clickable {
                                selectedStamp = emoji
                                onSpeak(emoji)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = emoji, fontSize = 22.sp)
                    }
                }
            }
        }

        // The Drawing Canvas
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(selectedBg)),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .shadow(4.dp, RoundedCornerShape(24.dp))
                .border(2.dp, Color(0xFFEDF2F9), RoundedCornerShape(24.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(selectedStamp, selectedColor, brushSize) {
                        if (selectedStamp != null) {
                            // Tap to place Stamp
                            detectTapGestures { offset ->
                                stamps.add(
                                    StampPlacement(
                                        emoji = selectedStamp!!,
                                        x = offset.x,
                                        y = offset.y,
                                        size = 36f
                                    )
                                )
                            }
                        } else {
                            // Drag to draw brush stroke
                            detectDragGestures(
                                onDragStart = { offset ->
                                    currentPoints = listOf(offset.x to offset.y)
                                },
                                onDrag = { change, _ ->
                                    change.consume()
                                    currentPoints = currentPoints + (change.position.x to change.position.y)
                                },
                                onDragEnd = {
                                    if (currentPoints.isNotEmpty()) {
                                        strokes.add(
                                            DrawingStroke(
                                                points = currentPoints,
                                                color = selectedColor,
                                                strokeWidth = brushSize,
                                                isEraser = selectedColor == 0xFFFFFFFF
                                            )
                                        )
                                        undoneStrokes.clear()
                                        currentPoints = emptyList()
                                    }
                                }
                            )
                        }
                    }
            ) {
                // Drawing Canvas
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Draw saved strokes
                    strokes.forEach { stroke ->
                        if (stroke.points.size > 1) {
                            val path = Path()
                            stroke.points.forEachIndexed { i, pt ->
                                if (i == 0) path.moveTo(pt.first, pt.second)
                                else path.lineTo(pt.first, pt.second)
                            }
                            drawPath(
                                path = path,
                                color = if (stroke.isEraser) Color(selectedBg) else Color(stroke.color),
                                style = Stroke(
                                    width = stroke.strokeWidth,
                                    cap = StrokeCap.Round,
                                    join = StrokeJoin.Round
                                )
                            )
                        }
                    }

                    // Active drawing stroke
                    if (currentPoints.size > 1) {
                        val activePath = Path()
                        currentPoints.forEachIndexed { i, pt ->
                            if (i == 0) activePath.moveTo(pt.first, pt.second)
                            else activePath.lineTo(pt.first, pt.second)
                        }
                        drawPath(
                            path = activePath,
                            color = if (selectedColor == 0xFFFFFFFF) Color(selectedBg) else Color(selectedColor),
                            style = Stroke(
                                width = brushSize,
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }

                    // Render placed stamps
                    if (stamps.isNotEmpty()) {
                        val stampPaint = android.graphics.Paint().apply {
                            textSize = 36.dp.toPx()
                            textAlign = android.graphics.Paint.Align.CENTER
                            isAntiAlias = true
                        }
                        stamps.forEach { stamp ->
                            drawContext.canvas.nativeCanvas.drawText(stamp.emoji, stamp.x, stamp.y, stampPaint)
                        }
                    }
                }
            }
        }

        // Bottom Tools: Palette & Canvas Backgrounds
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                // Color Swatches
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(colors) { colorHex ->
                        val isSel = selectedColor == colorHex && selectedStamp == null
                        Box(
                            modifier = Modifier
                                .testTag("free_color_${colorHex.toString(16)}")
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(Color(colorHex))
                                .border(
                                    if (isSel) 3.5.dp else 1.5.dp,
                                    if (isSel) DarkNavy else Color(0xFFCBD5E1),
                                    CircleShape
                                )
                                .clickable {
                                    selectedColor = colorHex
                                    selectedStamp = null
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (colorHex == 0xFFFFFFFF) {
                                Text("🧼", fontSize = 16.sp)
                            }
                        }
                    }
                }

                // Brush Size and Canvas Paper Selector
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Brush sizes
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Size:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SoftNavy)
                        listOf(8f to "S", 16f to "M", 28f to "L").forEach { (sz, label) ->
                            val isSel = brushSize == sz
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(if (isSel) DeepSkyBlue else Color(0xFFF1F5F9))
                                    .clickable { brushSize = sz },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    label,
                                    color = if (isSel) Color.White else DarkNavy,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    // Canvas Background colors
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Paper:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SoftNavy)
                        canvasBgOptions.forEach { (bgHex, _) ->
                            val isSel = selectedBg == bgHex
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(Color(bgHex))
                                    .border(if (isSel) 2.dp else 1.dp, if (isSel) DarkNavy else Color(0xFFCBD5E1), CircleShape)
                                    .clickable { selectedBg = bgHex }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = { Text("Clear Canvas?", fontWeight = FontWeight.Bold) },
            text = { Text("Start fresh with a clean white canvas?") },
            confirmButton = {
                Button(
                    onClick = {
                        strokes.clear()
                        undoneStrokes.clear()
                        stamps.clear()
                        showClearDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Clear All 🧼", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) {
                    Text("Keep Drawing")
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }

    if (showSavedDialog) {
        CelebrationDialog(
            title = "Drawing Saved! ⭐",
            message = "Your beautiful artwork has been saved to your gallery! Keep drawing!",
            starsEarned = 2,
            onDismiss = { showSavedDialog = false }
        )
    }
}
