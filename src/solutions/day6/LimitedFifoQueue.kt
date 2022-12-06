package solutions.day6

import java.util.*

class LimitedFifoQueue<T> constructor(private val limit: Int) : LinkedList<T>() {

    override fun add(element: T): Boolean {
        val added = super.add(element)
        while (added && super.size > limit) {
            super.remove()
        }

        return added
    }
}