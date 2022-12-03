package solutions.day2

import java.io.File

val part1Results = mapOf(
    "AX" to 1 + 3,
    "AY" to 2 + 6,
    "AZ" to 3 + 0,
    "BX" to 1 + 0,
    "BY" to 2 + 3,
    "BZ" to 3 + 6,
    "CX" to 1 + 6,
    "CY" to 2 + 0,
    "CZ" to 3 + 3
)

val part2Results = mapOf(
    "AX" to 3 + 0,
    "AY" to 1 + 3,
    "AZ" to 2 + 6,
    "BX" to 1 + 0,
    "BY" to 2 + 3,
    "BZ" to 3 + 6,
    "CX" to 2 + 0,
    "CY" to 3 + 3,
    "CZ" to 1 + 6
)


fun main() {
    part1()
    part2()
}

fun part1() {
    val turns = readTurns()
    var score = 0
    for (turn in turns) {
        score += part1Results[turn]!!
    }

    println(score)
}

fun part2() {
    val turns = readTurns()
    var score = 0
    for (turn in turns) {
        score += part2Results[turn]!!
    }

    println(score)
}

fun readTurns(): List<String> {
    val lines = File("src/solutions/day2/realInput.txt").readLines()
    val turns = mutableListOf<String>()
    for (line in lines) {
        turns.add(line.filterNot { it.isWhitespace() })
    }

    return turns
}