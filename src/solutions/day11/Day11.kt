package solutions.day11

import java.io.File
import java.util.*
import java.util.stream.Collectors

fun main() {
    part1()
    part2()
}

// Track the least common multiple of all the divisors from the monkey rules
var lcm = 1

fun part1() {
    val lines = readLines()
    val monkeys = parseInputToMonkeys(lines)

    // Play for 20 rounds
    for (i in 0..19) {
        performRound(monkeys)
    }

    // Find the 2 monkeys that inspected the most items
    val inspections = monkeys.stream()
        .map { monkey -> monkey.itemsInspected }
        .sorted(Collections.reverseOrder())
        .collect(Collectors.toList())

    println("Monkey Business ${inspections[0] * inspections[1]}")
}

fun part2() {
    val lines = readLines()
    val monkeys = parseInputToMonkeys(lines)

    // Play for 10000 rounds
    for (i in 0..9999) {
        performRound2(monkeys)
    }

    // Find the 2 monkeys that inspected the most items
    val inspections = monkeys.stream()
        .map { monkey -> monkey.itemsInspected }
        .sorted(Collections.reverseOrder())
        .collect(Collectors.toList())

    println("Monkey Business ${inspections[0] * inspections[1]}")
}

fun readLines(): List<String> {
    return File("src/solutions/day11/realInput.txt").readLines()
}

class Monkey(val items: MutableList<Long>, private val operator: String, private val operationConst: String, val divisibleBy: Int, private val trueMonkey: Int, private val falseMonkey: Int) {

    var itemsInspected: Long = 0L

    fun addToQueue(newItem: Long) {
        items.add(newItem)
    }

    fun performOperation(old: Long) : Long {
        val operand = if (operationConst == "old") old else operationConst.toLong()
        return if (operator == "+") {
            old + operand
        } else {
            old * operand
        }
    }

    fun inspectItem() {
        itemsInspected++
    }

    fun whichMonkeyToThrowTo(item: Long): Int {
        return if (item.mod(divisibleBy) == 0) {
            trueMonkey
        } else {
            falseMonkey
        }
    }

    override fun toString(): String {
        return items.toString()
    }
}

fun parseInputToMonkeys(lines: List<String>): List<Monkey> {
    val monkeys = mutableListOf<Monkey>()
    var i = 0
    while(i < lines.size) {
        // 0 - monkey number, we can skip
        // 1 - Starting items: 59, 65, 86, 56, 74, 57, 56
        val startingItems: MutableList<Long> = lines[i + 1]
            .substringAfter("Starting items: ")
            .split(", ")
            .map { it.toLong() } as MutableList<Long>

        // 2 - Operation: new = old * 17
        val operationParts = lines[i + 2]
            .substringAfter("Operation: new = old ")
            .split(" ")

        // 3 - Test: divisible by 3
        val divisibleBy = lines[i + 3]
            .substringAfter("Test: divisible by ")
            .toInt()

        // 4 - If true: throw to monkey 2
        val trueMonkey = lines[i + 4]
            .substringAfter("If true: throw to monkey ")
            .toInt()

        // 5 - If false: throw to monkey 3
        val falseMonkey = lines[i + 5]
            .substringAfter("If false: throw to monkey ")
            .toInt()

        val newMonkey = Monkey(startingItems, operationParts[0], operationParts[1], divisibleBy, trueMonkey, falseMonkey)

        monkeys.add(newMonkey)

        i += 7

        lcm *= divisibleBy
    }

    return monkeys
}

fun performRound(monkeys: List<Monkey>) {
    for (monkey in monkeys) {
        // Each monkey goes through their list
        val iter = monkey.items.iterator()
        while (iter.hasNext()) {
            // Inspect item
            monkey.inspectItem()

            // Get the item
            val currItem = iter.next()

            // Perform operation
            var worryLevel = monkey.performOperation(currItem)

            // Divide worry by 3
            worryLevel /= 3

            // Perform test and pass item
            val passToMonkey = monkey.whichMonkeyToThrowTo(worryLevel)

            monkeys[passToMonkey].addToQueue(worryLevel)

            // Remove the item after inspecting
            iter.remove()
        }
    }
}

fun performRound2(monkeys: List<Monkey>) {
    for (monkey in monkeys) {
        // Each monkey goes through their list
        val iter = monkey.items.iterator()
        while (iter.hasNext()) {
            // Inspect item
            monkey.inspectItem()

            // Get the item
            val currItem = iter.next()

            // Perform operation
            var worryLevel = monkey.performOperation(currItem)

            // Performing a modulus on the worry level should keep the number low while maintaining the same results,
            // since all the tests involve prime numbers we can use the least common multiplier
            worryLevel = worryLevel.mod(lcm).toLong()

            // Perform test and pass item
            val passToMonkey = monkey.whichMonkeyToThrowTo(worryLevel)

            monkeys[passToMonkey].addToQueue(worryLevel)

            // Remove the item after inspecting
            iter.remove()
        }
    }
}