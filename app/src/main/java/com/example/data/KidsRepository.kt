package com.example.data

import androidx.compose.ui.graphics.Color
import com.example.model.AlphabetItem
import com.example.model.AnimalItem
import com.example.model.Badge
import com.example.model.BirdInsectItem
import com.example.model.ColorItem
import com.example.model.FruitVegItem
import com.example.model.NumberItem
import com.example.model.QuizQuestion
import com.example.model.RhymeItem
import com.example.model.ShapeItem
import com.example.model.WordItem

object KidsRepository {

    val alphabets: List<AlphabetItem> = listOf(
        AlphabetItem('A', "Apple", "सेब (Seb)", "seb", "ah as in apple", "🍎", "An apple a day keeps the doctor away!", "Apples can float in water because they are 25% air!"),
        AlphabetItem('B', "Ball", "गेंद (Gend)", "gend", "buh as in ball", "⚽", "Let's kick and roll the colorful ball!", "Balls have been used for fun games for thousands of years!"),
        AlphabetItem('C', "Cat", "बिल्ली (Billi)", "billi", "kuh as in cat", "🐱", "The friendly cat purrs softly in the sun.", "Cats can jump up to 6 times their height!"),
        AlphabetItem('D', "Dog", "कुत्ता (Kutta)", "kutta", "duh as in dog", "🐶", "The playful dog wags its tail happily.", "Dogs have an amazing sense of smell!"),
        AlphabetItem('E', "Elephant", "हाथी (Haathi)", "haathi", "eh as in elephant", "🐘", "The gentle elephant has large flapping ears.", "Elephants are the largest living land animals!"),
        AlphabetItem('F', "Fish", "मछली (Machhli)", "machhli", "fuh as in fish", "🐟", "The shiny fish swims swiftly in the pond.", "Fish breathe underwater using their gills!"),
        AlphabetItem('G', "Grapes", "अंगूर (Angoor)", "angoor", "guh as in grapes", "🍇", "Sweet purple grapes grow in juicy bunches.", "Grapes turn into sweet raisins when dried in the sun!"),
        AlphabetItem('H', "House", "घर (Ghar)", "ghar", "huh as in house", "🏠", "A cozy house is full of warmth and love.", "Houses all over the world come in many shapes and colors!"),
        AlphabetItem('I', "Ice Cream", "आइसक्रीम (Ice Cream)", "ice cream", "eye as in ice", "🍦", "Cold and sweet ice cream is tasty on sunny days.", "The most popular ice cream flavor in the world is vanilla!"),
        AlphabetItem('J', "Juice", "रस (Ras)", "ras", "juh as in juice", "🧃", "Fresh orange juice gives lots of energy.", "Fresh fruit juice is packed with healthy vitamins!"),
        AlphabetItem('K', "Kite", "पतंग (Patang)", "patang", "kuh as in kite", "🪁", "The bright kite dances high in the sky.", "Kites were invented over 2,000 years ago!"),
        AlphabetItem('L', "Lion", "शेर (Sher)", "sher", "luh as in lion", "🦁", "The brave lion has a magnificent golden mane.", "A lion's loud roar can be heard 5 miles away!"),
        AlphabetItem('M', "Mango", "आम (Aam)", "aam", "muh as in mango", "🥭", "The sweet mango is known as the king of fruits.", "Mangoes are the national fruit of India!"),
        AlphabetItem('N', "Nest", "घोंसला (Ghosla)", "ghosla", "nuh as in nest", "🪺", "Mother bird builds a warm nest for her eggs.", "Birds weave twigs and soft leaves to build nests!"),
        AlphabetItem('O', "Orange", "संतरा (Santara)", "santara", "aw as in orange", "🍊", "Juicy oranges are bright, round, and citrusy.", "Oranges are loaded with healthy Vitamin C!"),
        AlphabetItem('P', "Penguin", "पेंगुइन (Penguin)", "penguin", "puh as in penguin", "🐧", "The cute penguin waddles and slides on ice.", "Penguins cannot fly in the air, but they fly underwater!"),
        AlphabetItem('Q', "Queen", "रानी (Rani)", "rani", "kwuh as in queen", "👑", "The noble queen wears a sparkling golden crown.", "Queens live in grand castles with beautiful gardens!"),
        AlphabetItem('R', "Rabbit", "खरगोश (Khargosh)", "khargosh", "ruh as in rabbit", "🐰", "The fluffy rabbit loves crunchy orange carrots.", "Rabbits have super long ears to hear tiny sounds!"),
        AlphabetItem('S', "Sun", "सूरज (Suraj)", "suraj", "suh as in sun", "☀️", "The golden sun shines bright and warms the earth.", "The Sun is actually a giant glowing star!"),
        AlphabetItem('T', "Tiger", "बाघ (Bagh)", "bagh", "tuh as in tiger", "🐯", "The mighty tiger has bold orange and black stripes.", "Every tiger has a unique pattern of stripes!"),
        AlphabetItem('U', "Umbrella", "छाता (Chhaata)", "chhaata", "uh as in umbrella", "☂️", "Open your umbrella when the raindrops fall.", "Umbrellas protect us from both rain and hot sun!"),
        AlphabetItem('V', "Van", "गाड़ी (Gaadi)", "gaadi", "vuh as in van", "🚐", "The yellow school van takes children to school.", "Vans have lots of room for friends and family trips!"),
        AlphabetItem('W', "Watermelon", "तरबूज (Tarbooj)", "tarbooj", "wuh as in watermelon", "🍉", "Crisp red watermelon is refreshing in summer.", "Watermelons are 92% pure water!"),
        AlphabetItem('X', "Xylophone", "जाइलोफोन (Xylophone)", "xylophone", "zuh as in xylophone", "🎵", "Tap the colorful bars to play happy music.", "Xylophone means 'sound of wood' in ancient Greek!"),
        AlphabetItem('Y', "Yo-yo", "यो-यो (Yo-yo)", "yo-yo", "yuh as in yo-yo", "🪀", "The round yo-yo spins down and rolls back up.", "Yo-yo is one of the oldest toys in history!"),
        AlphabetItem('Z', "Zebra", "ज़ेब्रा (Zebra)", "zebra", "zuh as in zebra", "🦓", "The zebra runs fast across the grassy plains.", "No two zebras have the exact same stripes!")
    )

