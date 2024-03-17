package com.example.visualizesorting.sorting_algorithm.swap

import com.example.visualizesorting.sorting_algorithm.data.Element

class ElementSwap: Swap<Element> {
    override suspend fun swap(arr: MutableList<Element>, i: Int, j: Int) {
        arr[i] = arr[i].copy(colorSwitch = false)
        arr[j] = arr[j].copy(colorSwitch = false)

        Thread.sleep(10)
        val temp = arr[i].copy()
        arr[i] = arr[j].copy()
        arr[j] = temp

        arr[i] = arr[i].copy(colorSwitch = true)
        arr[j] = arr[j].copy(colorSwitch = true)
    }
}