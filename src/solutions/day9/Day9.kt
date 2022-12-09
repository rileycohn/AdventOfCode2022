package solutions.day9

import java.io.File
import kotlin.math.abs

fun main() {
    part1()
    part2()
}

fun part1() {
    val instructions = readLines()
    val tailVisited = mutableSetOf<Point>()
    val currHeadLocation = Point(0, 0)
    var currTailLocation = Point(0, 0)
    tailVisited.add(currTailLocation)

    for (instruction in instructions) {
        // Split instructions into steps
        val steps = instruction.split(" ")
        val direction = steps[0]
        val amount = steps[1].toInt()

        for (i in 0 until amount) {
            // Move head to the new location
            currHeadLocation.movePoint(direction)

            println("Curr head: $currHeadLocation")

            // Check if tail is adjacent
            if (!currHeadLocation.isAdjacent(currTailLocation)) {
                // If not, see where to move it
                currTailLocation = currTailLocation.makeAdjacent(currHeadLocation)

                // Add new location to the set
                tailVisited.add(currTailLocation)
            }
        }
    }

    println(tailVisited)
    println(tailVisited.size)
}

fun part2() {
    val instructions = readLines()
    val tailVisited = mutableSetOf<Point>()
    val knots = mutableListOf<Point>()
    for (i in 0..9) {
        knots.add(Point(0, 0))
    }

    tailVisited.add(knots[9])

    for (instruction in instructions) {
        // Split instructions into steps
        val steps = instruction.split(" ")
        val direction = steps[0]
        val amount = steps[1].toInt()

        for (i in 0 until amount) {

            // Move head to the new location
            knots[0].movePoint(direction)

            println("Curr head: " + knots[0])

            // Every movement, we need to check against the prior knot in the list
            for (knot in 1 until knots.size) {

                // Check if this knot is adjacent to the one it follows
                if (!knots[knot - 1].isAdjacent(knots[knot])) {
                    // If not, see where to move it
                    knots[knot] = knots[knot].makeAdjacent(knots[knot - 1])

                    if (knot == 9) {
                        // Add new location to the set
                        tailVisited.add(knots[knot])
                    }
                }
            }
        }
    }

    println(tailVisited)
    println(tailVisited.size)
}

fun readLines(): List<String> {
    return File("src/solutions/day9/realInput.txt").readLines()
}

data class Point (var x: Int, var y: Int) {
    fun isAdjacent(point: Point): Boolean {
        return abs(x - point.x) <= 1 && abs(y - point.y) <= 1
    }

    fun movePoint(direction: String) {
        when (direction) {
            "R" -> {
                x ++
            }
            "L" -> {
                x --
            }
            "U" -> {
                y ++
            }
            "D" -> {
                y --
            }
        }
    }

    /**
     * Force the point object to become adjacent to the provided point input.
     *
     * If the points are in the same row, move x
     * If they are in the same column, move y
     * Else, move diagonally
     */
    fun makeAdjacent(point: Point) : Point {
        var newX = x
        var newY = y
        // Same column
        if (x == point.x) {
            if (y < point.y) {
                newY++
            } else {
                newY--
            }
        } else if (y == point.y) {
            if (x < point.x) {
                newX++
            } else {
                newX--
            }
        } else {
            // Different row and column, need to move x and y
            if (x < point.x) {
                newX++
            } else {
                newX--
            }

            if (y < point.y) {
                newY++
            } else {
                newY--
            }
        }

        return Point(newX, newY)
    }
}