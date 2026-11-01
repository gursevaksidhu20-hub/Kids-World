package com.example.ui.screens

import android.content.Context
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Redo
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FormatColorFill
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.draw.scale
import com.example.data.KidsColoringData
import com.example.model.ColoringPage
import com.example.model.DrawingStroke
import com.example.model.SavedArtwork
import com.example.ui.components.CelebrationDialog
import com.example.ui.components.KidsTopBar
import com.example.ui.components.bounceClick
import com.example.ui.components.floating3D
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.DeepSkyBlue
import com.example.ui.theme.ForestMint
import com.example.ui.theme.LightBg
import com.example.ui.theme.SoftNavy

@Composable
fun ColoringBookScreen(
    stars: Int,
    onBack: () -> Unit,
    onSaveArtwork: (SavedArtwork) -> Unit,
    savedArtworks: List<SavedArtwork>,
    onSpeak: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val pages = remember { KidsColoringData.pages }
    var selectedPage by remember { mutableStateOf<ColoringPage?>(null) }
    var viewingSavedGallery by remember { mutableStateOf(false) }

    if (selectedPage != null) {
        // Interactive Coloring Editor Mode
        ColoringEditorView(
            page = selectedPage!!,
            stars = stars,
            onBackToGallery = { selectedPage = null },
            onSave = { artwork ->
                onSaveArtwork(artwork)
                onSpeak("Artwork saved! Great job little artist!")
            },
            onSpeak = onSpeak
        )
    } else if (viewingSavedGallery) {
        // Saved Drawings Gallery
        SavedDrawingsGalleryView(
            stars = stars,
            artworks = savedArtworks.filter { it.isColoring },
            onBack = { viewingSavedGallery = false },
            onSpeak = onSpeak
        )
    } else {
        // Coloring Pages Gallery
        ColoringGalleryView(
            pages = pages,
            stars = stars,
            onBack = onBack,
            onSelectPage = { page ->
                selectedPage = page
                onSpeak("Let's color ${page.title}!")
            },
            onOpenSavedGallery = { viewingSavedGallery = true },
            onSpeak = onSpeak,
            modifier = modifier
        )
    }
}

