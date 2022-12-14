package solutions.day13

import java.io.File
import java.lang.Integer.min

fun main() {
    part1()
    part2()
}

fun part1() {
    val lines = readLines()
    var index = 0
    var packetIndex = 0
    var answer = 0

    while (index <= lines.size) {
        val p1 = Packet(lines[index])
        val p2 = Packet(lines[index + 1])
        if (p1 > p2) {
            answer += packetIndex + 1
        }

        // Go to the next pair of packets
        index += 3
        packetIndex++
    }

    println("Part 1: $answer")
}

fun part2() {
    val lines = readLines()
    var index = 0
    var answer = 1
    val packets = mutableListOf<Packet>()

    while (index <= lines.size) {
        val p1 = Packet(lines[index])
        val p2 = Packet(lines[index + 1])
        packets.add(p1)
        packets.add(p2)

        // Go to the next pair of packets
        index += 3
    }

    // Now all packets are in the list
    // Add the new "divider" packets
    packets.add(Packet("[[2]]"))
    packets.add(Packet("[[6]]"))

    // Sort the packages
    packets.sort()
    packets.reverse()

    // Find the divider packets
    for ((i, packet) in packets.withIndex()) {
        if (packet.stringValue == "[[2]]" || packet.stringValue == "[[6]]") {
            answer *= i + 1
        }
    }

    println("Part 2: $answer")
}

fun readLines(): List<String> {
    return File("src/solutions/day13/realInput.txt").readLines()
}

class Packet (packet: String) : Comparable<Packet> {

    var children: MutableList<Packet> = mutableListOf()
    var value: Int = 0
    var isInt: Boolean = true
    var stringValue: String = packet

    init {
        children = mutableListOf()
        // Special case, handle empty list
        if (packet == "[]" || packet == "") {
            value = -1
        } else if (!packet.startsWith("[")) {
            // This packet is an int, not a list
            value = packet.toInt()
        } else {
            // Now we have nested packets to deal with
            isInt = false

            // Chop off the brackets
            val newPacket = packet.substring(1, packet.length - 1)
            var currentLevel = 0
            var tempString = ""
            for (char in newPacket) {
                if (char == ',' && currentLevel == 0) {
                    children.add(Packet(tempString))
                    tempString = ""
                } else {
                    if (char == '[') {
                        currentLevel++
                    } else if (char == ']') {
                        currentLevel--
                    }
                    tempString += char
                }
            }

            // If we have anything left over in tempString
            if (tempString.isNotEmpty()) {
                children.add(Packet(tempString))
            }
        }
    }

    override fun compareTo(other: Packet): Int {
        // If both are integers, simple compare
        if (isInt && other.isInt) {
            return other.value - value
        }

        // If both are lists
        if (!isInt && !other.isInt) {
            // Loop through both children and call compareTo
            // Use the smaller list, so we don't go out of bounds
            var i = 0
            while (i < min(children.size, other.children.size)) {
                val comparison = children[i].compareTo(other.children[i])
                if (comparison != 0) {
                    return comparison
                }
                i++
            }

            // If we finish the loop, compare the sizes of the list
            return other.children.size - children.size
        }

        // If one of the packets is an integer and the other is a list, make the int a list and compare
        val packet1 = if (isInt) Packet("[${value}]") else this
        val packet2 = if (other.isInt) Packet("[${other.value}]") else other
        return packet1.compareTo(packet2)
    }
}