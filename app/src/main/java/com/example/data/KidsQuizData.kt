package com.example.data

import com.example.model.QuizQuestion

object KidsQuizData {

    val alphabetQuestions: List<QuizQuestion> = listOf(
        QuizQuestion("a1", "ALPHABET", "Which letter does 'Apple' start with?", "🍎", listOf("A", "B", "C", "D"), 0, "Awesome job! A is for Apple!"),
        QuizQuestion("a2", "ALPHABET", "Which word starts with letter 'B'?", "🔤", listOf("Cat", "Ball", "Dog", "Fish"), 1, "Super! B is for bouncy Ball!"),
        QuizQuestion("a3", "ALPHABET", "Find the lowercase letter for 'M'?", "🦁", listOf("n", "w", "m", "u"), 2, "Brilliant! Big M and small m!"),
        QuizQuestion("a4", "ALPHABET", "Which letter makes the 'Zzz' sound like a Zebra?", "🦓", listOf("S", "X", "Z", "V"), 2, "Hooray! Z is for Zebra!"),
        QuizQuestion("a5", "ALPHABET", "Which fruit starts with letter 'M'?", "🥭", listOf("Banana", "Apple", "Mango", "Grapes"), 2, "Yummy! M is for sweet Mango!"),
        QuizQuestion("a6", "ALPHABET", "Which letter comes right before 'E'?", "🔤", listOf("C", "D", "F", "B"), 1, "Great memory! A, B, C, D, E!")
    )

    val numberQuestions: List<QuizQuestion> = listOf(
        QuizQuestion("n1", "NUMBER", "Which number comes after 4?", "🔢", listOf("3", "5", "6", "7"), 1, "High five! 5 comes after 4!"),
        QuizQuestion("n2", "NUMBER", "What is the number name for '10'?", "🔟", listOf("Nine", "Ten", "Seven", "Eight"), 1, "Fantastic! 10 is Ten!"),
        QuizQuestion("n3", "NUMBER", "Which number is bigger: 12 or 21?", "⚖️", listOf("12", "21", "Both equal", "None"), 1, "Smart thinking! 21 is bigger than 12!"),
        QuizQuestion("n4", "NUMBER", "What is 3 + 2?", "➕", listOf("4", "5", "6", "7"), 1, "Spot on! 3 plus 2 equals 5!"),
        QuizQuestion("n5", "NUMBER", "What is 5 - 1?", "➖", listOf("3", "4", "5", "2"), 1, "Great math skills! 5 minus 1 is 4!"),
        QuizQuestion("n6", "NUMBER", "What number comes before 20?", "🔢", listOf("18", "19", "21", "22"), 1, "Awesome! 19 comes before 20!")
    )

    val animalQuestions: List<QuizQuestion> = listOf(
        QuizQuestion("an1", "ANIMAL", "Which animal is the King of the Jungle?", "👑", listOf("Monkey", "Elephant", "Lion", "Rabbit"), 2, "Roar! Lion is the king!"),
        QuizQuestion("an2", "ANIMAL", "Who has a long trunk and big ears?", "🌿", listOf("Giraffe", "Elephant", "Horse", "Bear"), 1, "Trumpet! The gentle elephant!"),
        QuizQuestion("an3", "ANIMAL", "Which animal says 'Moo Moo'?", "🥛", listOf("Cow", "Dog", "Cat", "Sheep"), 0, "Moo! Cows give healthy milk!"),
        QuizQuestion("an4", "ANIMAL", "Which animal loves to eat carrots and hops fast?", "🥕", listOf("Tiger", "Rabbit", "Hippo", "Goat"), 1, "Hop hop! The fluffy rabbit!"),
        QuizQuestion("an5", "ANIMAL", "Which playful sea creature leaps out of the waves?", "🌊", listOf("Shark", "Crab", "Dolphin", "Octopus"), 2, "Splish splash! Dolphins love to leap!")
    )

    val colorQuestions: List<QuizQuestion> = listOf(
        QuizQuestion("c1", "COLOR", "What color is a ripe sweet strawberry?", "🍓", listOf("Blue", "Green", "Red", "Yellow"), 2, "Yes! Strawberries are bright Red!"),
        QuizQuestion("c2", "COLOR", "What color is the clear sunny daytime sky?", "☀️", listOf("Purple", "Blue", "Black", "Pink"), 1, "Look up! The sky is beautiful Blue!"),
        QuizQuestion("c3", "COLOR", "What color are fresh growing tree leaves?", "🍃", listOf("Green", "Orange", "Brown", "Red"), 0, "Naturally wonderful! Leaves are Green!"),
        QuizQuestion("c4", "COLOR", "What color is the glowing morning sun?", "🌅", listOf("Yellow", "Purple", "Cyan", "Gray"), 0, "Golden and bright! The Sun is Yellow!"),
        QuizQuestion("c5", "COLOR", "What color do you get if you mix Red and Yellow?", "🎨", listOf("Green", "Orange", "Blue", "Black"), 1, "Magic artist! Red + Yellow = Orange!")
    )

