package com.example.visualizesorting.sorting_algorithm.algorithm

import com.example.visualizesorting.QUICK_SORT_DISPLAY_INFO
import com.example.visualizesorting.sorting_algorithm.swap.Swap

class QuickSort<T : Comparable<T>>(
    private val _swap: Swap<T>
): SortingAlgorithm<T> {
    override val displayInfo: String = QUICK_SORT_DISPLAY_INFO
    override suspend fun sort(arr: MutableList<T>, low: Int, high: Int) {
        if(low < high) {
            val pi = partition(arr, low, high)
            sort(arr, low, pi - 1)
            sort(arr, pi + 1, high)
        }
    }
    private suspend fun partition(arr: MutableList<T>, low: Int, high: Int): Int {
        val pivot = arr[high]
        var i = low - 1
        for(j in low until high) {
            if(arr[j] < pivot) {
                i++
                _swap.swap(arr, i, j)
            }
        }
        _swap.swap(arr, i + 1, high)
        return i + 1
    }
}