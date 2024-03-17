package com.example.visualizesorting.sorting_algorithm.algorithm

import com.example.visualizesorting.MERGE_SORT_DISPLAY_INFO
import com.example.visualizesorting.sorting_algorithm.insert.Insert

class MergeSort<T: Comparable<T>>(
    private val _insert: Insert<T>
): SortingAlgorithm<T> {
    override val displayInfo: String = MERGE_SORT_DISPLAY_INFO
    override suspend fun sort(arr: MutableList<T>, low: Int, high: Int) {
        if(low < high) {
            val median = low + (high - low) / 2
            sort(arr, low, median)
            sort(arr, median + 1, high)
            merge(arr, low, median, high)
        }
    }

    private suspend fun merge(arr: MutableList<T>, low: Int, median: Int, high: Int) {

        val leftLength = median - low + 1
        val rightLength = high - median

        val left = mutableListOf<T>()
        val right = mutableListOf<T>()

        for(i in low..high) {
            if(i <= median) left.add(arr[i])
            else right.add(arr[i])
        }

        var i = 0
        var j = 0
        var k = low

        while(i < leftLength && j < rightLength) {
            if(left[i] <= right[j]) {
                _insert.insert(arr, k, left[i])
                i++
            }else {
                _insert.insert(arr, k, right[j])
                j++
            }
            k++
        }

        while(i < leftLength) {
            _insert.insert(arr, k, left[i])
            i++
            k++
        }

        while(j < rightLength) {
            _insert.insert(arr, k, right[j])
            j++
            k++
        }
    }
}