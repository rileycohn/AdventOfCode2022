package solutions.day12

import java.io.File
import java.util.*
import kotlin.math.min

fun main() {
    part1()
    part2()
}

fun part1() {
    val heightMap = readLinesToMatrix(readLines())

    println("Starting at point ${heightMap.startingPoint}. Ending at point ${heightMap.endingPoint}. Height Map: ${heightMap.printMap()}")

    println("Part 1: ${heightMap.bfs(heightMap.startingPoint)}")
}

fun part2() {
    val heightMap = readLinesToMatrix(readLines())

    var minSteps = 10000000
    // Perform BFS from each 'a' starting point
    for (start in heightMap.aLocations) {
        val distFromPointA = heightMap.bfs(start)
        if (distFromPointA > 0) {
            minSteps = min(heightMap.bfs(start), minSteps)
        }
    }

    println("Part 2: $minSteps")
}

fun readLines(): List<String> {
    return File("src/solutions/day12/realInput.txt").readLines()
}

data class HeightMap(val map: Array<CharArray>, val startingPoint: Point, val endingPoint: Point, val aLocations: List<Point>) {
    fun printMap() {
        for ((y, _) in map.withIndex()) {
            for (x in map[y]) {
                print(x)
            }
            println()
        }
    }

    fun bfs(start: Point): Int {
        val compareByDist: Comparator<QueueItem> = compareBy { it.dist }
        val queue: PriorityQueue<QueueItem> = PriorityQueue(compareByDist)
        val visited = mutableSetOf<Point>()

        queue.add(QueueItem(start, 0))

        while (queue.isNotEmpty()) {
            val currPoint = queue.poll()

            if (currPoint.point in visited) {
                continue
            }

            visited.add(currPoint.point)

            val currChar = map[currPoint.point.x][currPoint.point.y]

            // If we found the end, we're done
            if (currChar == 'E') {
                return currPoint.dist
            }

            // Go each direction and add them to the queue

            // Up (x - 1)
            if (isValidToVisit(map, visited, currPoint.point.x - 1, currPoint.point.y, currChar)) {
                val newPoint = Point(currPoint.point.x - 1, currPoint.point.y)
                queue.add(QueueItem(newPoint, currPoint.dist + 1))
            }

            // Down (x + 1)
            if (isValidToVisit(map, visited, currPoint.point.x + 1, currPoint.point.y, currChar)) {
                val newPoint = Point(currPoint.point.x + 1, currPoint.point.y)
                queue.add(QueueItem(newPoint, currPoint.dist + 1))
            }

            // Left (y - 1)
            if (isValidToVisit(map, visited, currPoint.point.x, currPoint.point.y - 1, currChar)) {
                val newPoint = Point(currPoint.point.x, currPoint.point.y - 1)
                queue.add(QueueItem(newPoint, currPoint.dist + 1))
            }

            // Right (y + 1)
            if (isValidToVisit(map, visited, currPoint.point.x, currPoint.point.y + 1, currChar)) {
                val newPoint = Point(currPoint.point.x, currPoint.point.y + 1)
                queue.add(QueueItem(newPoint, currPoint.dist + 1))
            }
        }

        // If we never find the end, that's bad. Return -1
        return -1
    }

    private fun isValidToVisit(map: Array<CharArray>, visited: Set<Point>, row: Int, col: Int, currChar: Char): Boolean {
        // Check if out of bounds
        if (row < 0 || col < 0 || row >= map.size || col >= map[0].size) {
            return false
        }

        // Check if the cell is already visited
        if (Point(row, col) in visited) {
            return false
        }

        // Elevation
        val elevation = if (map[row][col] == 'E') 'z'.digitToInt(radix = 36) else map[row][col].digitToInt(radix = 36)

        // Check the value of the field. We can always go down letters, but we can only go up 1 letter
        if (elevation - currChar.digitToInt(radix = 36) < 2) {
            return true
        }

        return false
    }
}

data class Point(val x: Int, val y: Int)

data class QueueItem(val point: Point, val dist: Int)

fun readLinesToMatrix(lines: List<String>): HeightMap {
    var startingPoint = Point(0 ,0)
    var endingPoint = Point(0 ,0)
    val aLocations = mutableListOf<Point>()

    val width = lines[0].length
    val matrix : Array<CharArray> = Array(lines.size) {CharArray(width)}
    for ((y, line) in lines.withIndex()) {
        for ((x, char) in line.withIndex()) {
            matrix[y][x] = char
            if (char == 'S') {
                startingPoint = Point(y, x)
            } else if (char == 'E') {
                endingPoint = Point(y, x)
            } else if (char == 'a') {
                aLocations.add(Point(y, x))
            }
        }
    }

    return HeightMap(matrix, startingPoint, endingPoint, aLocations)
}