    val pictureMatchingQuestions: List<QuizQuestion> = listOf(
        QuizQuestion("pm1", "PICTURE", "Match the word to its picture: 'Ice Cream'", "🍦", listOf("Pizza", "Ice Cream", "Juice", "Cookie"), 1, "Yum! Delicious sweet Ice Cream!"),
        QuizQuestion("pm2", "PICTURE", "Which vehicle flies high through the clouds?", "☁️", listOf("Bicycle", "Car", "Airplane", "Boat"), 2, "Soar high! Airplanes fly in the sky!"),
        QuizQuestion("pm3", "PICTURE", "What do we use to protect ourselves from rain?", "🌧️", listOf("Umbrella", "Sunglasses", "Fan", "Backpack"), 0, "Open it up! Umbrella keeps us dry!"),
        QuizQuestion("pm4", "PICTURE", "Which one belongs in the school classroom?", "🏫", listOf("Pillow", "Blackboard", "Bed", "Spoon"), 1, "Ready to learn! Teachers write on the Blackboard!")
    )

    val missingLetterQuestions: List<QuizQuestion> = listOf(
        QuizQuestion("ml1", "MISSING_LETTER", "What is the missing letter?  A, B, __, D", "🔤", listOf("E", "C", "F", "G"), 1, "Super sequence! A, B, C, D!"),
        QuizQuestion("ml2", "MISSING_LETTER", "Complete the word:  C _ T", "🐱", listOf("A", "O", "U", "E"), 0, "Meow! C-A-T makes CAT!"),
        QuizQuestion("ml3", "MISSING_LETTER", "What comes next?  X, Y, __", "🔤", listOf("W", "Z", "A", "V"), 1, "Hurrah! You reached the end of ABC: Z!"),
        QuizQuestion("ml4", "MISSING_LETTER", "Complete the word:  D _ G", "🐶", listOf("A", "O", "I", "U"), 1, "Woof woof! D-O-G makes DOG!")
    )

    val countingQuestions: List<QuizQuestion> = listOf(
        QuizQuestion("cnt1", "COUNTING", "How many apples do you see?  🍎 🍎 🍎", "🍎", listOf("2", "3", "4", "5"), 1, "One, two, three! Exactly 3 apples!"),
        QuizQuestion("cnt2", "COUNTING", "Count the twinkling stars:  ⭐ ⭐ ⭐ ⭐ ⭐", "⭐", listOf("4", "5", "6", "3"), 1, "You counted 5 shining stars!"),
        QuizQuestion("cnt3", "COUNTING", "How many balloons?  🎈 🎈", "🎈", listOf("1", "2", "3", "4"), 1, "Two colorful floating balloons!"),
        QuizQuestion("cnt4", "COUNTING", "How many ducklings?  🐥 🐥 🐥 🐥", "🐥", listOf("3", "4", "5", "6"), 1, "Quack quack! 4 little ducklings!")
    )

    val shapeMatchingQuestions: List<QuizQuestion> = listOf(
        QuizQuestion("sh1", "SHAPE", "Which shape is round with NO sharp corners?", "⭕", listOf("Square", "Circle", "Triangle", "Star"), 1, "Round like a ball! It's a Circle!"),
        QuizQuestion("sh2", "SHAPE", "Which shape has 3 straight sides like a slice of pizza?", "🍕", listOf("Triangle", "Rectangle", "Oval", "Heart"), 0, "Pointy and fun! It's a Triangle!"),
        QuizQuestion("sh3", "SHAPE", "Which shape has 4 equal straight sides?", "⬛", listOf("Circle", "Hexagon", "Square", "Diamond"), 2, "Equal and sturdy! It's a Square!"),
        QuizQuestion("sh4", "SHAPE", "Which shape twinkles in the night sky?", "✨", listOf("Star", "Circle", "Pentagon", "Square"), 0, "Twinkle twinkle! It's a Star!")
    )

    fun getQuestionsForGame(type: String): List<QuizQuestion> {
        return when (type) {
            "ALPHABET" -> alphabetQuestions
            "NUMBER" -> numberQuestions
            "ANIMAL" -> animalQuestions
            "COLOR" -> colorQuestions
            "PICTURE" -> pictureMatchingQuestions
            "MISSING_LETTER" -> missingLetterQuestions
            "COUNTING" -> countingQuestions
            "SHAPE" -> shapeMatchingQuestions
            else -> alphabetQuestions
        }
    }
}
