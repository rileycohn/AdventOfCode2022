package solutions.day14

import java.io.File
import kotlin.math.max
import kotlin.math.min

fun main() {
    part1()
    part2()
}

fun part1() {
    val matrix = createGridFromLines(readLines())
    // printMatrix(matrix)

    var totalResting = 0
    val deepestY = findDeepestY(matrix)
    while (true) {
        val restingPoint = findRestingSpot(matrix)
        if (restingPoint.x < deepestY) {
            matrix[restingPoint.x][restingPoint.y] = 'o'
            totalResting++
        } else {
            break
        }

    }

    println("Part 1: $totalResting")

}

fun part2() {
    val matrix = createGridFromLines(readLines())
    // printMatrix(matrix)

    var totalResting = 0
    val floor = findDeepestY(matrix) + 2

    // Add floor to matrix
    for (i in 0..999) {
        matrix[floor][i] = '#'
    }

    while (true) {
        val restingPoint = findRestingSpot(matrix)
        if (restingPoint.x == 0 && restingPoint.y == 500) {
            break
        } else {
            matrix[restingPoint.x][restingPoint.y] = 'o'
            totalResting++
        }

    }

    println("Part 2: ${totalResting + 1}")
}

fun readLines(): List<String> {
    return File("src/solutions/day14/realInput.txt").readLines()
}

fun findDeepestY(matrix: Array<CharArray>): Int {

    var deepestY = 0
    for ((i, x) in matrix.withIndex()) {
        for ((j, _) in x.withIndex()) {
            if (matrix[i][j] == '#') {
                deepestY = max(deepestY, i)
            }
        }
    }

    return deepestY
}

fun findRestingSpot(matrix: Array<CharArray>) : Point {
    // Start at 0, 500
    var currPoint = Point(0, 500)

    while (true) {
        // Check out-of-bounds first
        if (currPoint.x + 1 > 999 || currPoint.y > 999) {
            return currPoint
        }

        // If space below is open, go down
        val charBelow = matrix[currPoint.x + 1][currPoint.y]

        // If not, go down to the left, if open
        val charBelowLeft = matrix[currPoint.x + 1][currPoint.y - 1]

        // If not, go down to the right, if open
        val charBelowRight = matrix[currPoint.x + 1][currPoint.y + 1]

        if (charBelow == '.') {
            currPoint = Point(currPoint.x + 1, currPoint.y)
        } else if (charBelowLeft == '.') {
            currPoint = Point(currPoint.x + 1, currPoint.y - 1)
        } else if (charBelowRight == '.') {
            currPoint = Point(currPoint.x + 1, currPoint.y + 1)
        } else {
            // Else, found resting space, return from loop
            return currPoint
        }
    }
}

fun createGridFromLines(lines: List<String>) : Array<CharArray> {
    val matrix : Array<CharArray> = Array(1000) {CharArray(1000)}
    // Initialize the grid with dots
    for (i in 0..999) {
        for (j in 0..999) {
            matrix[i][j] = '.'
        }
    }

    // Now we need to populate the grid with the rocks
    for (line in lines) {
        // Split by ->
        val rawCoords = line.split("->")

        val points = mutableListOf<Point>()
        for (coord in rawCoords) {
            // Remove whitespace
            val cleanedRawCoords = coord.trim()

            // Split by ,
            val xAndY = cleanedRawCoords.split(",")

            // y comes first
            points.add(Point(xAndY[1].toInt(), xAndY[0].toInt()))
        }

        var index = 0
        while (index < points.size - 1) {
            val pointsToFill = points[index].findPointsBetween(points[index + 1])
            for (point in pointsToFill) {
                matrix[point.x][point.y] = '#'
            }

            index++
        }
    }

    return matrix
}

class Point(val x: Int, val y: Int) {
    fun findPointsBetween(otherPoint: Point): List<Point> {
        val pointsBetween = mutableListOf<Point>()

        // Either x or y is different
        if (x == otherPoint.x) {
            // y is different
            for (i in min(this.y, otherPoint.y)..max(this.y, otherPoint.y)) {
                pointsBetween.add(Point(x, i))
            }
        } else {
            // x is different
            for (i in min(this.x, otherPoint.x)..max(this.x, otherPoint.x)) {
                pointsBetween.add(Point(i, y))
            }
        }

        return pointsBetween
    }
}

fun printMatrix(matrix: Array<CharArray>) {
    for ((i, x) in matrix.withIndex()) {
        for ((j, _) in x.withIndex()) {
            print(matrix[i][j])
        }
        println()
    }
}