@Composable
fun ColoringGalleryView(
    pages: List<ColoringPage>,
    stars: Int,
    onBack: () -> Unit,
    onSelectPage: (ColoringPage) -> Unit,
    onOpenSavedGallery: () -> Unit,
    onSpeak: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = remember {
        listOf("All", "Animals", "Birds", "Sea Animals", "Dinosaurs", "Fruits", "Vegetables", "Flowers", "Trees", "Vehicles", "Houses", "Space", "Fantasy", "Shapes", "Festivals")
    }
    var selectedCategory by remember { mutableStateOf("All") }

    val filteredPages = remember(selectedCategory) {
        if (selectedCategory == "All") pages else pages.filter { it.category == selectedCategory }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        KidsTopBar(
            title = "Coloring Book 🎨",
            stars = stars,
            onBack = onBack,
            onSpeakTitle = { onSpeak("Coloring Book. Tap any page to start coloring!") }
        )

        // Saved Gallery Jump Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Choose a picture to color (${filteredPages.size} pages):",
                fontSize = 13.sp,
                color = SoftNavy,
                fontWeight = FontWeight.SemiBold
            )

            Button(
                onClick = onOpenSavedGallery,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC8B6FF)),
                shape = RoundedCornerShape(14.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                modifier = Modifier.testTag("coloring_saved_gallery_button")
            ) {
                Icon(Icons.Default.PhotoLibrary, contentDescription = null, tint = DarkNavy, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("My Art 🖼️", fontSize = 12.sp, color = DarkNavy, fontWeight = FontWeight.Bold)
            }
        }

        // Category Filter Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { cat ->
                val isSelected = cat == selectedCategory
                Box(
                    modifier = Modifier
                        .testTag("coloring_cat_$cat")
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSelected) DeepSkyBlue else Color.White)
                        .border(1.5.dp, if (isSelected) DeepSkyBlue else Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
                        .clickable { selectedCategory = cat }
                        .padding(horizontal = 14.dp, vertical = 7.dp)
                ) {
                    Text(
                        text = cat,
                        color = if (isSelected) Color.White else DarkNavy,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Gallery Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredPages) { page ->
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    modifier = Modifier
                        .testTag("coloring_page_card_${page.id}")
                        .fillMaxWidth()
                        .bounceClick { onSelectPage(page) }
                        .border(1.5.dp, Color(0xFFEDF2F9), RoundedCornerShape(22.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Preview Box with SVG outlines
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFFFAFCFF))
                                .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize().padding(10.dp)) {
                                val w = size.width
                                val h = size.height
                                page.regions.forEach { region ->
                                    val path = Path()
                                    region.polygon.forEachIndexed { i, pt ->
                                        val x = pt.first * w
                                        val y = pt.second * h
                                        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                                    }
                                    path.close()
                                    drawPath(path, Color(0xFFF1F5F9))
                                    drawPath(path, Color(0xFF24324A), style = Stroke(width = 2.5f))
                                }
                            }
                            Text(
                                text = page.emoji,
                                fontSize = 28.sp,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(6.dp)
                                    .floating3D(verticalOffset = 2.5f, rotationDeg = 3f)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = page.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = DarkNavy,
                            maxLines = 1
                        )
                        Text(
                            text = "${page.category} • ${page.regions.size} parts",
                            fontSize = 11.sp,
                            color = SoftNavy
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ColoringEditorView(
    page: ColoringPage,
    stars: Int,
    onBackToGallery: () -> Unit,
    onSave: (SavedArtwork) -> Unit,
    onSpeak: (String) -> Unit
) {
    // 18 Vibrant child-friendly palette colors
    val palette = remember {
        listOf(
            0xFFFF3B30, // Red
            0xFFFF9500, // Orange
            0xFFFFCC00, // Yellow
            0xFF34C759, // Green
            0xFF00C7BE, // Teal
            0xFF30B0C7, // Cyan
            0xFF007AFF, // Blue
            0xFF5856D6, // Indigo
            0xFFAF52DE, // Purple
            0xFFFF2D55, // Pink
            0xFF8B572A, // Brown
            0xFF1C1C1E, // Black
            0xFFFFFFFF, // White (Eraser)
            0xFFFFD166, // Sunny
            0xFF8FE3B0, // Mint
            0xFFFFB5D5, // Soft Pink
            0xFFC8B6FF, // Lavender
            0xFF70C8FF  // Sky Blue
        )
    }

    var selectedColor by remember { mutableStateOf(palette[0]) }
    var isBrushMode by remember { mutableStateOf(false) } // false = Tap Fill, true = Freehand Brush
    var brushSize by remember { mutableFloatStateOf(16f) }
    var showResetDialog by remember { mutableStateOf(false) }
    var showSavedCelebration by remember { mutableStateOf(false) }

    // Region fills map: regionId -> Color Long
    val regionColors = remember { mutableStateMapOf<String, Long>() }
    // Freehand strokes drawn in brush mode
    val brushStrokes = remember { mutableStateListOf<DrawingStroke>() }

    // History undo/redo stacks
    val undoHistory = remember { mutableStateListOf<Map<String, Long>>() }
    val redoHistory = remember { mutableStateListOf<Map<String, Long>>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        // Top Bar
        KidsTopBar(
            title = page.title,
            stars = stars,
            onBack = onBackToGallery,
            onSpeakTitle = { onSpeak("Coloring ${page.title}. Select a color and tap a part to fill!") }
        )

        // Tool Action Strip: Mode, Undo, Redo, Reset, Save
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Mode Toggle (Tap Fill vs Brush)
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Button(
                    onClick = { isBrushMode = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!isBrushMode) DeepSkyBlue else Color.White
                    ),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(14.dp)).testTag("coloring_fill_tool")
                ) {
                    Icon(
                        Icons.Default.FormatColorFill,
                        contentDescription = "Fill",
                        tint = if (!isBrushMode) Color.White else DarkNavy,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "Fill",
                        color = if (!isBrushMode) Color.White else DarkNavy,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                Button(
                    onClick = { isBrushMode = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isBrushMode) DeepSkyBlue else Color.White
                    ),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(14.dp)).testTag("coloring_brush_tool")
                ) {
                    Icon(
                        Icons.Default.Brush,
                        contentDescription = "Brush",
                        tint = if (isBrushMode) Color.White else DarkNavy,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "Brush",
                        color = if (isBrushMode) Color.White else DarkNavy,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }

            // Undo / Redo / Reset / Save Actions
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        if (undoHistory.isNotEmpty()) {
                            val previousState = undoHistory.removeAt(undoHistory.lastIndex)
                            redoHistory.add(regionColors.toMap())
                            regionColors.clear()
                            regionColors.putAll(previousState)
                        } else if (brushStrokes.isNotEmpty()) {
                            brushStrokes.removeAt(brushStrokes.lastIndex)
                        }
                    },
                    enabled = undoHistory.isNotEmpty() || brushStrokes.isNotEmpty(),
                    modifier = Modifier.size(36.dp).background(Color.White, CircleShape).testTag("coloring_undo_button")
                ) {
                    Icon(Icons.AutoMirrored.Filled.Undo, contentDescription = "Undo", tint = DarkNavy, modifier = Modifier.size(18.dp))
                }

                IconButton(
                    onClick = {
                        if (redoHistory.isNotEmpty()) {
                            val nextState = redoHistory.removeAt(redoHistory.lastIndex)
                            undoHistory.add(regionColors.toMap())
                            regionColors.clear()
                            regionColors.putAll(nextState)
                        }
                    },
                    enabled = redoHistory.isNotEmpty(),
                    modifier = Modifier.size(36.dp).background(Color.White, CircleShape).testTag("coloring_redo_button")
                ) {
                    Icon(Icons.AutoMirrored.Filled.Redo, contentDescription = "Redo", tint = DarkNavy, modifier = Modifier.size(18.dp))
                }

                IconButton(
                    onClick = { showResetDialog = true },
                    modifier = Modifier.size(36.dp).background(Color.White, CircleShape).testTag("coloring_reset_button")
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Reset", tint = Color.Red, modifier = Modifier.size(18.dp))
                }

                Button(
                    onClick = {
                        val artwork = SavedArtwork(
                            id = "artwork_${System.currentTimeMillis()}",
                            title = page.title,
                            timestamp = System.currentTimeMillis(),
                            isColoring = true,
                            pageId = page.id,
                            regionColors = regionColors.toMap(),
                            previewEmoji = page.emoji
                        )
                        onSave(artwork)
                        showSavedCelebration = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ForestMint),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("coloring_save_button")
                ) {
                    Icon(Icons.Default.Save, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Save", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }

        // The Interactive Canvas
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .shadow(4.dp, RoundedCornerShape(24.dp))
                .border(2.dp, Color(0xFFEDF2F9), RoundedCornerShape(24.dp))
        ) {
            var currentPoints by remember { mutableStateOf<List<Pair<Float, Float>>>(emptyList()) }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color.White)
                    .pointerInput(isBrushMode) {
                        if (!isBrushMode) {
                            // Tap to Fill Region
                            detectTapGestures { offset ->
                                val w = size.width.toFloat()
                                val h = size.height.toFloat()
                                val normX = offset.x / w
                                val normY = offset.y / h

                                // Find clicked region via point-in-polygon
                                var matchedRegion = page.regions.firstOrNull { region ->
                                    isPointInPolygon(normX, normY, region.polygon)
                                }
                                // Fallback: find closest region center within threshold
                                if (matchedRegion == null) {
                                    matchedRegion = page.regions.minByOrNull { region ->
                                        val dx = normX - region.centerX
                                        val dy = normY - region.centerY
                                        dx * dx + dy * dy
                                    }
                                }

                                if (matchedRegion != null) {
                                    undoHistory.add(regionColors.toMap())
                                    redoHistory.clear()
                                    regionColors[matchedRegion.id] = selectedColor
                                    onSpeak(matchedRegion.label)
                                }
                            }
                        } else {
                            // Freehand Drag Brush
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
                                        brushStrokes.add(
                                            DrawingStroke(
                                                points = currentPoints,
                                                color = selectedColor,
                                                strokeWidth = brushSize,
                                                isEraser = selectedColor == 0xFFFFFFFF
                                            )
                                        )
                                        currentPoints = emptyList()
                                    }
                                }
                            )
                        }
                    }
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    // 1. Draw each region filled with its color
                    page.regions.forEach { region ->
                        val path = Path()
                        region.polygon.forEachIndexed { i, pt ->
                            val x = pt.first * w
                            val y = pt.second * h
                            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                        }
                        path.close()

                        val fillColor = Color(regionColors[region.id] ?: region.defaultColor)
                        drawPath(path, fillColor)
                    }

                    // 2. Draw clean dark outlines on top
                    page.regions.forEach { region ->
                        val path = Path()
                        region.polygon.forEachIndexed { i, pt ->
                            val x = pt.first * w
                            val y = pt.second * h
                            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                        }
                        path.close()
                        drawPath(
                            path,
                            Color(0xFF24324A),
                            style = Stroke(
                                width = 5.dp.toPx(),
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }

                    // 3. Draw freehand brush strokes
                    brushStrokes.forEach { stroke ->
                        if (stroke.points.size > 1) {
                            val strokePath = Path()
                            stroke.points.forEachIndexed { i, pt ->
                                if (i == 0) strokePath.moveTo(pt.first, pt.second)
                                else strokePath.lineTo(pt.first, pt.second)
                            }
                            drawPath(
                                strokePath,
                                Color(stroke.color),
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
                            activePath,
                            Color(selectedColor),
                            style = Stroke(
                                width = brushSize,
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }
                }
            }
        }

        // Color Palette Selector
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(palette) { colorHex ->
                        val isSelected = selectedColor == colorHex
                        val swatchScale by animateFloatAsState(
                            targetValue = if (isSelected) 1.22f else 1f,
                            label = "swatch_scale"
                        )
                        Box(
                            modifier = Modifier
                                .testTag("palette_color_${colorHex.toString(16)}")
                                .scale(swatchScale)
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(Color(colorHex))
                                .border(
                                    if (isSelected) 3.5.dp else 1.5.dp,
                                    if (isSelected) DarkNavy else Color(0xFFCBD5E1),
                                    CircleShape
                                )
                                .bounceClick { selectedColor = colorHex },
                            contentAlignment = Alignment.Center
                        ) {
                            if (colorHex == 0xFFFFFFFF) {
                                Text("🧼", fontSize = 16.sp)
                            }
                        }
                    }
                }

                // If in brush mode, show brush size selector
                if (isBrushMode) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Brush Size:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SoftNavy)
                        listOf(8f to "Small", 16f to "Medium", 28f to "Thick").forEach { (sz, label) ->
                            val isSel = brushSize == sz
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isSel) DeepSkyBlue else Color(0xFFF1F5F9))
                                    .clickable { brushSize = sz }
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
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
                }
            }
        }
    }

    // Reset confirmation dialog
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Clear Picture?", fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to start fresh and remove colors?") },
            confirmButton = {
                Button(
                    onClick = {
                        undoHistory.add(regionColors.toMap())
                        regionColors.clear()
                        brushStrokes.clear()
                        showResetDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Clear All 🧼", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Keep Coloring")
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }

    // Saved celebration dialog
    if (showSavedCelebration) {
        CelebrationDialog(
            title = "Masterpiece Saved! 🎨",
            message = "Your colorful drawing has been saved to your art gallery! You earned 2 stars! ⭐",
            starsEarned = 2,
            onDismiss = { showSavedCelebration = false }
        )
    }
}

