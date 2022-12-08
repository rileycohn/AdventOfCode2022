package solutions.day8

import java.io.File
import kotlin.math.max

fun main() {
    part1()
    part2()
}

fun part1() {
    val lines = readLines()
    val matrix = readLinesToMatrix(lines)
    printMatrix(matrix)
    val visibleTrees = traverseMatrix(matrix)
    println("Visible Trees: %d".format(visibleTrees))
}

fun part2() {
    val lines = readLines()
    val matrix = readLinesToMatrix(lines)
    val maxScenicScore = getMaxScenicScore(matrix)
    println("Max Scenic Score %d".format(maxScenicScore))
}

fun readLines(): List<String> {
    return File("src/solutions/day8/realInput.txt").readLines()
}

fun printMatrix(matrix: Array<IntArray>) {
    for ((i, x) in matrix.withIndex()) {
        for ((j, _) in x.withIndex()) {
            print(matrix[j][i])
        }
        println()
    }
}

fun readLinesToMatrix(lines: List<String>): Array<IntArray> {
    val width = lines[0].length
    val matrix : Array<IntArray> = Array(width) {IntArray(width)}
    for ((y, line) in lines.withIndex()) {
        for ((x, num) in line.withIndex()) {
            matrix[x][y] = num.toString().toInt()
        }
    }

    return matrix
}

fun traverseMatrix(matrix: Array<IntArray>): Int {
    var visibleTrees = 0
    for ((i, x) in matrix.withIndex()) {
        for ((j, _) in x.withIndex()) {
            // For each element in the matrix, need to go left, right, up, and down to see if it's visible
            if (isVisibleFromLeft(matrix, i, j) || isVisibleFromRight(matrix, i, j)
                    || isVisibleFromTop(matrix, i, j)  || isVisibleFromBottom(matrix, i, j)) {
                visibleTrees++
            }
        }
    }

    return visibleTrees
}

fun isVisibleFromLeft(matrix: Array<IntArray>, startX: Int, startY: Int): Boolean {
    val treeHeight = matrix[startX][startY]

    // Go left until x = 0 to see if any trees are taller
    var currX = startX - 1
    while (currX >= 0) {
        // Found a taller tree, return false
        if (matrix[currX][startY] >= treeHeight) {
            return false
        }

        currX--
    }

    // Didn't find any taller trees, so given tree is visible from the left
    return true
}

fun isVisibleFromRight(matrix: Array<IntArray>, startX: Int, startY: Int): Boolean {
    val treeHeight = matrix[startX][startY]

    // Go right until x = matrix.size to see if any trees are taller
    var currX = startX + 1
    while (currX < matrix.size) {
        // Found a taller tree, return false
        if (matrix[currX][startY] >= treeHeight) {
            return false
        }

        currX++
    }

    // Didn't find any taller trees, so given tree is visible from the right
    return true
}

fun isVisibleFromTop(matrix: Array<IntArray>, startX: Int, startY: Int): Boolean {
    val treeHeight = matrix[startX][startY]

    // Go up until y = 0 to see if any trees are taller
    var currY = startY - 1
    while (currY >= 0) {
        // Found a taller tree, return false
        if (matrix[startX][currY] >= treeHeight) {
            return false
        }
        currY--
    }

    // Didn't find any taller trees, so given tree is visible from the top
    return true
}

fun isVisibleFromBottom(matrix: Array<IntArray>, startX: Int, startY: Int): Boolean {
    val treeHeight = matrix[startX][startY]

    // Go down until y = matrix.size to see if any trees are taller
    var currY = startY + 1
    while (currY < matrix.size) {
        // Found a taller tree, return false
        if (matrix[startX][currY] >= treeHeight) {
            return false
        }
        currY++
    }

    // Didn't find any taller trees, so given tree is visible from the top
    return true
}

fun getMaxScenicScore(matrix: Array<IntArray>): Int {
    var maxScenicScore = 0
    for ((i, x) in matrix.withIndex()) {
        for ((j, _) in x.withIndex()) {
            // For each element, calculate scenic score
            maxScenicScore = max(maxScenicScore, getScenicScore(matrix, i, j))
        }
    }

    return maxScenicScore
}

fun getScenicScore(matrix: Array<IntArray>, startX: Int, startY: Int): Int {
    val treeHeight = matrix[startX][startY]

    // Up
    var treesVisibleUp = 0

    // Go down until y = matrix.size to see if any trees are taller
    var currY = startY + 1
    while (currY < matrix.size) {
        treesVisibleUp++

        // Found a taller tree, return false
        if (matrix[startX][currY] >= treeHeight) {
            break
        }
        currY++
    }

    // Down
    var treesVisibleDown = 0

    // Go down until y = matrix.size to see if any trees are taller
    currY = startY - 1
    while (currY >= 0) {
        treesVisibleDown++

        // Found a taller tree, return false
        if (matrix[startX][currY] >= treeHeight) {
            break
        }
        currY--
    }

    // Left
    var treesVisibleLeft = 0

    // Go left until x = 0 to see if any trees are taller
    var currX = startX - 1
    while (currX >= 0) {
        treesVisibleLeft++

        // Found a taller tree, return false
        if (matrix[currX][startY] >= treeHeight) {
            break
        }

        currX--
    }

    // Right
    var treesVisibleRight = 0

    // Go right until x = matrix.size to see if any trees are taller
    currX = startX + 1
    while (currX < matrix.size) {
        treesVisibleRight++
        // Found a taller tree, return false
        if (matrix[currX][startY] >= treeHeight) {
            break
        }

        currX++
    }

    return treesVisibleUp * treesVisibleDown * treesVisibleLeft * treesVisibleRight
}