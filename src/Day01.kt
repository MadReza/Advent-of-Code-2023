fun part1(input: List<String>): Int =
    input.sumOf { line ->
        val first = line.first { it.isDigit() }
        val last = line.last { it.isDigit() }
        "$first$last".toInt()
    }

fun part2(input: List<String>): Int =
    input.sumOf { line ->
        val first = firstDigitOrNumber(line)
        val last = lastDigitOrNumber(line)
        println("$first + $last")
        "$first$last".toInt()
    }

fun firstDigitOrNumber(input: String): Char? {

    input.forEachIndexed { index, c ->
        if (c.isDigit()) return c
        val numberFound = input.numberAsDigitOrNull(index, words)

        if (numberFound != null) return numberFound
    }
    return null
}

fun lastDigitOrNumber(input: String): Char? {
    val reversedInput = input.reversed()
    reversedInput.forEachIndexed { index, c ->
        if (c.isDigit()) return c
        val numberFound = reversedInput.numberAsDigitOrNull(index, wordsReversed)

        if (numberFound != null) return numberFound
    }
    return null
}

fun String.numberAsDigitOrNull(startingIndex: Int, possibleWords: Map<String, Char>): Char? {
    val combinations = mutableListOf<String>()
    (3 .. 5).forEach { digitLength ->
        combinations.add(substring(startingIndex, (startingIndex + digitLength).coerceAtMost(length)))
    }

    return combinations.firstNotNullOfOrNull { candidate ->
        possibleWords[candidate]
    }
}

private val words: Map<String, Char> = mapOf(
    "one" to '1',
    "two" to '2',
    "three" to '3',
    "four" to '4',
    "five" to '5',
    "six" to '6',
    "seven" to '7',
    "eight" to '8',
    "nine" to '9'
)

private val wordsReversed: Map<String, Char> = mapOf(
    "one".reversed() to '1',
    "two".reversed() to '2',
    "three".reversed() to '3',
    "four".reversed() to '4',
    "five".reversed() to '5',
    "six".reversed() to '6',
    "seven".reversed() to '7',
    "eight".reversed() to '8',
    "nine".reversed() to '9'
)


fun main() {
    val input = readInput("d1_input")
//    part1(input).println()
    part2(input).println()
}
