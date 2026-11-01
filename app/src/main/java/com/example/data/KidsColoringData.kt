package com.example.data

import com.example.model.ColoringPage
import com.example.model.ColoringRegion

object KidsColoringData {

    // Helper to generate a rectangular or polygonal region easily
    private fun boxRegion(
        id: String,
        label: String,
        left: Float,
        top: Float,
        right: Float,
        bottom: Float
    ): ColoringRegion {
        val poly = listOf(
            left to top,
            right to top,
            right to bottom,
            left to bottom
        )
        return ColoringRegion(
            id = id,
            label = label,
            polygon = poly,
            centerX = (left + right) / 2f,
            centerY = (top + bottom) / 2f
        )
    }

    private fun ovalRegion(
        id: String,
        label: String,
        cx: Float,
        cy: Float,
        rx: Float,
        ry: Float,
        steps: Int = 16
    ): ColoringRegion {
        val points = mutableListOf<Pair<Float, Float>>()
        for (i in 0 until steps) {
            val theta = (i * 2.0 * Math.PI / steps).toFloat()
            val x = (cx + rx * Math.cos(theta.toDouble())).toFloat()
            val y = (cy + ry * Math.sin(theta.toDouble())).toFloat()
            points.add(x to y)
        }
        return ColoringRegion(
            id = id,
            label = label,
            polygon = points,
            centerX = cx,
            centerY = cy
        )
    }

