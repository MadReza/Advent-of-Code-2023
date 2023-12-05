package day02

import println
import readInput

enum class Colors {
    red,
    blue,
    green
}

data class Game(
    val gameNumber: Int,
    val sets: List<CubeSet>,
)

data class CubeSet(
    val redAmount: Int,
    val blueAmount: Int,
    val greenAmount: Int,
)

private fun getAllGames(input: List<String>): MutableList<Game> {
    val validGames = mutableListOf<Game>()

    input.forEach { line ->
        val (game, sets) = line.split(":")
        val gameNumber = game.split(" ")[1].toInt()
        val listOfCubeSet = mutableListOf<CubeSet>()

        sets.split(";").forEach { set ->
            val cubesPicked = set.split(",")
                .associate { cubesPicked ->
                    val (amount, color) = cubesPicked.trim().split(" ")
                    Colors.valueOf(color) to amount.toInt()
                }
            val cubeSet = CubeSet(
                redAmount = cubesPicked[Colors.red] ?: 0,
                blueAmount = cubesPicked[Colors.blue] ?: 0,
                greenAmount = cubesPicked[Colors.green] ?: 0,
            )

            listOfCubeSet.add(cubeSet)
        }

        validGames.add(Game(gameNumber, listOfCubeSet))
    }
    return validGames
}

fun part1(input: List<String>): Int {
    val maxRedCubes = 12
    val maxGreenCubes = 13
    val maxBlueCubes = 14

    val validGames = getAllGames(input)

    return validGames
        .filter {
            it.sets.all { cubeSet ->
                cubeSet.redAmount <= maxRedCubes && cubeSet.blueAmount <= maxBlueCubes && cubeSet.greenAmount <= maxGreenCubes
            }
        }
        .sumOf { it.gameNumber }
}

fun part2(input: List<String>): Int = getAllGames(input)
    .map { game ->
        val redAmount = game.sets.maxOf { it.redAmount }
        val greenAmount = game.sets.maxOf { it.greenAmount }
        val blueAmount = game.sets.maxOf { it.blueAmount }
        redAmount * greenAmount * blueAmount
    }.sumOf { it }

fun main() {
    val input = readInput("day02/d2_input")
    part1(input).println()
    part2(input).println()
}
