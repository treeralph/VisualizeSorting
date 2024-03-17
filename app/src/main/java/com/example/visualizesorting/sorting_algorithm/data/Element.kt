package com.example.visualizesorting.sorting_algorithm.data

data class Element(
    val value: Int,
    val colorSwitch: Boolean = true
): Comparable<Element> {
    override fun compareTo(other: Element): Int {
        return if(value > other.value) 1 else -1
    }
}