    val pages: List<ColoringPage> = listOf(
        // 1. Animals - Cute Cat
        ColoringPage(
            id = "cat",
            title = "Happy Kitten",
            category = "Animals",
            emoji = "🐱",
            regions = listOf(
                ovalRegion("cat_head", "Cat Head", 0.5f, 0.35f, 0.28f, 0.22f),
                listOf(0.25f to 0.22f, 0.35f to 0.10f, 0.42f to 0.20f).let {
                    ColoringRegion("cat_ear_l", "Left Ear", it, 0.34f, 0.17f)
                },
                listOf(0.58f to 0.20f, 0.65f to 0.10f, 0.75f to 0.22f).let {
                    ColoringRegion("cat_ear_r", "Right Ear", it, 0.66f, 0.17f)
                },
                ovalRegion("cat_body", "Body", 0.5f, 0.68f, 0.25f, 0.24f),
                ovalRegion("cat_belly", "Belly", 0.5f, 0.70f, 0.15f, 0.15f),
                ovalRegion("cat_paw_l", "Left Paw", 0.38f, 0.90f, 0.08f, 0.05f),
                ovalRegion("cat_paw_r", "Right Paw", 0.62f, 0.90f, 0.08f, 0.05f),
                listOf(0.72f to 0.70f, 0.88f to 0.60f, 0.92f to 0.75f, 0.75f to 0.80f).let {
                    ColoringRegion("cat_tail", "Tail", it, 0.82f, 0.71f)
                },
                ovalRegion("cat_collar", "Collar Bell", 0.5f, 0.52f, 0.06f, 0.04f)
            )
        ),

        // 2. Animals - Playful Puppy
        ColoringPage(
            id = "dog",
            title = "Playful Puppy",
            category = "Animals",
            emoji = "🐶",
            regions = listOf(
                ovalRegion("dog_head", "Puppy Head", 0.5f, 0.36f, 0.27f, 0.22f),
                ovalRegion("dog_snout", "Snout", 0.5f, 0.44f, 0.12f, 0.09f),
                listOf(0.20f to 0.26f, 0.14f to 0.45f, 0.28f to 0.42f).let {
                    ColoringRegion("dog_ear_l", "Left Floppy Ear", it, 0.21f, 0.38f)
                },
                listOf(0.72f to 0.42f, 0.86f to 0.45f, 0.80f to 0.26f).let {
                    ColoringRegion("dog_ear_r", "Right Floppy Ear", it, 0.79f, 0.38f)
                },
                ovalRegion("dog_body", "Body", 0.5f, 0.70f, 0.26f, 0.22f),
                ovalRegion("dog_belly", "Belly Patch", 0.5f, 0.72f, 0.14f, 0.14f),
                ovalRegion("dog_leg_l", "Left Paw", 0.36f, 0.91f, 0.09f, 0.06f),
                ovalRegion("dog_leg_r", "Right Paw", 0.64f, 0.91f, 0.09f, 0.06f),
                listOf(0.74f to 0.66f, 0.90f to 0.52f, 0.92f to 0.60f, 0.76f to 0.74f).let {
                    ColoringRegion("dog_tail", "Waggy Tail", it, 0.84f, 0.63f)
                }
            )
        ),

        // 3. Animals - Gentle Elephant
        ColoringPage(
            id = "elephant",
            title = "Gentle Elephant",
            category = "Animals",
            emoji = "🐘",
            regions = listOf(
                ovalRegion("ele_body", "Giant Body", 0.55f, 0.58f, 0.32f, 0.25f),
                ovalRegion("ele_head", "Head", 0.32f, 0.44f, 0.20f, 0.18f),
                listOf(0.20f to 0.34f, 0.10f to 0.50f, 0.18f to 0.62f, 0.28f to 0.52f).let {
                    ColoringRegion("ele_ear", "Big Flapping Ear", it, 0.19f, 0.50f)
                },
                listOf(0.22f to 0.52f, 0.14f to 0.75f, 0.20f to 0.82f, 0.26f to 0.60f).let {
                    ColoringRegion("ele_trunk", "Curling Trunk", it, 0.18f, 0.67f)
                },
                boxRegion("ele_leg_1", "Front Leg", 0.35f, 0.75f, 0.45f, 0.94f),
                boxRegion("ele_leg_2", "Back Leg", 0.65f, 0.75f, 0.75f, 0.94f),
                ovalRegion("ele_blanket", "Festival Blanket", 0.55f, 0.50f, 0.20f, 0.12f)
            )
        ),

        // 4. Animals - Little Teddy Bear
        ColoringPage(
            id = "teddy_bear",
            title = "Cuddly Teddy Bear",
            category = "Animals",
            emoji = "🧸",
            regions = listOf(
                ovalRegion("tb_head", "Teddy Head", 0.5f, 0.34f, 0.24f, 0.20f),
                ovalRegion("tb_ear_l", "Left Ear", 0.28f, 0.18f, 0.08f, 0.08f),
                ovalRegion("tb_ear_r", "Right Ear", 0.72f, 0.18f, 0.08f, 0.08f),
                ovalRegion("tb_muzzle", "Soft Muzzle", 0.5f, 0.42f, 0.11f, 0.08f),
                ovalRegion("tb_body", "Round Tummy", 0.5f, 0.66f, 0.24f, 0.22f),
                ovalRegion("tb_belly", "Belly Patch", 0.5f, 0.68f, 0.14f, 0.14f),
                ovalRegion("tb_arm_l", "Left Arm", 0.25f, 0.58f, 0.08f, 0.14f),
                ovalRegion("tb_arm_r", "Right Arm", 0.75f, 0.58f, 0.08f, 0.14f),
                ovalRegion("tb_foot_l", "Left Foot", 0.36f, 0.88f, 0.10f, 0.08f),
                ovalRegion("tb_foot_r", "Right Foot", 0.64f, 0.88f, 0.10f, 0.08f)
            )
        ),

        // 5. Animals - Friendly Lion
        ColoringPage(
            id = "lion",
            title = "King Lion",
            category = "Animals",
            emoji = "🦁",
            regions = listOf(
                ovalRegion("lion_mane", "Fluffy Mane", 0.5f, 0.38f, 0.36f, 0.30f),
                ovalRegion("lion_face", "Friendly Face", 0.5f, 0.38f, 0.22f, 0.18f),
                ovalRegion("lion_nose", "Snout", 0.5f, 0.45f, 0.10f, 0.07f),
                ovalRegion("lion_body", "Body", 0.5f, 0.74f, 0.26f, 0.20f),
                boxRegion("lion_leg_l", "Front Leg", 0.36f, 0.82f, 0.45f, 0.95f),
                boxRegion("lion_leg_r", "Back Leg", 0.55f, 0.82f, 0.64f, 0.95f),
                ovalRegion("lion_crown", "Royal Crown", 0.5f, 0.12f, 0.16f, 0.08f)
            )
        ),

        // 6. Birds - Sweet Little Bird
        ColoringPage(
            id = "bird",
            title = "Singing Songbird",
            category = "Birds",
            emoji = "🐦",
            regions = listOf(
                ovalRegion("bird_body", "Plump Body", 0.46f, 0.52f, 0.26f, 0.24f),
                ovalRegion("bird_head", "Round Head", 0.34f, 0.36f, 0.16f, 0.14f),
                listOf(0.20f to 0.36f, 0.10f to 0.40f, 0.20f to 0.44f).let {
                    ColoringRegion("bird_beak", "Orange Beak", it, 0.16f, 0.40f)
                },
                ovalRegion("bird_wing", "Feathered Wing", 0.52f, 0.54f, 0.16f, 0.12f),
                listOf(0.68f to 0.54f, 0.90f to 0.44f, 0.86f to 0.62f).let {
                    ColoringRegion("bird_tail", "Tail Feathers", it, 0.81f, 0.53f)
                },
                boxRegion("bird_branch", "Tree Branch", 0.10f, 0.78f, 0.90f, 0.84f)
            )
        ),

        // 7. Birds - Proud Peacock
        ColoringPage(
            id = "peacock",
            title = "Royal Peacock",
            category = "Birds",
            emoji = "🦚",
            regions = listOf(
                ovalRegion("pc_fan", "Huge Feather Fan", 0.5f, 0.42f, 0.44f, 0.36f),
                ovalRegion("pc_body", "Graceful Body", 0.5f, 0.62f, 0.14f, 0.22f),
                ovalRegion("pc_head", "Crown Head", 0.5f, 0.38f, 0.08f, 0.08f),
                listOf(0.48f to 0.40f, 0.40f to 0.42f, 0.48f to 0.44f).let {
                    ColoringRegion("pc_beak", "Beak", it, 0.45f, 0.42f)
                },
                ovalRegion("pc_crest", "Feather Crest", 0.5f, 0.28f, 0.06f, 0.06f),
                boxRegion("pc_stand", "Grass Stand", 0.35f, 0.86f, 0.65f, 0.94f)
            )
        ),

        // 8. Birds - Playful Duckling
        ColoringPage(
            id = "duck",
            title = "Swimming Duckling",
            category = "Birds",
            emoji = "🦆",
            regions = listOf(
                ovalRegion("duck_head", "Duck Head", 0.35f, 0.32f, 0.16f, 0.15f),
                listOf(0.20f to 0.32f, 0.08f to 0.36f, 0.20f to 0.40f).let {
                    ColoringRegion("duck_bill", "Yellow Bill", it, 0.15f, 0.36f)
                },
                ovalRegion("duck_body", "Body", 0.52f, 0.58f, 0.28f, 0.20f),
                ovalRegion("duck_wing", "Side Wing", 0.54f, 0.58f, 0.16f, 0.10f),
                boxRegion("duck_water", "Rippling Pond", 0.10f, 0.78f, 0.90f, 0.92f)
            )
        ),

        // 9. Sea Animals - Happy Dolphin
        ColoringPage(
            id = "dolphin",
            title = "Jumping Dolphin",
            category = "Sea Animals",
            emoji = "🐬",
            regions = listOf(
                ovalRegion("dol_body", "Smooth Body", 0.5f, 0.46f, 0.38f, 0.18f),
                listOf(0.46f to 0.30f, 0.52f to 0.16f, 0.58f to 0.30f).let {
                    ColoringRegion("dol_fin_dorsal", "Top Fin", it, 0.52f, 0.25f)
                },
                listOf(0.42f to 0.56f, 0.38f to 0.72f, 0.48f to 0.62f).let {
                    ColoringRegion("dol_fin_side", "Flipper", it, 0.42f, 0.63f)
                },
                listOf(0.82f to 0.48f, 0.96f to 0.38f, 0.96f to 0.60f).let {
                    ColoringRegion("dol_tail", "Tail Flukes", it, 0.91f, 0.49f)
                },
                boxRegion("dol_waves", "Ocean Waves", 0.10f, 0.76f, 0.90f, 0.92f)
            )
        ),

        // 10. Sea Animals - Friendly Octopus
        ColoringPage(
            id = "octopus",
            title = "Friendly Octopus",
            category = "Sea Animals",
            emoji = "🐙",
            regions = listOf(
                ovalRegion("oct_head", "Round Dome Head", 0.5f, 0.34f, 0.28f, 0.24f),
                boxRegion("oct_arm1", "Tentacle 1", 0.15f, 0.58f, 0.25f, 0.88f),
                boxRegion("oct_arm2", "Tentacle 2", 0.30f, 0.58f, 0.40f, 0.88f),
                boxRegion("oct_arm3", "Tentacle 3", 0.45f, 0.58f, 0.55f, 0.88f),
                boxRegion("oct_arm4", "Tentacle 4", 0.60f, 0.58f, 0.70f, 0.88f),
                boxRegion("oct_arm5", "Tentacle 5", 0.75f, 0.58f, 0.85f, 0.88f),
                ovalRegion("oct_bubble1", "Sea Bubble", 0.20f, 0.20f, 0.06f, 0.06f),
                ovalRegion("oct_bubble2", "Sea Bubble", 0.80f, 0.22f, 0.07f, 0.07f)
            )
        ),

        // 11. Dinosaurs - Baby Dino
        ColoringPage(
            id = "dino",
            title = "Baby T-Rex",
            category = "Dinosaurs",
            emoji = "🦖",
            regions = listOf(
                ovalRegion("dino_head", "Cute Dino Head", 0.36f, 0.30f, 0.22f, 0.18f),
                ovalRegion("dino_body", "Chubby Body", 0.52f, 0.60f, 0.26f, 0.24f),
                listOf(0.70f to 0.60f, 0.92f to 0.55f, 0.86f to 0.72f).let {
                    ColoringRegion("dino_tail", "Sturdy Tail", it, 0.83f, 0.62f)
                },
                boxRegion("dino_leg_l", "Left Stompy Foot", 0.40f, 0.82f, 0.50f, 0.95f),
                boxRegion("dino_leg_r", "Right Stompy Foot", 0.58f, 0.82f, 0.68f, 0.95f),
                ovalRegion("dino_egg", "Hatching Egg Shell", 0.52f, 0.78f, 0.34f, 0.15f)
            )
        ),

        // 12. Dinosaurs - Gentle Long-neck
        ColoringPage(
            id = "brachio",
            title = "Long-Neck Brachio",
            category = "Dinosaurs",
            emoji = "🦕",
            regions = listOf(
                ovalRegion("brach_head", "Little Head", 0.22f, 0.18f, 0.10f, 0.08f),
                boxRegion("brach_neck", "Long Graceful Neck", 0.24f, 0.24f, 0.36f, 0.56f),
                ovalRegion("brach_body", "Giant Body", 0.52f, 0.64f, 0.30f, 0.20f),
                listOf(0.78f to 0.64f, 0.95f to 0.70f, 0.88f to 0.80f).let {
                    ColoringRegion("brach_tail", "Whipping Tail", it, 0.87f, 0.71f)
                },
                boxRegion("brach_leg1", "Front Leg", 0.38f, 0.80f, 0.46f, 0.96f),
                boxRegion("brach_leg2", "Back Leg", 0.64f, 0.80f, 0.72f, 0.96f)
            )
        ),

        // 13. Fruits - Juicy Apple
        ColoringPage(
            id = "apple",
            title = "Crispy Red Apple",
            category = "Fruits",
            emoji = "🍎",
            regions = listOf(
                ovalRegion("app_left", "Left Half", 0.42f, 0.54f, 0.22f, 0.26f),
                ovalRegion("app_right", "Right Half", 0.58f, 0.54f, 0.22f, 0.26f),
                boxRegion("app_stem", "Woody Stem", 0.48f, 0.20f, 0.52f, 0.32f),
                ovalRegion("app_leaf", "Green Leaf", 0.62f, 0.22f, 0.12f, 0.07f)
            )
        ),

        // 14. Fruits - Sweet Strawberry
        ColoringPage(
            id = "strawberry",
            title = "Sweet Strawberry",
            category = "Fruits",
            emoji = "🍓",
            regions = listOf(
                listOf(0.24f to 0.36f, 0.76f to 0.36f, 0.54f to 0.88f, 0.46f to 0.88f).let {
                    ColoringRegion("berry_body", "Berry Flesh", it, 0.50f, 0.56f)
                },
                boxRegion("berry_stem", "Little Stem", 0.48f, 0.16f, 0.52f, 0.26f),
                listOf(0.30f to 0.32f, 0.50f to 0.24f, 0.70f to 0.32f, 0.50f to 0.36f).let {
                    ColoringRegion("berry_leaves", "Calyx Crown", it, 0.50f, 0.30f)
                }
            )
        ),

        // 15. Fruits - Juicy Watermelon
        ColoringPage(
            id = "watermelon",
            title = "Watermelon Slice",
            category = "Fruits",
            emoji = "🍉",
            regions = listOf(
                ovalRegion("wm_rind", "Green Outer Rind", 0.50f, 0.76f, 0.42f, 0.12f),
                ovalRegion("wm_white", "Crisp White Layer", 0.50f, 0.72f, 0.38f, 0.10f),
                listOf(0.18f to 0.64f, 0.82f to 0.64f, 0.50f to 0.22f).let {
                    ColoringRegion("wm_flesh", "Juicy Red Triangle", it, 0.50f, 0.50f)
                }
            )
        ),

        // 16. Vegetables - Crunchy Carrot
        ColoringPage(
            id = "carrot",
            title = "Crunchy Carrot",
            category = "Vegetables",
            emoji = "🥕",
            regions = listOf(
                listOf(0.36f to 0.34f, 0.64f to 0.34f, 0.52f to 0.90f, 0.48f to 0.90f).let {
                    ColoringRegion("cr_root", "Orange Root", it, 0.50f, 0.58f)
                },
                listOf(0.46f to 0.12f, 0.40f to 0.32f, 0.52f to 0.32f).let {
                    ColoringRegion("cr_top1", "Left Green Leaf", it, 0.44f, 0.22f)
                },
                listOf(0.50f to 0.08f, 0.47f to 0.32f, 0.53f to 0.32f).let {
                    ColoringRegion("cr_top2", "Center Green Leaf", it, 0.50f, 0.20f)
                },
                listOf(0.54f to 0.12f, 0.48f to 0.32f, 0.60f to 0.32f).let {
                    ColoringRegion("cr_top3", "Right Green Leaf", it, 0.56f, 0.22f)
                }
            )
        ),

        // 17. Vegetables - Shiny Eggplant
        ColoringPage(
            id = "eggplant",
            title = "Purple Eggplant",
            category = "Vegetables",
            emoji = "🍆",
            regions = listOf(
                ovalRegion("ep_body", "Purple Belly", 0.50f, 0.62f, 0.26f, 0.28f),
                boxRegion("ep_stem", "Stem Cap", 0.46f, 0.20f, 0.54f, 0.34f),
                listOf(0.34f to 0.38f, 0.50f to 0.30f, 0.66f to 0.38f, 0.50f to 0.42f).let {
                    ColoringRegion("ep_crown", "Leafy Hat", it, 0.50f, 0.36f)
                }
            )
        ),

        // 18. Flowers - Smiling Sunflower
        ColoringPage(
            id = "sunflower",
            title = "Sunny Sunflower",
            category = "Flowers",
            emoji = "🌻",
            regions = listOf(
                ovalRegion("sf_center", "Seed Center", 0.5f, 0.38f, 0.18f, 0.18f),
                ovalRegion("sf_petals", "Sun Petals Ring", 0.5f, 0.38f, 0.36f, 0.32f),
                boxRegion("sf_stem", "Tall Stem", 0.47f, 0.68f, 0.53f, 0.94f),
                ovalRegion("sf_leaf_l", "Left Leaf", 0.34f, 0.76f, 0.14f, 0.07f),
                ovalRegion("sf_leaf_r", "Right Leaf", 0.66f, 0.82f, 0.14f, 0.07f)
            )
        ),

        // 19. Flowers - Pretty Tulip
        ColoringPage(
            id = "tulip",
            title = "Spring Tulip",
            category = "Flowers",
            emoji = "🌷",
            regions = listOf(
                ovalRegion("tulip_center", "Center Petal", 0.5f, 0.36f, 0.12f, 0.18f),
                ovalRegion("tulip_left", "Left Petal", 0.38f, 0.38f, 0.11f, 0.16f),
                ovalRegion("tulip_right", "Right Petal", 0.62f, 0.38f, 0.11f, 0.16f),
                boxRegion("tulip_stem", "Slender Stem", 0.48f, 0.54f, 0.52f, 0.92f),
                listOf(0.28f to 0.85f, 0.48f to 0.68f, 0.48f to 0.90f).let {
                    ColoringRegion("tulip_leaf", "Pointed Leaf", it, 0.40f, 0.80f)
                }
            )
        ),

        // 20. Trees - Big Oak Tree
        ColoringPage(
            id = "oak_tree",
            title = "Shady Oak Tree",
            category = "Trees",
            emoji = "🌳",
            regions = listOf(
                ovalRegion("tree_crown", "Green Leaf Canopy", 0.5f, 0.36f, 0.38f, 0.28f),
                boxRegion("tree_trunk", "Strong Trunk", 0.42f, 0.62f, 0.58f, 0.92f),
                boxRegion("tree_grass", "Grassy Meadow", 0.10f, 0.88f, 0.90f, 0.96f)
            )
        ),

        // 21. Vehicles - Zooming Racecar
        ColoringPage(
            id = "car",
            title = "Zooming Car",
            category = "Vehicles",
            emoji = "🚗",
            regions = listOf(
                boxRegion("car_body", "Car Body Chassis", 0.14f, 0.52f, 0.86f, 0.74f),
                listOf(0.28f to 0.52f, 0.38f to 0.32f, 0.68f to 0.32f, 0.76f to 0.52f).let {
                    ColoringRegion("car_roof", "Cabin Roof", it, 0.52f, 0.42f)
                },
                boxRegion("car_window", "Windshield Glass", 0.36f, 0.36f, 0.66f, 0.50f),
                ovalRegion("car_wheel_f", "Front Wheel", 0.30f, 0.74f, 0.11f, 0.11f),
                ovalRegion("car_wheel_b", "Back Wheel", 0.70f, 0.74f, 0.11f, 0.11f),
                ovalRegion("car_hub_f", "Front Hubcap", 0.30f, 0.74f, 0.05f, 0.05f),
                ovalRegion("car_hub_b", "Back Hubcap", 0.70f, 0.74f, 0.05f, 0.05f)
            )
        ),

        // 22. Vehicles - Flying Airplane
        ColoringPage(
            id = "airplane",
            title = "Sky Airplane",
            category = "Vehicles",
            emoji = "✈️",
            regions = listOf(
                ovalRegion("plane_body", "Fuselage Body", 0.5f, 0.5f, 0.38f, 0.12f),
                listOf(0.42f to 0.42f, 0.56f to 0.15f, 0.68f to 0.42f).let {
                    ColoringRegion("plane_wing_t", "Top Wing", it, 0.55f, 0.28f)
                },
                listOf(0.42f to 0.58f, 0.56f to 0.85f, 0.68f to 0.58f).let {
                    ColoringRegion("plane_wing_b", "Bottom Wing", it, 0.55f, 0.72f)
                },
                listOf(0.78f to 0.46f, 0.88f to 0.28f, 0.90f to 0.48f).let {
                    ColoringRegion("plane_tail", "Rudder Fin", it, 0.85f, 0.38f)
                },
                ovalRegion("plane_cockpit", "Cockpit Glass", 0.24f, 0.49f, 0.08f, 0.06f)
            )
        ),

        // 23. Houses - Cozy Cottage
        ColoringPage(
            id = "cottage",
            title = "Cozy Sweet Home",
            category = "Houses",
            emoji = "🏠",
            regions = listOf(
                boxRegion("house_walls", "Front Wall", 0.25f, 0.48f, 0.75f, 0.88f),
                listOf(0.18f to 0.48f, 0.50f to 0.18f, 0.82f to 0.48f).let {
                    ColoringRegion("house_roof", "Triangular Roof", it, 0.50f, 0.33f)
                },
                boxRegion("house_door", "Welcome Door", 0.44f, 0.64f, 0.56f, 0.88f),
                boxRegion("house_win_l", "Left Window", 0.28f, 0.56f, 0.38f, 0.68f),
                boxRegion("house_win_r", "Right Window", 0.62f, 0.56f, 0.72f, 0.68f),
                boxRegion("house_chimney", "Brick Chimney", 0.66f, 0.18f, 0.74f, 0.34f)
            )
        ),

        // 24. Space - Rocket Ship
        ColoringPage(
            id = "rocket",
            title = "Cosmic Rocket",
            category = "Space",
            emoji = "🚀",
            regions = listOf(
                ovalRegion("rkt_body", "Rocket Body", 0.5f, 0.46f, 0.16f, 0.30f),
                listOf(0.38f to 0.24f, 0.50f to 0.08f, 0.62f to 0.24f).let {
                    ColoringRegion("rkt_nose", "Nose Cone", it, 0.50f, 0.16f)
                },
                listOf(0.34f to 0.62f, 0.18f to 0.80f, 0.36f to 0.76f).let {
                    ColoringRegion("rkt_fin_l", "Left Booster Fin", it, 0.28f, 0.72f)
                },
                listOf(0.66f to 0.62f, 0.82f to 0.80f, 0.64f to 0.76f).let {
                    ColoringRegion("rkt_fin_r", "Right Booster Fin", it, 0.72f, 0.72f)
                },
                ovalRegion("rkt_porthole", "Astronaut Window", 0.5f, 0.40f, 0.08f, 0.08f),
                listOf(0.42f to 0.76f, 0.50f to 0.94f, 0.58f to 0.76f).let {
                    ColoringRegion("rkt_fire", "Exhaust Flame", it, 0.50f, 0.85f)
                }
            )
        ),

        // 25. Space - Saturn Planet
        ColoringPage(
            id = "saturn",
            title = "Ringed Planet Saturn",
            category = "Space",
            emoji = "🪐",
            regions = listOf(
                ovalRegion("sat_planet", "Gas Giant Globe", 0.5f, 0.5f, 0.26f, 0.26f),
                ovalRegion("sat_ring", "Glistening Rings", 0.5f, 0.5f, 0.45f, 0.12f),
                ovalRegion("sat_star1", "Sparkling Star", 0.22f, 0.22f, 0.06f, 0.06f),
                ovalRegion("sat_star2", "Tiny Star", 0.78f, 0.76f, 0.05f, 0.05f)
            )
        ),

        // 26. Fantasy - Magic Unicorn
        ColoringPage(
            id = "unicorn",
            title = "Magical Unicorn",
            category = "Fantasy",
            emoji = "🦄",
            regions = listOf(
                ovalRegion("uni_head", "Unicorn Head", 0.38f, 0.38f, 0.22f, 0.18f),
                listOf(0.36f to 0.22f, 0.44f to 0.06f, 0.48f to 0.24f).let {
                    ColoringRegion("uni_horn", "Golden Spiral Horn", it, 0.42f, 0.15f)
                },
                ovalRegion("uni_mane", "Rainbow Mane", 0.58f, 0.38f, 0.16f, 0.24f),
                ovalRegion("uni_body", "White Body", 0.56f, 0.66f, 0.28f, 0.22f),
                boxRegion("uni_leg_f", "Front Hoof", 0.42f, 0.80f, 0.50f, 0.95f),
                boxRegion("uni_leg_b", "Back Hoof", 0.68f, 0.80f, 0.76f, 0.95f)
            )
        ),

        // 27. Shapes - Smiling Star
        ColoringPage(
            id = "star",
            title = "Shining Star Friend",
            category = "Shapes",
            emoji = "⭐",
            regions = listOf(
                listOf(
                    0.50f to 0.14f, 0.60f to 0.36f, 0.85f to 0.38f, 0.66f to 0.56f,
                    0.72f to 0.82f, 0.50f to 0.68f, 0.28f to 0.82f, 0.34f to 0.56f,
                    0.15f to 0.38f, 0.40f to 0.36f
                ).let {
                    ColoringRegion("star_body", "Five Point Star", it, 0.50f, 0.50f)
                },
                ovalRegion("star_cheek_l", "Rosy Left Cheek", 0.40f, 0.54f, 0.04f, 0.03f),
                ovalRegion("star_cheek_r", "Rosy Right Cheek", 0.60f, 0.54f, 0.04f, 0.03f)
            )
        ),

        // 28. Shapes - Loving Heart
        ColoringPage(
            id = "heart",
            title = "Valentine Heart",
            category = "Shapes",
            emoji = "❤️",
            regions = listOf(
                ovalRegion("heart_lobe_l", "Left Heart Lobe", 0.38f, 0.38f, 0.22f, 0.22f),
                ovalRegion("heart_lobe_r", "Right Heart Lobe", 0.62f, 0.38f, 0.22f, 0.22f),
                listOf(0.18f to 0.46f, 0.82f to 0.46f, 0.50f to 0.88f).let {
                    ColoringRegion("heart_tip", "Bottom Tip", it, 0.50f, 0.64f)
                }
            )
        ),

        // 29. Everyday Life - Sweet Birthday Cake
        ColoringPage(
            id = "birthday_cake",
            title = "Celebration Cake",
            category = "Festivals",
            emoji = "🎂",
            regions = listOf(
                boxRegion("cake_bottom", "Bottom Tier", 0.20f, 0.62f, 0.80f, 0.86f),
                boxRegion("cake_top", "Top Tier", 0.30f, 0.40f, 0.70f, 0.62f),
                boxRegion("cake_candle", "Birthday Candle", 0.47f, 0.24f, 0.53f, 0.40f),
                ovalRegion("cake_flame", "Golden Flame", 0.50f, 0.18f, 0.05f, 0.06f),
                boxRegion("cake_frosting", "Cream Frosting", 0.20f, 0.60f, 0.80f, 0.66f)
            )
        ),

        // 30. Everyday Life - Big Flying Kite
        ColoringPage(
            id = "kite",
            title = "Sky Dancing Kite",
            category = "Everyday Life",
            emoji = "🪁",
            regions = listOf(
                listOf(0.50f to 0.15f, 0.78f to 0.50f, 0.50f to 0.75f, 0.50f to 0.50f).let {
                    ColoringRegion("kite_quad_r", "Right Diamond", it, 0.62f, 0.45f)
                },
                listOf(0.50f to 0.15f, 0.22f to 0.50f, 0.50f to 0.75f, 0.50f to 0.50f).let {
                    ColoringRegion("kite_quad_l", "Left Diamond", it, 0.38f, 0.45f)
                },
                listOf(0.50f to 0.75f, 0.44f to 0.84f, 0.56f to 0.84f).let {
                    ColoringRegion("kite_tail_fin", "Tail Fin", it, 0.50f, 0.80f)
                },
                ovalRegion("kite_ribbon1", "Ribbon Bow", 0.52f, 0.88f, 0.05f, 0.03f),
                ovalRegion("kite_ribbon2", "Ribbon Bow", 0.48f, 0.94f, 0.05f, 0.03f)
            )
        )
    )
}
