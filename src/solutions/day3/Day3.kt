package solutions.day3

import java.io.File

val alphabetMap = mutableMapOf<Char, Int>()

fun main() {
    buildAlphabetMap()
    part1()
    part2()
}

fun part1() {
    val lines = readLines()
    var sum = 0

    for (line in lines) {
        // Split the line in half
        val firstHalf = line.subSequence(0, line.length / 2)
        val secondHalf = line.subSequence(line.length / 2, line.length)
        val intersect = firstHalf.toString().toCharArray().intersect(secondHalf.toString().asIterable())

        for (char in intersect) {
            sum += alphabetMap[char]!!
        }
    }

    println(sum)
}

fun part2() {
    val lines = readLines()
    var sum = 0
    for (i in lines.indices step 3) {
        val intersect = lines[i].toCharArray().intersect(lines[i + 1].asIterable()).intersect(lines[i + 2].asIterable())

        for (char in intersect) {
            sum += alphabetMap[char]!!
        }
    }

    println(sum)
}

fun readLines(): List<String> {
    return File("src/solutions/day3/realInput.txt").readLines()
}

fun buildAlphabetMap() {
    var c = 'a'
    var i = 1
    while (c <= 'z') {
        alphabetMap[c] = i
        c++
        i++
    }

    c = 'A'
    while (c <= 'Z') {
        alphabetMap[c] = i
        c++
        i++
    }
}