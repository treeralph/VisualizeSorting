package com.example.visualizesorting.sorting_algorithm.algorithm

import com.example.visualizesorting.TIM_SORT_DISPLAY_INFO
import com.example.visualizesorting.sorting_algorithm.insert.Insert
import kotlin.math.min

class TimSort<T: Comparable<T>>(
    private val _insert: Insert<T>,
): SortingAlgorithm<T> {
    companion object { const val minRun = 32 }
    override val displayInfo: String = TIM_SORT_DISPLAY_INFO
    override suspend fun sort(arr: MutableList<T>, low: Int, high: Int) {
        for(i in low..high step(minRun)) {
            timInsertionSort(arr, i, min(i + minRun - 1, high))
        }
        var size = minRun
        while(size <= high) {
            var tempLow = 0
            while(tempLow <= high) {
                val median = tempLow + size - 1
                val tempHigh = min(tempLow + 2 * size - 1, high)
                if(median < tempHigh) {
                    timMerge(arr, tempLow, median, tempHigh)
                }
                tempLow += 2 * size
            }
            size *= 2
        }
    }

    private suspend fun timInsertionSort(arr: MutableList<T>, low: Int, high: Int) {
        for(i in low+1.. high) {
            val key = arr[i]
            var j = i - 1
            while(j >= low && arr[j] > key) {
                _insert.insert(arr, j+1, arr[j])
                j--
            }
            _insert.insert(arr, j+1, key)
        }
    }

    private suspend fun timMerge(arr: MutableList<T>, low: Int, median: Int, high: Int) {
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