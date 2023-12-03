package day3

import println
import readInput
import kotlin.system.measureTimeMillis

fun Char.isNotDigitOrDot(): Boolean {
    if (this.isDigit()) return false
    if (this == '.') return false
    return true
}

fun Char.isStar(): Boolean {
    if (this == '*') return true
    return false
}

fun checkSurroundingForSymbol(input: List<String>, lineIndex: Int, charIndex: Int): Boolean {
    if (lineIndex > 0) {
        if (charIndex > 0) {    // top left
            if (input[lineIndex - 1][charIndex - 1].isNotDigitOrDot()) return true
        }
        if (input[lineIndex - 1][charIndex].isNotDigitOrDot()) return true //top middle
        if (input[lineIndex - 1].length > charIndex + 1) {  //top right
            if (input[lineIndex - 1][charIndex + 1].isNotDigitOrDot()) return true
        }
    }
    if (lineIndex < input.size - 1) {
        if (charIndex > 0) {    // Bottom Left
            if (input[lineIndex + 1][charIndex - 1].isNotDigitOrDot()) return true
        }
        if (input[lineIndex + 1][charIndex].isNotDigitOrDot()) return true // Bottom middle
        if (input[lineIndex + 1].length > charIndex + 1) {    // Bottom right
            if (input[lineIndex + 1][charIndex + 1].isNotDigitOrDot()) return true
        }
    }
    if (charIndex > 0) {
        if (input[lineIndex][charIndex - 1].isNotDigitOrDot()) return true // Left
    }
    if (charIndex < input[lineIndex].length - 1) {
        if (input[lineIndex][charIndex + 1].isNotDigitOrDot()) return true // Right
    }
    return false
}

fun checkSurroundingForStar(input: List<String>, lineIndex: Int, charIndex: Int): StarNumber? {
    if (lineIndex > 0) {
        if (charIndex > 0) {    // top left
            if (input[lineIndex - 1][charIndex - 1].isStar()) return StarNumber(lineIndex - 1, charIndex - 1)
        }
        if (input[lineIndex - 1][charIndex].isStar()) return StarNumber(lineIndex - 1, charIndex) //top middle
        if (input[lineIndex - 1].length > charIndex + 1) {  //top right
            if (input[lineIndex - 1][charIndex + 1].isStar()) return StarNumber(lineIndex - 1, charIndex + 1)
        }
    }
    if (lineIndex < input.size - 1) {
        if (charIndex > 0) {    // Bottom Left
            if (input[lineIndex + 1][charIndex - 1].isStar()) return StarNumber(lineIndex + 1, charIndex - 1)
        }
        if (input[lineIndex + 1][charIndex].isStar()) return StarNumber(lineIndex + 1, charIndex) // Bottom middle
        if (input[lineIndex + 1].length > charIndex + 1) {    // Bottom right
            if (input[lineIndex + 1][charIndex + 1].isStar()) return StarNumber(lineIndex + 1, charIndex + 1)
        }
    }
    if (charIndex > 0) {
        if (input[lineIndex][charIndex - 1].isStar()) return StarNumber(lineIndex, charIndex - 1) // Left
    }
    if (charIndex < input[lineIndex].length - 1) {
        if (input[lineIndex][charIndex + 1].isStar()) return StarNumber(lineIndex, charIndex + 1) // Right
    }
    return null
}

fun part1(input: List<String>): Int {
    val validValues = mutableListOf<Int>()
    input.forEachIndexed { lineIndex, line ->
        var digit = 0
        var foundDigit = false
        var foundSymbol = false

        line.forEachIndexed { charIndex, c ->
//            println("$lineIndex:$charIndex::$c")
            if (c.isDigit()) {
                foundDigit = true
                digit *= 10
                digit += c.digitToInt()
            } else {
                if (foundDigit && foundSymbol) {
                    validValues.add(digit)
                }
                digit = 0
                foundDigit = false
                foundSymbol = false
            }
            if (foundDigit && !foundSymbol) {
                foundSymbol = checkSurroundingForSymbol(input, lineIndex, charIndex)
            }
        }
        if (foundDigit && foundSymbol) {
            validValues.add(digit)
        }
    }

    return validValues.sumOf { it }
}

data class StarNumber(
    val lineIndex: Int,
    val charIndex: Int,
    var number: Int = 0,
)

fun part2(input: List<String>): Int {
    val validValues = mutableListOf<StarNumber>()
    input.forEachIndexed { lineIndex, line ->
        var digit = 0
        var foundDigit = false
        var foundStar: StarNumber? = null
        line.forEachIndexed { charIndex, c ->
//            println("$lineIndex:$charIndex::$c")
            if (c.isDigit()) {
                foundDigit = true
                digit *= 10
                digit += c.digitToInt()
            } else {
                if (foundDigit && foundStar != null) {
                    foundStar!!.number = digit
                    validValues.add(foundStar!!)
                }
                digit = 0
                foundDigit = false
                foundStar = null
            }
            if (foundDigit && foundStar == null) {
                foundStar = checkSurroundingForStar(input, lineIndex, charIndex)
            }
        }
        if (foundDigit && foundStar != null) {
            foundStar!!.number = digit
            validValues.add(foundStar!!)
        }
    }

    return validValues
        .groupBy { it.lineIndex to it.charIndex }
        .filter { pairListEntry -> pairListEntry.value.size > 1 }
        .map { it.value }
        .sumOf { it[0].number * it[1].number }
}

fun main() {
    val input = readInput("day3/d3_input")
    val part1Millis = measureTimeMillis {
        part1(input).println()
    }
    check(part1(input) == 539433)
    println("$part1Millis ms for part 1")

    val part2Millis = measureTimeMillis {
        part2(input).println()
    }
    check(part2(input) == 75847567)
    println("$part2Millis ms for part 2")

}
