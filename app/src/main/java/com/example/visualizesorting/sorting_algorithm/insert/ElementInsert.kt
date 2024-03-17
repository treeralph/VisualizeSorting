package com.example.visualizesorting.sorting_algorithm.insert

import com.example.visualizesorting.sorting_algorithm.data.Element

class ElementInsert: Insert<Element> {
    override suspend fun insert(arr: MutableList<Element>, index: Int, value: Element) {
        arr[index] = value.copy(colorSwitch = false)
        Thread.sleep(10)
        arr[index] = arr[index].copy(colorSwitch = true)
    }
}