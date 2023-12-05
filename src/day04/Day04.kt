package day04

import println
import readInput
import kotlin.system.measureTimeMillis


fun part1(input: List<String>): Int {
    var totalSum = 0
    input.forEachIndexed { lineIndex, line ->
        val (winningNumbersLine, myNumbersLine) = line.split(":")[1].split("|")
        val winningNumbers = winningNumbersLine.trim().split(" ").filter { it.trim() != "" }.map { it.toInt() }.sorted()
        val myNumbers = myNumbersLine.trim().split(" ").filter { it.trim() != "" }.map { it.toInt() }.sorted()

        val size = myNumbers.filter { winningNumbers.contains(it) }.size

        var total = 0
        if (size == 1) {
            total = 1
        } else if (size > 1) {
            total = 1
            for (x in 2..size) {
                total *= 2
            }
        }

        totalSum += total

    }
    return totalSum
}

data class Card(
    val cardNumber: Int,
    val totalWinningNumbers: Int,
)

fun part2(input: List<String>): Int {
    val originalCards = mutableListOf<Card>()
    input.forEachIndexed { lineIndex, line ->
        val (cardNumber, numbers) = line.split(":")
        val (winningNumbersLine, myNumbersLine) = numbers.split("|")
        val winningNumbers = winningNumbersLine.trim().split(" ").filter { it.trim() != "" }.map { it.toInt() }.sorted()
        val myNumbers = myNumbersLine.trim().split(" ").filter { it.trim() != "" }.map { it.toInt() }.sorted()

        val size = myNumbers.filter { winningNumbers.contains(it) }.size

        cardNumber.replace("Card", "")
            .replace(" ", "")
        originalCards.add(Card(
            cardNumber = cardNumber.replace("Card", "").replace(" ", "").toInt(),
            totalWinningNumbers = size,
        ))

    }
    val winningCards = mutableListOf<Card>()

    originalCards.forEach {
        addWinnings(it, winningCards, originalCards)
    }

    return winningCards.size
}

fun addWinnings(card: Card, winningCards: MutableList<Card>, originalCards: List<Card>) {
    winningCards.add(card)
    for (x in 1..card.totalWinningNumbers){
        val newCard = originalCards.find { it.cardNumber == x+card.cardNumber }
        if (newCard != null) {
            addWinnings(newCard, winningCards, originalCards)
        }
    }
}

fun main() {
    val input = readInput("day04/d4_input")
    val part1Millis = measureTimeMillis {
        part1(input).println()
    }
    check(part1(input) == 28538)
    println("$part1Millis ms for part 1")

    val part2Millis = measureTimeMillis {
        part2(input).println()
    }
    check(part2(input) == 9425061)
    println("$part2Millis ms for part 2")

}