@Composable
fun SavedDrawingsGalleryView(
    stars: Int,
    artworks: List<SavedArtwork>,
    onBack: () -> Unit,
    onSpeak: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        KidsTopBar(
            title = "Saved Gallery 🖼️",
            stars = stars,
            onBack = onBack,
            onSpeakTitle = { onSpeak("Saved Gallery. Here are all your colorful creations!") }
        )

        if (artworks.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "🎨", fontSize = 64.sp)
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "No saved drawings yet!",
                        style = MaterialTheme.typography.titleLarge,
                        color = DarkNavy,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Pick a coloring page, fill it with pretty colors, and hit Save!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SoftNavy,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(artworks) { artwork ->
                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(110.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color(0xFFE8F4FD)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = artwork.previewEmoji, fontSize = 48.sp)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = artwork.title,
                                fontWeight = FontWeight.Bold,
                                color = DarkNavy,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Colored with love ❤️",
                                fontSize = 11.sp,
                                color = SoftNavy
                            )
                        }
                    }
                }
            }
        }
    }
}

// Ray-casting algorithm to test point inside arbitrary polygon
fun isPointInPolygon(px: Float, py: Float, polygon: List<Pair<Float, Float>>): Boolean {
    var inside = false
    var j = polygon.size - 1
    for (i in polygon.indices) {
        val (xi, yi) = polygon[i]
        val (xj, yj) = polygon[j]
        val intersect = ((yi > py) != (yj > py)) &&
                (px < (xj - xi) * (py - yi) / (yj - yi) + xi)
        if (intersect) inside = !inside
        j = i
    }
    return inside
}
