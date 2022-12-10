package solutions.day10

import java.io.File

fun main() {
    part1()
    part2()
}

fun part1() {
    val lines = readLines()
    var register = 1
    var cycle = 1
    val signalStrengths = mutableListOf<Int>()
    val significantCycles = mutableSetOf(20, 60, 100, 140, 180, 220)

    for (line in lines) {

        val split = line.split(" ")

        if (cycle in significantCycles) {
            signalStrengths.add(cycle * register)
        }

        if (split[0] == "noop") {
            cycle++
        } else {
            val valToAdd = split[1].toInt()

            // Add command takes 2 cycles, but we need to check the significant cycles in between
            cycle++

            if (cycle in significantCycles) {
                signalStrengths.add(cycle * register)
            }

            // Take another cycle
            cycle++

            // After the 2 cycles are done, add the value to the register
            register += valToAdd
        }
    }
    println("Part 1: ${signalStrengths.sum()}")
}

fun part2() {
    val lines = readLines()
    var register = 1
    var cycle = 0
    val crt = mutableListOf<String>()

    for (line in lines) {

        val split = line.split(" ")

        if (split[0] == "noop") {
            // If the sprite is lined up with the current cycle
            if (kotlin.math.abs(cycle.mod(40) - register) <= 1) {
                crt.add("#")
            } else {
                crt.add(".")
            }
            cycle++
        } else {
            val valToAdd = split[1].toInt()

            // Add command takes 2 cycles, but we need to check the significant cycles in between
            // If the sprite is lined up with the current cycle
            if (kotlin.math.abs(cycle.mod(40) - register) <= 1) {
                crt.add("#")
            } else {
                crt.add(".")
            }
            cycle++

            // Take another cycle
            // If the sprite is lined up with the current cycle
            if (kotlin.math.abs(cycle.mod(40) - register) <= 1) {
                crt.add("#")
            } else {
                crt.add(".")
            }

            cycle++

            // After the 2 cycles are done, add the value to the register
            register += valToAdd
        }
    }

    for ((index, char) in crt.withIndex()) {
        if (index.mod(40) == 0) {
            println()
        }

        print(char)
    }
}

fun readLines(): List<String> {
    return File("src/solutions/day10/realInput.txt").readLines()
}