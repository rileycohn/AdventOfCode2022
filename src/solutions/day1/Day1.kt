package solutions.day1

import java.io.File
import java.util.*


fun main() {
    part1()
    part2()
}

fun part1() {
    val elvesCalories: PriorityQueue<Int> = createMaxHeap()
    println(elvesCalories.peek())
}

fun part2() {
    val elvesCalories: PriorityQueue<Int> = createMaxHeap()
    println(elvesCalories.poll() + elvesCalories.poll() + elvesCalories.poll())
}

fun createMaxHeap(): PriorityQueue<Int> {
    val lines = File("src/solutions/day1/realInput.txt").readLines()
    val elvesCalories: PriorityQueue<Int> = PriorityQueue<Int>(Collections.reverseOrder())
    var currElfCalories = 0
    for (line in lines) {
        if (line.isBlank()) {
            elvesCalories.add(currElfCalories)
            currElfCalories = 0
        } else {
            currElfCalories += line.toInt()
        }
    }

    return elvesCalories
}