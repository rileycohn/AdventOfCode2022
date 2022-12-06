package solutions.day6

import java.io.File


fun main() {
    part1()
    part2()
}

fun part1() {
    val lines = readLines()
    val signal = lines[0].toCharArray()
    val queuePrefix = 4
    val queue = LimitedFifoQueue<Char>(queuePrefix)
    for (i in 0..signal.size) {
        queue.add(signal[i])
        if (areValuesUnique(queue, queuePrefix)) {
            println(i + 1)
            break
        }
    }
}

fun part2() {
    val lines = readLines()
    val signal = lines[0].toCharArray()
    val queuePrefix = 14
    val queue = LimitedFifoQueue<Char>(queuePrefix)
    for (i in 0..signal.size) {
        queue.add(signal[i])
        if (areValuesUnique(queue, queuePrefix)) {
            println(i + 1)
            break
        }
    }
}

fun areValuesUnique(queue: LimitedFifoQueue<Char>, minSize: Int): Boolean {
    if (queue.size < minSize) {
        return false
    }

    val unique = mutableSetOf<Char>()
    for (char in queue) {
        unique.add(char)
    }

    return unique.size == queue.size
}

fun readLines(): List<String> {
    return File("src/solutions/day6/realInput.txt").readLines()
}