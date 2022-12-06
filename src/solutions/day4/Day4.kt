package solutions.day4

import java.io.File


fun main() {
    part1()
    part2()
}

fun part1() {
    val lines = readLines()
    var fullyContains = 0
    for (line in lines) {
        // Split by comma
        val pairs = line.split(",")

        // First elf
        val firstElfSplit = pairs[0].split("-")
        val firstElfRange = (firstElfSplit[0].toInt()..firstElfSplit[1].toInt()).toSet()

        // Second elf
        val secondElfSplit = pairs[1].split("-")
        val secondElfRange = (secondElfSplit[0].toInt()..secondElfSplit[1].toInt()).toSet()

        if (firstElfRange.containsAll(secondElfRange) || secondElfRange.containsAll(firstElfRange)) {
            fullyContains++
        }
    }

    println(fullyContains)
}

fun part2() {
    val lines = readLines()
    var overlap = 0
    for (line in lines) {
        // Split by comma
        val pairs = line.split(",")

        // First elf
        val firstElfSplit = pairs[0].split("-")
        val firstElfRange = (firstElfSplit[0].toInt()..firstElfSplit[1].toInt()).toSet()

        // Second elf
        val secondElfSplit = pairs[1].split("-")
        val secondElfRange = (secondElfSplit[0].toInt()..secondElfSplit[1].toInt()).toSet()

        if (firstElfRange.intersect(secondElfRange).isNotEmpty()) {
            overlap++
        }
    }

    println(overlap)
}

fun readLines(): List<String> {
    return File("src/solutions/day4/realInput.txt").readLines()
}