    // Complete 1 to 100 numbers generator
    val numbers: List<NumberItem> by lazy {
        val ones = listOf("", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
            "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen")
        val tens = listOf("", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety")

        val hindiNames = mapOf(
            1 to ("एक" to "Ek"), 2 to ("दो" to "Do"), 3 to ("तीन" to "Teen"), 4 to ("चार" to "Chaar"),
            5 to ("पाँच" to "Paanch"), 6 to ("छह" to "Chhah"), 7 to ("सात" to "Saat"), 8 to ("आठ" to "Aath"),
            9 to ("नौ" to "Nau"), 10 to ("दस" to "Das"), 11 to ("ग्यारह" to "Gyaarah"), 12 to ("बारह" to "Baarah"),
            13 to ("तेरह" to "Terah"), 14 to ("चौदह" to "Chaudah"), 15 to ("पंद्रह" to "Pandrah"), 16 to ("सोलह" to "Solah"),
            17 to ("सत्रह" to "Satrah"), 18 to ("अठारह" to "Athaarah"), 19 to ("उन्नीस" to "Unnees"), 20 to ("बीस" to "Bees"),
            25 to ("पच्चीस" to "Pachchees"), 30 to ("तीस" to "Tees"), 40 to ("चालीस" to "Chaalees"),
            50 to ("पचास" to "Pachaas"), 60 to ("साठ" to "Saath"), 70 to ("सत्तर" to "Sattar"),
            80 to ("अस्सी" to "Assee"), 90 to ("नब्बे" to "Nabbe"), 100 to ("एक सौ" to "Ek Sau")
        )

        val emojis = listOf("⭐", "🍎", "🎈", "🚗", "🌸", "🧸", "🍪", "🍬", "🍭", "🐥")

        (1..100).map { i ->
            val englishName = when {
                i < 20 -> ones[i]
                i % 10 == 0 && i < 100 -> tens[i / 10]
                i < 100 -> "${tens[i / 10]}-${ones[i % 10].lowercase()}"
                else -> "One hundred"
            }
            val (hWord, hPron) = hindiNames[i] ?: ("$i" to "Number $i")
            val objEmoji = emojis[(i - 1) % emojis.size]
            NumberItem(
                value = i,
                englishWord = englishName,
                hindiWord = hWord,
                hindiPronunciation = hPron,
                objectEmoji = objEmoji
            )
        }
    }

    // 40 Animals with categories
    val animals: List<AnimalItem> = listOf(
        // Wild
        AnimalItem("lion", "Lion", "शेर (Sher)", "Wild", "🦁", "Roar!", "Lions sleep up to 20 hours a day.", "Meat"),
        AnimalItem("tiger", "Tiger", "बाघ (Bagh)", "Wild", "🐯", "Growl!", "Tigers are great swimmers and enjoy water.", "Meat"),
        AnimalItem("elephant", "Elephant", "हाथी (Haathi)", "Wild", "🐘", "Trumpet!", "Elephants greet by wrapping trunks together.", "Leaves & Fruits"),
        AnimalItem("giraffe", "Giraffe", "जिराफ़ (Giraffe)", "Wild", "🦒", "Hum!", "Giraffes have blue-black tongues to eat thorny trees.", "Acacia leaves"),
        AnimalItem("zebra", "Zebra", "ज़ेब्रा (Zebra)", "Wild", "🦓", "Neigh!", "Zebras can sleep while standing up.", "Grass"),
        AnimalItem("bear", "Bear", "भालू (Bhaloo)", "Wild", "🐻", "Growl!", "Bears have thick fur coats to stay warm in snow.", "Fish & Berries"),
        AnimalItem("monkey", "Monkey", "बंदर (Bandar)", "Wild", "🐵", "Ooh Ooh Aah Aah!", "Monkeys use their tails for balance when swinging.", "Bananas & Nuts"),
        AnimalItem("kangaroo", "Kangaroo", "कंगारू (Kangaroo)", "Wild", "🦘", "Chortle!", "Baby kangaroos are called joeys and stay in mom's pouch.", "Grass & Shrubs"),
        AnimalItem("panda", "Panda", "पांडा (Panda)", "Wild", "🐼", "Bleat!", "Pandas spend almost 12 hours a day eating bamboo.", "Bamboo"),
        AnimalItem("fox", "Fox", "लोमड़ी (Lomdi)", "Wild", "🦊", "Yip!", "Foxes have bushy tails called brushes that keep them warm.", "Berries & Small animals"),
        AnimalItem("wolf", "Wolf", "भेड़िया (Bhediya)", "Wild", "🐺", "Howl!", "Wolves live and hunt together in friendly family packs.", "Meat"),
        AnimalItem("hippopotamus", "Hippo", "दरियाई घोड़ा (Dariyaee Ghoda)", "Wild", "🦛", "Grunt!", "Hippos spend all day soaking in rivers to stay cool.", "River Grass"),
        AnimalItem("rhinoceros", "Rhino", "गैंडा (Gainda)", "Wild", "🦏", "Snort!", "A rhino's horn is made of the same stuff as our hair!", "Grass & Leaves"),
        AnimalItem("cheetah", "Cheetah", "चीता (Cheetah)", "Wild", "🐆", "Purr & Chirp!", "Cheetah is the fastest running land animal on Earth!", "Meat"),
        AnimalItem("deer", "Deer", "हिरण (Hiran)", "Wild", "🦌", "Bellow!", "Male deer grow new antlers every year.", "Grass & Tender shoots"),

        // Farm
        AnimalItem("cow", "Cow", "गाय (Gaay)", "Farm", "🐮", "Moo!", "Cows give us nutritious milk to grow strong.", "Fresh green grass"),
        AnimalItem("horse", "Horse", "घोड़ा (Ghoda)", "Farm", "🐴", "Neigh!", "Horses can run within just hours after being born.", "Hay & Oats"),
        AnimalItem("sheep", "Sheep", "भेड़ (Bhed)", "Farm", "🐑", "Baa!", "Soft wool from sheep keeps us warm in cozy winter sweaters.", "Pasture grass"),
        AnimalItem("goat", "Goat", "बकरी (Bakri)", "Farm", "🐐", "Maa!", "Goats are great climbers and can balance on steep rocks.", "Leaves & Grains"),
        AnimalItem("pig", "Pig", "सुअर (Suar)", "Farm", "🐷", "Oink!", "Pigs are very smart and roll in mud to stay cool.", "Vegetables & Grains"),
        AnimalItem("chicken", "Chicken", "मुर्गी (Murgi)", "Farm", "🐔", "Cluck Cluck!", "Chickens communicate with over 30 different vocal sounds.", "Seeds & Corn"),
        AnimalItem("rooster", "Rooster", "मुर्गा (Murga)", "Farm", "🐓", "Cock-a-doodle-doo!", "Roosters crow early in the morning as the sun rises.", "Grains & Worms"),
        AnimalItem("donkey", "Donkey", "गधा (Gadha)", "Farm", "🫏", "Hee-Haw!", "Donkeys have large ears to hear friends from far away.", "Hay & Straw"),
        AnimalItem("duck", "Duck", "बत्तख (Battakh)", "Farm", "🦆", "Quack Quack!", "Ducks have waterproof feathers that never get soaked.", "Water plants & Seeds"),
        AnimalItem("turkey", "Turkey", "तुर्की (Turkey)", "Farm", "🦃", "Gobble Gobble!", "Turkeys can change color on their heads when excited.", "Berries & Seeds"),

        // Pets
        AnimalItem("dog", "Dog", "कुत्ता (Kutta)", "Pets", "🐶", "Woof Woof!", "Dogs are loyal companions known as human's best friends.", "Dog food & Treats"),
        AnimalItem("cat", "Cat", "बिल्ली (Billi)", "Pets", "🐱", "Meow!", "Cats spend about 70% of their life snoozing comfortably.", "Fish & Milk"),
        AnimalItem("rabbit", "Rabbit", "खरगोश (Khargosh)", "Pets", "🐰", "Thump!", "Rabbits twitch their cute noses to smell everything.", "Carrots & Hay"),
        AnimalItem("hamster", "Hamster", "हैम्स्टर (Hamster)", "Pets", "🐹", "Squeak!", "Hamsters have cheek pouches to carry snacks to bed.", "Sunflower seeds"),
        AnimalItem("parrot", "Parrot", "तोता (Tota)", "Pets", "🦜", "Squawk & Hello!", "Parrots can copy human words and sounds cheerfully.", "Fruits & Seeds"),
        AnimalItem("goldfish", "Goldfish", "गोल्डफिश (Goldfish)", "Pets", "🐠", "Blub Blub!", "Goldfish can see more colors than humans can!", "Fish flakes"),
        AnimalItem("turtle", "Turtle", "कछुआ (Kachhua)", "Pets", "🐢", "Hiss!", "Turtles carry a hard protective shell on their back.", "Leafy greens"),

        // Sea
        AnimalItem("dolphin", "Dolphin", "डॉल्फिन (Dolphin)", "Sea", "🐬", "Click Click & Whistle!", "Dolphins are super friendly and leap high out of waves.", "Small fish"),
        AnimalItem("whale", "Whale", "व्हेल (Whale)", "Sea", "🐋", "Singing calls!", "The Blue Whale is the largest animal ever known to live.", "Krill & Plankton"),
        AnimalItem("shark", "Shark", "शार्क (Shark)", "Sea", "🦈", "Splash!", "Sharks have multiple rows of teeth that regrow when lost.", "Fish"),
        AnimalItem("octopus", "Octopus", "ऑक्टोपस (Octopus)", "Sea", "🐙", "Swish!", "An octopus has 8 flexible arms and 3 hearts!", "Crabs & Shellfish"),
        AnimalItem("crab", "Crab", "केकड़ा (Kekda)", "Sea", "🦀", "Click Clack!", "Crabs walk sideways with their funny pinching claws.", "Algae & Seaweed"),
        AnimalItem("seahorse", "Seahorse", "समुद्री घोड़ा (Samudri Ghoda)", "Sea", "🫧", "Gentle flutter!", "Father seahorses carry eggs in their pouch until babies hatch.", "Tiny shrimp"),
        AnimalItem("seal", "Seal", "सील (Seal)", "Sea", "🦭", "Bark Bark!", "Seals glide smoothly through cold icy waters.", "Fish & Squid"),
        AnimalItem("jellyfish", "Jellyfish", "जेलीफ़िश (Jellyfish)", "Sea", "🪼", "Gentle glow!", "Jellyfish have no brain, heart, or bones, yet swim gracefully.", "Plankton")
    )

    // 16 Colors
    val colors: List<ColorItem> = listOf(
        ColorItem("Red", "लाल (Laal)", Color(0xFFFF3B30), "#FF3B30", listOf("Apple", "Strawberry", "Fire Engine", "Heart"), "🍎"),
        ColorItem("Blue", "नीला (Neela)", Color(0xFF007AFF), "#007AFF", listOf("Sky", "Ocean", "Blueberries", "Jeans"), "🌊"),
        ColorItem("Green", "हरा (Hara)", Color(0xFF34C759), "#34C759", listOf("Leaf", "Grass", "Frog", "Broccoli"), "🌿"),
        ColorItem("Yellow", "पीला (Peela)", Color(0xFFFFCC00), "#FFCC00", listOf("Sun", "Banana", "Sunflower", "School Bus"), "☀️"),
        ColorItem("Orange", "नारंगी (Naarangi)", Color(0xFFFF9500), "#FF9500", listOf("Orange Fruit", "Carrot", "Basketball", "Pumpkin"), "🍊"),
        ColorItem("Pink", "गुलाबी (Gulaabi)", Color(0xFFFF2D55), "#FF2D55", listOf("Flamingo", "Cotton Candy", "Rose", "Piglet"), "🌸"),
        ColorItem("Purple", "बैंगनी (Baingani)", Color(0xFF5856D6), "#5856D6", listOf("Grapes", "Eggplant", "Lavender", "Amethyst"), "🍇"),
        ColorItem("Brown", "भूरा (Bhoora)", Color(0xFF8B572A), "#8B572A", listOf("Chocolate", "Tree Bark", "Teddy Bear", "Soil"), "🍫"),
        ColorItem("Black", "काला (Kaala)", Color(0xFF1C1C1E), "#1C1C1E", listOf("Night Sky", "Coal", "Crow", "Tire"), "🐈‍⬛"),
        ColorItem("White", "सफ़ेद (Safed)", Color(0xFFF2F2F7), "#F2F2F7", listOf("Snow", "Milk", "Cloud", "Swan"), "🥛"),
        ColorItem("Gray", "स्लेटी (Sleti)", Color(0xFF8E8E93), "#8E8E93", listOf("Elephant", "Pebble", "Rain Cloud", "Dolphin"), "🐘"),
        ColorItem("Cyan", "फ़िरोज़ी (Firozi)", Color(0xFF32ADE6), "#32ADE6", listOf("Tropical Sea", "Iceberg", "Swimming Pool"), "💎"),
        ColorItem("Magenta", "गहरा गुलाबी (Gahra Gulaabi)", Color(0xFFAF52DE), "#AF52DE", listOf("Orchid Flower", "Dragon Fruit", "Neon Light"), "🌺"),
        ColorItem("Gold", "सुनहरा (Sunahra)", Color(0xFFFFD700), "#FFD700", listOf("Crown", "Medal", "Treasure", "Star"), "👑"),
        ColorItem("Silver", "चाँदी जैसा (Chaandi Jaisa)", Color(0xFFC0C0C0), "#C0C0C0", listOf("Spoon", "Coin", "Robot", "Spaceship"), "🥈"),
        ColorItem("Beige", "हल्का बादामी (Halka Badami)", Color(0xFFF5F5DC), "#F5F5DC", listOf("Sand", "Almond", "Cookie", "Seashell"), "🏖️")
    )

    // 160+ Vocabulary Words across 10 categories
    val vocabulary: List<WordItem> = listOf(
        // Body Parts (16)
        WordItem("Eyes", "Body Parts", "आँखें (Aankhein)", "👀", "We see the world with our eyes."),
        WordItem("Ears", "Body Parts", "कान (Kaan)", "👂", "We hear music with our ears."),
        WordItem("Nose", "Body Parts", "नाक (Naak)", "👃", "We smell flowers with our nose."),
        WordItem("Mouth", "Body Parts", "मुँह (Munh)", "👄", "We speak and smile with our mouth."),
        WordItem("Teeth", "Body Parts", "दाँत (Daant)", "🦷", "Brush your teeth twice every day."),
        WordItem("Hands", "Body Parts", "हाथ (Haath)", "👋", "Clap your hands with joy!"),
        WordItem("Fingers", "Body Parts", "उंगलियाँ (Ungliyaan)", "🖐️", "We have five fingers on each hand."),
        WordItem("Feet", "Body Parts", "पैर (Pair)", "🦶", "We walk and run with our feet."),
        WordItem("Head", "Body Parts", "सिर (Sir)", "🧒", "Nod your head yes!"),
        WordItem("Hair", "Body Parts", "बाल (Baal)", "💇", "Comb your hair neatly."),
        WordItem("Arms", "Body Parts", "बाँहें (Baahein)", "💪", "Stretch your arms up high!"),
        WordItem("Legs", "Body Parts", "टाँगें (Taangein)", "🦵", "Hop on your strong legs."),
        WordItem("Knees", "Body Parts", "घुटने (Ghutne)", "🧎", "Bend your knees to jump."),
        WordItem("Tummy", "Body Parts", "पेट (Pet)", "🤰", "Healthy food keeps your tummy happy."),
        WordItem("Shoulders", "Body Parts", "कंधे (Kandhe)", "🤷", "Roll your shoulders gently."),
        WordItem("Heart", "Body Parts", "दिल (Dil)", "❤️", "Your heart beats beat-beat-beat!"),

        // Family Members (12)
        WordItem("Mother", "Family", "माँ (Maa)", "👩", "Mother gives warm hugs."),
        WordItem("Father", "Family", "पिता (Pita)", "👨", "Father plays games with us."),
        WordItem("Sister", "Family", "बहन (Behan)", "👧", "My sister shares her toys."),
        WordItem("Brother", "Family", "भाई (Bhai)", "👦", "My brother helps me build blocks."),
        WordItem("Grandmother", "Family", "दादी/नानी (Daadi/Naani)", "👵", "Grandmother tells wonderful bedtime stories."),
        WordItem("Grandfather", "Family", "दादा/नाना (Daada/Naana)", "👴", "Grandfather walks with me in the park."),
        WordItem("Baby", "Family", "छोटा बच्चा (Chhota Bachha)", "👶", "The baby giggles sweetly."),
        WordItem("Uncle", "Family", "चाचा/मामा (Chacha/Mama)", "👨‍🦰", "Uncle brought a puzzle box."),
        WordItem("Aunt", "Family", "चाची/मौसी (Chachi/Mausi)", "👩‍🦰", "Aunt baked delicious cupcakes."),
        WordItem("Cousin", "Family", "चचेरा भाई/बहन (Cousin)", "🧑", "Playing with cousins is so fun!"),
        WordItem("Friend", "Family", "दोस्त (Dost)", "👫", "Best friends care and share."),
        WordItem("Pet", "Family", "पालतू जानवर (Paaltu Jaanwar)", "🐕", "Our pet puppy wags its tail."),

        // School Objects (16)
        WordItem("Book", "School", "किताब (Kitaab)", "📖", "Open the book to read a story."),
        WordItem("Pencil", "School", "पेंसिल (Pencil)", "✏️", "Write and draw with a sharp pencil."),
        WordItem("Eraser", "School", "रबर (Rabar)", "🧼", "Erase pencil marks cleanly."),
        WordItem("Crayon", "School", "क्रेयॉन (Crayon)", "🖍️", "Color the picture with bright crayons."),
        WordItem("Ruler", "School", "पैमाना (Paimaana)", "📏", "Draw straight lines with a ruler."),
        WordItem("Backpack", "School", "बस्ता (Basta)", "🎒", "Pack books neatly in the backpack."),
        WordItem("Scissors", "School", "कैंची (Kainchi)", "✂️", "Cut colorful craft paper carefully."),
        WordItem("Glue", "School", "गोंद (Gond)", "🧴", "Stick pictures using paper glue."),
        WordItem("Desk", "School", "मेज़ (Mez)", "🪑", "Sit at the study desk quietly."),
        WordItem("Chair", "School", "कुर्सी (Kursi)", "🪑", "Pull up a chair to sit."),
        WordItem("Blackboard", "School", "ब्लैकबोर्ड (Blackboard)", "📋", "Teacher writes letters on the board."),
        WordItem("Chalk", "School", "चाक (Chalk)", "🖍️", "White chalk writes clearly."),
        WordItem("Clock", "School", "घड़ी (Ghadi)", "⏰", "The clock tells us the time."),
        WordItem("Bell", "School", "घंटी (Ghanti)", "🔔", "The school bell rings ding-dong!"),
        WordItem("Map", "School", "नक्शा (Naksha)", "🗺️", "Find mountains and seas on the map."),
        WordItem("Lunchbox", "School", "टिफिन (Tiffin)", "🍱", "Eat fresh fruits from your lunchbox."),

        // Clothes (14)
        WordItem("Shirt", "Clothes", "कमीज़ (Kameez)", "👕", "Put on a clean shirt."),
        WordItem("Pants", "Clothes", "पतलून (Pataloon)", "👖", "Blue pants are comfortable."),
        WordItem("Dress", "Clothes", "फ़्रॉक (Frock)", "👗", "A pretty swirling pink dress."),
        WordItem("Shoes", "Clothes", "जूते (Joote)", "👟", "Tie your shoe laces before running."),
        WordItem("Socks", "Clothes", "मोज़े (Moze)", "🧦", "Soft cotton socks warm your toes."),
        WordItem("Hat", "Clothes", "टोपी (Topi)", "🧢", "Wear a cap when sun is bright."),
        WordItem("Jacket", "Clothes", "जैकेट (Jacket)", "🧥", "A warm jacket for windy days."),
        WordItem("Gloves", "Clothes", "दस्ताने (Dastaane)", "🧤", "Wear warm mittens in winter."),
        WordItem("Scarf", "Clothes", "स्कार्फ़ (Scarf)", "🧣", "Wrap a cozy scarf around your neck."),
        WordItem("Raincoat", "Clothes", "रेनकोट (Raincoat)", "🧥", "Splash in rain puddles with a raincoat!"),
        WordItem("Boots", "Clothes", "बूट (Boot)", "🥾", "Stomp in puddles with rubber boots."),
        WordItem("Pyjamas", "Clothes", "पायजामा (Pyjama)", "🩳", "Slip into cozy pyjamas for sleep."),
        WordItem("Belt", "Clothes", "पेटी (Peti)", "🥋", "Fasten your belt comfortably."),
        WordItem("Glasses", "Clothes", "चश्मा (Chashma)", "👓", "Glasses help us see clearly."),

        // Food & Snacks (16)
        WordItem("Bread", "Food", "ब्रेड/रोटी (Bread/Roti)", "🍞", "Warm toasted bread with butter."),
        WordItem("Milk", "Food", "दूध (Doodh)", "🥛", "Drink warm milk for strong bones."),
        WordItem("Egg", "Food", "अंडा (Anda)", "🥚", "Eggs give healthy morning protein."),
        WordItem("Cheese", "Food", "पनीर/चीज़ (Paneer/Cheese)", "🧀", "Yummy yellow cheese cube."),
        WordItem("Rice", "Food", "चावल (Chaawal)", "🍚", "Fluffy white steamed rice."),
        WordItem("Soup", "Food", "सूप (Soup)", "🥣", "Hot vegetable soup on chilly days."),
        WordItem("Water", "Food", "पानी (Paani)", "💧", "Drink plenty of clean water."),
        WordItem("Honey", "Food", "शहद (Shahad)", "🍯", "Sweet golden honey from busy bees."),
        WordItem("Cake", "Food", "केक (Cake)", "🎂", "Blow the candles on birthday cake!"),
        WordItem("Cookie", "Food", "बिस्कुट (Biscuit)", "🍪", "Crunchy chocolate chip cookie."),
        WordItem("Salad", "Food", "सलाद (Salaad)", "🥗", "Crispy cucumber and tomato salad."),
        WordItem("Pizza", "Food", "पिज़्ज़ा (Pizza)", "🍕", "Slice of cheesy warm pizza."),
        WordItem("Sandwich", "Food", "सैंडविच (Sandwich)", "🥪", "Fresh vegetable sandwich."),
        WordItem("Pancake", "Food", "पैनकेक (Pancake)", "🥞", "Fluffy pancakes topped with syrup."),
        WordItem("Yogurt", "Food", "दही (Dahi)", "🥛", "Cool creamy sweet yogurt."),
        WordItem("Noodles", "Food", "नूडल्स (Noodles)", "🍜", "Slurp tasty vegetable noodles."),

        // Nature & Weather (16)
        WordItem("Sun", "Nature", "सूरज (Suraj)", "☀️", "The sun gives light and warmth."),
        WordItem("Moon", "Nature", "चाँद (Chaand)", "🌙", "The crescent moon glows in the night."),
        WordItem("Star", "Nature", "तारा (Taara)", "⭐", "Twinkle, twinkle, little star!"),
        WordItem("Cloud", "Nature", "बादल (Baadal)", "☁️", "Fluffy white clouds float in the blue sky."),
        WordItem("Rain", "Nature", "बारिश (Baarish)", "🌧️", "Raindrops water the flowers."),
        WordItem("Rainbow", "Nature", "इंद्रधनुष (Indradhanush)", "🌈", "Seven beautiful colors arch in the sky."),
        WordItem("Wind", "Nature", "हवा (Hawa)", "💨", "Gentle breeze sways the tall trees."),
        WordItem("Snow", "Nature", "बर्फ़ (Barf)", "❄️", "Let's build a smiling snowman!"),
        WordItem("Tree", "Nature", "पेड़ (Ped)", "🌳", "Trees give cool shade and clean air."),
        WordItem("Flower", "Nature", "फूल (Phool)", "🌸", "A blossom opens with sweet fragrance."),
        WordItem("Leaf", "Nature", "पत्ता (Patta)", "🍃", "Green leaves catch sunlight."),
        WordItem("River", "Nature", "नदी (Nadi)", "🌊", "The winding river flows toward the sea."),
        WordItem("Mountain", "Nature", "पहाड़ (Pahaad)", "⛰️", "The giant mountain peaks reach the clouds."),
        WordItem("Ocean", "Nature", "समुद्र (Samudra)", "🌊", "Waves lap against the sandy shore."),
        WordItem("Garden", "Nature", "बगीचा (Bageecha)", "🪴", "Butterflies dance in the garden."),
        WordItem("Fire", "Nature", "आग (Aag)", "🔥", "A campfire keeps us warm camping."),

        // Household Objects (16)
        WordItem("Bed", "Household", "बिस्तर (Bistar)", "🛏️", "Sleep tight in your cozy bed."),
        WordItem("Pillow", "Household", "तकिया (Takiya)", "🪶", "Rest your sleepy head on the soft pillow."),
        WordItem("Blanket", "Household", "कंबल (Kambal)", "🛋️", "Pull up the warm blanket."),
        WordItem("Door", "Household", "दरवाज़ा (Darwaaza)", "🚪", "Knock gently on the wooden door."),
        WordItem("Window", "Household", "खिड़की (Khidki)", "🪟", "Look out the window at the birds."),
        WordItem("Lamp", "Household", "दीपक/बत्ती (Batti)", "💡", "Switch on the reading lamp."),
        WordItem("Table", "Household", "मेज़ (Mez)", "🪵", "Family gathers around the dinner table."),
        WordItem("Cup", "Household", "कप (Cup)", "☕", "Sip warm cocoa from your mug."),
        WordItem("Plate", "Household", "थाली (Thaali)", "🍽️", "Arrange delicious food on the plate."),
        WordItem("Spoon", "Household", "चम्मच (Chammach)", "🥄", "Eat soup neatly with a spoon."),
        WordItem("Fork", "Household", "काँटा (Kaanta)", "🍴", "Pick up fruits with a fork."),
        WordItem("Clock", "Household", "घड़ी (Ghadi)", "🕰️", "Tick-tock goes the grandfather clock."),
        WordItem("Mirror", "Household", "आईना (Aaina)", "🪞", "Smile at your reflection in the mirror."),
        WordItem("Towel", "Household", "तौलिया (Tauliya)", "🧖", "Dry your hands with a fluffy towel."),
        WordItem("Soap", "Household", "साबुन (Saabun)", "🧼", "Wash hands with bubbly soap."),
        WordItem("Broom", "Household", "झाड़ू (Jhaadoo)", "🧹", "Keep our room tidy and clean."),

        // Actions / Verbs (16)
        WordItem("Run", "Actions", "दौड़ना (Daudna)", "🏃", "Run fast across the green field!"),
        WordItem("Jump", "Actions", "कूदना (Koodna)", "🦘", "Jump high up like a bouncy kangaroo!"),
        WordItem("Walk", "Actions", "चलना (Chalna)", "🚶", "Walk side-by-side with your parents."),
        WordItem("Dance", "Actions", "नाचना (Naachna)", "💃", "Twirl and dance to happy music!"),
        WordItem("Sing", "Actions", "गाना (Gaana)", "🎤", "Sing joyful nursery rhymes."),
        WordItem("Read", "Actions", "पढ़ना (Padhna)", "📚", "Read exciting bedtime stories."),
        WordItem("Write", "Actions", "लिखना (Likhna)", "✍️", "Write your name neatly."),
        WordItem("Draw", "Actions", "चित्र बनाना (Chitra)", "🎨", "Paint colorful flowers and houses."),
        WordItem("Sleep", "Actions", "सोना (Sona)", "😴", "Rest well to wake up energized."),
        WordItem("Smile", "Actions", "मुस्कुराना (Muskurana)", "😊", "A happy smile brightens the day."),
        WordItem("Laugh", "Actions", "हँसना (Hansna)", "😄", "Laughter is the best feeling!"),
        WordItem("Eat", "Actions", "खाना (Khaana)", "🍽️", "Chew your food slowly and happily."),
        WordItem("Drink", "Actions", "पीना (Peena)", "🥤", "Drink cool water when thirsty."),
        WordItem("Clap", "Actions", "ताली बजाना (Taali)", "👏", "Clap for your wonderful work!"),
        WordItem("Swim", "Actions", "तैरना (Tairna)", "🏊", "Paddle and swim like a little fish."),
        WordItem("Play", "Actions", "खेलना (Khelna)", "🪀", "Playing with friends brings big smiles!"),

        // Feelings & Emotions (14)
        WordItem("Happy", "Feelings", "खुश (Khush)", "😃", "Today is a joyful, happy day!"),
        WordItem("Sad", "Feelings", "उदास (Udaas)", "😢", "A warm hug makes sad moments better."),
        WordItem("Excited", "Feelings", "उत्साहित (Utsaahit)", "🤩", "Excited for a fun holiday trip!"),
        WordItem("Sleepy", "Feelings", "नींद में (Neend Mein)", "🥱", "Yawning when bedtime arrives."),
        WordItem("Calm", "Feelings", "शांत (Shaant)", "😌", "Taking deep breaths makes us calm."),
        WordItem("Surprised", "Feelings", "हैरान (Hairaan)", "😲", "Surprised by a sweet birthday present!"),
        WordItem("Proud", "Feelings", "गर्व (Garv)", "🎖️", "Proud of learning all the letters!"),
        WordItem("Brave", "Feelings", "बहादुर (Bahaadur)", "🦁", "Be brave like a little hero."),
        WordItem("Curious", "Feelings", "जिज्ञासु (Jigyaasu)", "🧐", "Curious to discover how stars shine!"),
        WordItem("Kind", "Feelings", "दयालु (Dayaalu)", "🤝", "Always be kind and helpful."),
        WordItem("Loving", "Feelings", "प्यारा (Pyaara)", "🥰", "Family gives unconditional love."),
        WordItem("Energetic", "Feelings", "ऊर्जावान (Oorjaavaan)", "⚡", "Ready to hop and run around!"),
        WordItem("Gentle", "Feelings", "कोमल (Komal)", "🕊️", "Pet the kitten with gentle hands."),
        WordItem("Thankful", "Feelings", "आभारी (Aabhaari)", "🙏", "Say thank you for nice deeds."),

        // Opposites (16)
        WordItem("Big & Small", "Opposites", "बड़ा और छोटा (Bada aur Chhota)", "🐘 🐁", "An elephant is big; a mouse is small."),
        WordItem("Hot & Cold", "Opposites", "गरम और ठंडा (Garam aur Thanda)", "🔥 🧊", "Fire is hot; ice is cold."),
        WordItem("Fast & Slow", "Opposites", "तेज़ और धीमा (Tez aur Dheema)", "🐆 🐢", "Cheetah is fast; turtle is slow."),
        WordItem("Up & Down", "Opposites", "ऊपर और नीचे (Oopar aur Neeche)", "⬆️ ⬇️", "Look up at stars, look down at grass."),
        WordItem("Day & Night", "Opposites", "दिन और रात (Din aur Raat)", "☀️ 🌙", "Sun shines in day, stars shine at night."),
        WordItem("Happy & Sad", "Opposites", "खुश और उदास (Khush aur Udaas)", "😊 😢", "A smile is happy, tears when sad."),
        WordItem("Open & Closed", "Opposites", "खुला और बंद (Khula aur Band)", "📖 📕", "Open the book; close it after reading."),
        WordItem("Heavy & Light", "Opposites", "भारी और हल्का (Bhaari aur Halka)", "🪨 🪶", "A stone is heavy; a feather is light."),
        WordItem("Clean & Dirty", "Opposites", "साफ़ और गंदा (Saaf aur Ganda)", "🧼 🫧", "Soap makes dirty hands clean."),
        WordItem("Hard & Soft", "Opposites", "कठोर और नरम (Kathor aur Naram)", "🧱 🧸", "Bricks are hard; teddy bears are soft."),
        WordItem("Long & Short", "Opposites", "लंबा और छोटा (Lamba aur Chhota)", "📏 ✏️", "A ruler is long; a small pencil is short."),
        WordItem("In & Out", "Opposites", "अंदर और बाहर (Andar aur Baahar)", "📥 📤", "Put toys in the toy box; take them out to play."),
        WordItem("Full & Empty", "Opposites", "भरा और खाली (Bhara aur Khaali)", "🥛 🫗", "The glass is full of milk; now it's empty."),
        WordItem("Loud & Quiet", "Opposites", "ज़ोर से और शांत (Zor se aur Shaant)", "📢 🤫", "Drums are loud; a whisper is quiet."),
        WordItem("Wet & Dry", "Opposites", "गीला और सूखा (Geela aur Sookha)", "🌧️ 🏖️", "Rain makes us wet; a towel makes us dry."),
        WordItem("Near & Far", "Opposites", "पास और दूर (Paas aur Door)", "📍 🔭", "My shoes are near; distant stars are far.")
    )

    // 20 Fruits + 20 Vegetables
    val fruitsAndVeg: List<FruitVegItem> = listOf(
        // 20 Fruits
        FruitVegItem("Apple", true, "सेब (Seb)", "🍎", "Red", "Crunchy and sweet, keeps you healthy!"),
        FruitVegItem("Banana", true, "केला (Kela)", "🍌", "Yellow", "Easy to peel and gives instant energy!"),
        FruitVegItem("Mango", true, "आम (Aam)", "🥭", "Yellow-Orange", "Known as the sweetest king of fruits!"),
        FruitVegItem("Orange", true, "संतरा (Santara)", "🍊", "Orange", "Packed with juicy Vitamin C!"),
        FruitVegItem("Grapes", true, "अंगूर (Angoor)", "🍇", "Purple/Green", "Grows in sweet hanging clusters!"),
        FruitVegItem("Strawberry", true, "स्ट्रॉबेरी (Strawberry)", "🍓", "Red", "The only fruit with seeds on its outside!"),
        FruitVegItem("Watermelon", true, "तरबूज (Tarbooj)", "🍉", "Green & Red", "92% refreshing water on hot days!"),
        FruitVegItem("Pineapple", true, "अनानास (Ananaas)", "🍍", "Golden Yellow", "Wears a spiky crown like a royal fruit!"),
        FruitVegItem("Papaya", true, "पपीता (Papeeta)", "🍈", "Orange", "Soft, sweet, and great for digestion!"),
        FruitVegItem("Guava", true, "अमरूद (Amrood)", "🍐", "Green", "Crispy fruit with crunchy little seeds!"),
        FruitVegItem("Pomegranate", true, "अनार (Anaar)", "🍎", "Ruby Red", "Filled with hundreds of sparkling ruby pearls!"),
        FruitVegItem("Cherry", true, "चेरी (Cherry)", "🍒", "Bright Red", "Pairs of sweet red berries on stems!"),
        FruitVegItem("Peach", true, "आड़ू (Aadoo)", "🍑", "Soft Orange", "Velvety fuzzy skin and sweet juicy flesh!"),
        FruitVegItem("Pear", true, "नाशपाती (Naashpaati)", "🍐", "Pale Green", "Bell-shaped, crisp, and refreshing!"),
        FruitVegItem("Kiwi", true, "कीवी (Kiwi)", "🥝", "Brown & Bright Green", "Fuzzy outside, bright emerald green inside!"),
        FruitVegItem("Plum", true, "आलू बुखारा (Aaloo Bukhara)", "🫐", "Deep Purple", "Sweet and slightly tart stone fruit!"),
        FruitVegItem("Coconut", true, "नारियल (Naariyal)", "🥥", "Brown", "Has sweet refreshing water inside!"),
        FruitVegItem("Lemon", true, "नींबू (Neemboo)", "🍋", "Bright Yellow", "Sour citrus that makes tasty lemonade!"),
        FruitVegItem("Avocado", true, "एवोकाडो (Avocado)", "🥑", "Dark Green", "Creamy and rich in good healthy fats!"),
        FruitVegItem("Blueberry", true, "ब्लूबेरी (Blueberry)", "🫐", "Deep Blue", "Tiny antioxidant-packed power berry!"),

        // 20 Vegetables
        FruitVegItem("Carrot", false, "गाजर (Gaajar)", "🥕", "Orange", "Crunchy root that helps your eyesight!"),
        FruitVegItem("Potato", false, "आलू (Aaloo)", "🥔", "Brown", "The most loved vegetable for crispy treats!"),
        FruitVegItem("Tomato", false, "टमाटर (Tamaatar)", "🍅", "Red", "Juicy and bright, great for soup and salads!"),
        FruitVegItem("Broccoli", false, "ब्रोकली (Broccoli)", "🥦", "Green", "Looks like cute miniature green trees!"),
        FruitVegItem("Corn", false, "मक्का (Makka)", "🌽", "Yellow", "Sweet golden kernels on a cob!"),
        FruitVegItem("Peas", false, "मटर (Matar)", "🫛", "Green", "Sweet green pearls hidden inside cozy pods!"),
        FruitVegItem("Cucumber", false, "खीरा (Kheera)", "🥒", "Green", "Cool and crisp on hot sunny afternoons!"),
        FruitVegItem("Onion", false, "प्याज़ (Pyaaz)", "🧅", "Purple/White", "Has layers and adds flavor to dishes!"),
        FruitVegItem("Spinach", false, "पालक (Paalak)", "🥬", "Dark Green", "Leafy green full of iron for strength!"),
        FruitVegItem("Eggplant", false, "बैंगन (Baingan)", "🍆", "Glossy Purple", "Wears a green stem hat like a crown!"),
        FruitVegItem("Capsicum", false, "शिमला मिर्च (Shimla Mirch)", "🫑", "Green/Red/Yellow", "Crunchy sweet bell pepper!"),
        FruitVegItem("Cauliflower", false, "फूलगोभी (Phoolgobhi)", "🥦", "Creamy White", "Looks like a beautiful white flower bouquet!"),
        FruitVegItem("Cabbage", false, "पत्तागोभी (Pattagobhi)", "🥬", "Light Green", "Layers of tightly wrapped crispy leaves!"),
        FruitVegItem("Pumpkin", false, "कद्दू (Kaddu)", "🎃", "Bright Orange", "Giant round vegetable loved in autumn!"),
        FruitVegItem("Radish", false, "मूली (Moolee)", "🫚", "White & Pink", "Peppery crisp root with leafy tops!"),
        FruitVegItem("Beetroot", false, "चुकंदर (Chukandar)", "🍠", "Deep Crimson", "Sweet root that paints your tongue pink!"),
        FruitVegItem("Ladyfinger (Okra)", false, "भिंडी (Bhindi)", "🥒", "Green", "Long slender pods with tiny star shapes inside!"),
        FruitVegItem("Garlic", false, "लहसुन (Lahsun)", "🧄", "White", "Tiny cloves that smell great when cooking!"),
        FruitVegItem("Ginger", false, "अदरक (Adrak)", "🫚", "Light Brown", "A spicy root that keeps winter colds away!"),
        FruitVegItem("Mushroom", false, "मशरूम (Mushroom)", "🍄", "White & Tan", "Cute umbrella-shaped forest vegetable!")
    )

    // Shapes (10)
    val shapes: List<ShapeItem> = listOf(
        ShapeItem("Circle", "वृत्त (Vritt)", 0, "⭕", "A circle is perfectly round with no corners, like the Sun!"),
        ShapeItem("Square", "वर्ग (Varg)", 4, "⬛", "A square has 4 equal straight sides and 4 corners!"),
        ShapeItem("Triangle", "त्रिकोण (Trikon)", 3, "🔺", "A triangle has 3 sides and 3 pointy corners, like a pizza slice!"),
        ShapeItem("Rectangle", "आयत (Aayat)", 4, "▭", "A rectangle has 2 long sides and 2 shorter sides, like a door!"),
        ShapeItem("Oval", "अंडाकार (Andaakaar)", 0, "🥚", "An oval is an elongated circle, shaped like an egg!"),
        ShapeItem("Star", "तारा (Taara)", 10, "⭐", "A 5-pointed star shines brightly in the night sky!"),
        ShapeItem("Heart", "दिल (Dil)", 0, "❤️", "A heart shape represents love, care, and friendship!"),
        ShapeItem("Diamond", "हीरा (Heera)", 4, "💠", "A diamond or rhombus looks like a tilted square flying kite!"),
        ShapeItem("Pentagon", "पंचकोण (Panchkon)", 5, "⬠", "A pentagon has 5 straight sides, like a cozy little house!"),
        ShapeItem("Hexagon", "षट्कोण (Shatkon)", 6, "⬡", "A hexagon has 6 sides, just like bees build honeycomb cells!")
    )

    // Birds (10) & Insects (8)
    val birdsAndInsects: List<BirdInsectItem> = listOf(
        // 10 Birds
        BirdInsectItem("Sparrow", true, "गौरैया (Gauraiya)", "🐦", "Friendly little brown birds that chirp cheerfully on balconies."),
        BirdInsectItem("Parrot", true, "तोता (Tota)", "🦜", "Bright green feathers with a curved red beak, can repeat words!"),
        BirdInsectItem("Peacock", true, "मोर (Mor)", "🦚", "The national bird of India with spectacular dancing feathers!"),
        BirdInsectItem("Pigeon", true, "कबूतर (Kabootar)", "🕊️", "Gentle gray birds that bob their heads and coo peacefully."),
        BirdInsectItem("Eagle", true, "चील/गरुड़ (Garuda)", "🦅", "Soars high above clouds with super sharp vision."),
        BirdInsectItem("Owl", true, "उल्लू (Ullu)", "🦉", "Wise night bird that can turn its head almost all the way around!"),
        BirdInsectItem("Duck", true, "बत्तख (Battakh)", "🦆", "Swims on lakes with webbed feet and quacks happily."),
        BirdInsectItem("Hen", true, "मुर्गी (Murgi)", "🐔", "Clucks around the yard and lays morning eggs."),
        BirdInsectItem("Crow", true, "कौआ (Kauwa)", "🐦‍⬛", "Clever black bird that can solve tricky puzzles."),
        BirdInsectItem("Penguin", true, "पेंगुइन (Penguin)", "🐧", "Waddles on ice and swims like a rocket in cold oceans."),

        // 8 Insects
        BirdInsectItem("Butterfly", false, "तितली (Titli)", "🦋", "Starts as a caterpillar and transforms into colorful winged beauty!"),
        BirdInsectItem("Ant", false, "चींटी (Cheenti)", "🐜", "Tiny but super strong, can carry 20 times its own body weight!"),
        BirdInsectItem("Honey Bee", false, "मधुमक्खी (Madhumakkhi)", "🐝", "Bumbles from flower to flower and makes sweet honey!"),
        BirdInsectItem("Ladybug", false, "लेडीबग (Ladybug)", "🐞", "Cute red bug with shiny black polka dots on its shell."),
        BirdInsectItem("Dragonfly", false, "व्याध पतंग (Dragonfly)", "🪰", "Can fly backwards, upside down, and hover like a helicopter!"),
        BirdInsectItem("Grasshopper", false, "टिड्डा (Tidda)", "🦗", "Leaps high in the grass and makes music by rubbing its legs!"),
        BirdInsectItem("Beetle", false, "भृंग (Bhring)", "🪲", "Has a hard shiny shield that protects its delicate wings."),
        BirdInsectItem("Caterpillar", false, "इल्ली (Illi)", "🐛", "Munches green leaves happily before curling into a chrysalis.")
    )

    // 10 Classic Nursery Rhymes
    val nurseryRhymes: List<RhymeItem> = listOf(
        RhymeItem(
            "Twinkle Twinkle Little Star", "⭐", 45,
            listOf(
                "Twinkle, twinkle, little star,",
                "How I wonder what you are!",
                "Up above the world so high,",
                "Like a diamond in the sky.",
                "Twinkle, twinkle, little star,",
                "How I wonder what you are!"
            )
        ),
        RhymeItem(
            "Old MacDonald Had a Farm", "🚜", 50,
            listOf(
                "Old MacDonald had a farm, E-I-E-I-O!",
                "And on his farm he had a cow, E-I-E-I-O!",
                "With a moo-moo here and a moo-moo there,",
                "Here a moo, there a moo, everywhere a moo-moo!",
                "Old MacDonald had a farm, E-I-E-I-O!"
            )
        ),
        RhymeItem(
            "The Wheels on the Bus", "🚌", 50,
            listOf(
                "The wheels on the bus go round and round,",
                "Round and round, round and round.",
                "The wheels on the bus go round and round,",
                "All through the town!",
                "The wipers on the bus go swish, swish, swish,",
                "All through the town!"
            )
        ),
        RhymeItem(
            "Baa Baa Black Sheep", "🐑", 40,
            listOf(
                "Baa, baa, black sheep, have you any wool?",
                "Yes sir, yes sir, three bags full!",
                "One for the master, and one for the dame,",
                "And one for the little boy who lives down the lane!"
            )
        ),
        RhymeItem(
            "Itsy Bitsy Spider", "🕷️", 40,
            listOf(
                "The itsy bitsy spider climbed up the water spout.",
                "Down came the rain and washed the spider out.",
                "Out came the sun and dried up all the rain,",
                "And the itsy bitsy spider climbed up the spout again!"
            )
        ),
        RhymeItem(
            "Humpty Dumpty", "🥚", 35,
            listOf(
                "Humpty Dumpty sat on a wall,",
                "Humpty Dumpty had a great fall.",
                "All the king's horses and all the king's men,",
                "Couldn't put Humpty together again!"
            )
        ),
        RhymeItem(
            "Row Row Row Your Boat", "🛶", 35,
            listOf(
                "Row, row, row your boat,",
                "Gently down the stream.",
                "Merrily, merrily, merrily, merrily,",
                "Life is but a dream!"
            )
        ),
        RhymeItem(
            "The ABC Song", "🔤", 45,
            listOf(
                "A, B, C, D, E, F, G,",
                "H, I, J, K, L, M, N, O, P,",
                "Q, R, S, T, U, V,",
                "W, X, Y and Z.",
                "Now I know my ABCs,",
                "Next time won't you sing with me!"
            )
        ),
        RhymeItem(
            "Hickory Dickory Dock", "🕰️", 35,
            listOf(
                "Hickory, dickory, dock,",
                "The mouse ran up the clock.",
                "The clock struck one,",
                "The mouse ran down,",
                "Hickory, dickory, dock!"
            )
        ),
        RhymeItem(
            "Mary Had a Little Lamb", "🐑", 40,
            listOf(
                "Mary had a little lamb,",
                "Its fleece was white as snow.",
                "And everywhere that Mary went,",
                "The lamb was sure to go!",
                "It followed her to school one day,",
                "Which was against the rule!"
            )
        )
    )

    // Badges
    val badges: List<Badge> = listOf(
        Badge("welcome", "First Lesson Badge", "Started the amazing learning journey!", "🌱", 0, "General"),
        Badge("abc_champion", "Alphabet Explorer", "Learned all 26 English alphabets!", "🔤", 26, "Letters"),
        Badge("number_wizard", "Number Champion", "Mastered numbers up to 100!", "🔢", 50, "Numbers"),
        Badge("coloring_artist", "Coloring Artist", "Colored your first beautiful masterpiece!", "🎨", 10, "Art"),
        Badge("master_artist", "Master Artist", "Completed 10 interactive coloring pages!", "🖼️", 30, "Art"),
        Badge("animal_expert", "Animal Expert", "Explored wild, farm, and ocean animals!", "🦁", 15, "Nature"),
        Badge("little_writer", "Little Writer", "Practiced tracing letters and numbers!", "✍️", 10, "Writing"),
        Badge("quiz_star", "Quiz Star", "Answered 5 quiz questions correctly!", "⭐", 20, "Quizzes"),
        Badge("super_thinker", "Super Thinker", "Solved 15 educational quizzes!", "🧠", 45, "Quizzes"),
        Badge("star_collector", "Star Collector", "Earned 20 golden stars across activities!", "🌟", 20, "Stars"),
        Badge("super_learner", "Super Learner", "Earned 50 sparkling stars!", "💫", 50, "Stars"),
        Badge("kids_world_hero", "Kids World Hero", "Reached the grand milestone of 100 stars!", "👑", 100, "Stars")
    )
}
