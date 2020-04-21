package com.example.mvp_livedata_base_kotlin.feature.navigation.bottomnavigation

import java.io.Serializable
import java.util.*

class TabHistory : Serializable {

    private val stack = Stack<Int>()

    val size
        get() = stack.size

    fun push(entry: Int) {
        stack.remove(entry)
        stack.add(entry)
    }

    fun popPrevious(): Int {
        stack.pop()

        return stack.peekOrElse(-1)
    }

    fun clear() {
        stack.clear()
    }

    private fun <T> Stack<T>.peekOrElse(optional: T): T {
        return if (this.isEmpty()) optional else this.peek()
    }
}