package solutions.day5

import java.io.File
import java.util.*


fun main() {
    part1()
    part2()
}

fun part1() {
    // We need a list of stacks
    val listOfStacks = mutableListOf<Stack<Char>>()
    var directionTime = false
    for (line in readLines()) {
        if (line.isBlank()) {
            directionTime = true
            continue
        }

        if (!directionTime) {
            // First char of line is the stack number + 1
            val newStack = Stack<Char>()
            for (char in line.subSequence(1, line.length)) {
                newStack.push(char)
            }

            listOfStacks.add(newStack)
        } else {
            // String looks like
            // move {pop num} from {origin stack} to {dest stack}
            val match = Regex("move (\\d+) from (\\d+) to (\\d+)").find(line)!!
            val (pop, origin, dest) = match.destructured

            // perform the move
            for (i in 1..pop.toInt()) {
                listOfStacks[dest.toInt() - 1].push(listOfStacks[origin.toInt() - 1].pop())
            }
        }
    }

    println(listOfStacks)

    for (stack in listOfStacks) {
        print(stack.peek())
    }
}

fun part2() {
    // We need a list of stacks
    val listOfStacks = mutableListOf<Stack<Char>>()
    var directionTime = false
    for (line in readLines()) {
        if (line.isBlank()) {
            directionTime = true
            continue
        }

        if (!directionTime) {
            // First char of line is the stack number + 1
            val newStack = Stack<Char>()
            for (char in line.subSequence(1, line.length)) {
                newStack.push(char)
            }

            listOfStacks.add(newStack)
        } else {
            // String looks like
            // move {pop num} from {origin stack} to {dest stack}
            val match = Regex("move (\\d+) from (\\d+) to (\\d+)").find(line)!!
            val (pop, origin, dest) = match.destructured

            // perform the move
            val stackedCrated = mutableListOf<Char>()
            for (i in 1..pop.toInt()) {
                stackedCrated.add(listOfStacks[origin.toInt() - 1].pop())
            }

            for (i in stackedCrated.indices.reversed()) {
                listOfStacks[dest.toInt() - 1].push(stackedCrated[i])
            }
        }
    }

    println(listOfStacks)

    for (stack in listOfStacks) {
        print(stack.peek())
    }
}

fun readLines(): List<String> {
    return File("src/solutions/day5/realInput.txt").readLines()
}