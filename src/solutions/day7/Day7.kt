package solutions.day7

import java.io.File
import java.util.*

fun main() {
    part1()
}

fun part1() {
    val lines = readLines()
    val cwd = Stack<String>()
    cwd.push("/")
    val dirSize = mutableMapOf<String, Long>()
    val childDir = mutableMapOf<String, MutableSet<String>>()
    val allDirs = mutableSetOf<String>()
    allDirs.add("/")
    for (element in lines) {
        // Split each line
        val splitLine = element.split(" ")

        // Command incoming
        if (splitLine[0] == "$") {
            if (splitLine[1] == "cd") {
                // Go back to root, clear the stack
                if (splitLine[2] == "/") {
                    cwd.clear()
                    cwd.push("/")
                } else if(splitLine[2] == "..") {
                    // Go back one directory
                    cwd.pop()
                } else {
                    // Go forward one directory
                    // Add the whole path to the dir
                    val newDir = cwd.peek() + "/" + splitLine[2]
                    cwd.push(newDir)
                    allDirs.add(newDir)
                }
            }
            // I don't think we care about ls commands. When we print sizes, we will know what our current dir is
        } else if (splitLine[0] != "dir") {
            // We are printing the size of some files
            if (cwd.peek() in dirSize) {
                dirSize[cwd.peek()] = dirSize[cwd.peek()]!! + (splitLine[0].toLong())
            } else {
                dirSize[cwd.peek()] = splitLine[0].toLong()
            }
        } else {
            if (cwd.peek() in childDir) {
                childDir[cwd.peek()]!!.add(cwd.peek() + "/" + splitLine[1])
            } else {
                childDir[cwd.peek()] = mutableSetOf(cwd.peek() + "/" + splitLine[1])
            }
        }
    }

    val spacePerDir = mutableMapOf<String, Long>()
    var total = 0L
    for (dir in allDirs) {
        val currDirSize = dirSize(dir, dirSize, childDir)

        spacePerDir[dir] = currDirSize

        if (currDirSize <= 100000) {
            total += currDirSize
        }
    }

    println("Part 1: %s".format(total))

    part2(spacePerDir)
}

fun part2(spacePerDir: Map<String, Long>) {
    val totalDiskSpace = 70000000L
    val requiredUnusedSpace = 30000000L

    val totalUsedSpace: Long = spacePerDir["/"]!!

    val currFreeSpace = totalDiskSpace - totalUsedSpace

    val spaceToClean = requiredUnusedSpace - currFreeSpace

    // Find the smallest dir > spaceToClean
    var minAboveSpaceToClean = totalDiskSpace
    for (dir in spacePerDir.entries) {
        if (dir.value >= spaceToClean && dir.value < minAboveSpaceToClean) {
            minAboveSpaceToClean = dir.value
        }
    }

    println("Part 2: %d".format(minAboveSpaceToClean))
}

fun readLines(): List<String> {
    return File("src/solutions/day7/realInput.txt").readLines()
}

fun dirSize(dir: String, dirSizes: Map<String, Long>, childDirs: Map<String, MutableSet<String>>): Long {
    var total = 0L
    if (dir in dirSizes) {
        total += dirSizes[dir]!!
    }

    // Add all child dirs
    if (dir in childDirs) {
        for (child in childDirs[dir]!!) {
            val newSize = dirSize(child, dirSizes, childDirs)
            // println("Dir %s child %s size %s".format(dir, child, size))
            total += newSize
        }
    }

    return total
}