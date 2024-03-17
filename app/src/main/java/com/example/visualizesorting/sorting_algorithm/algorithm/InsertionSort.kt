package com.example.visualizesorting.sorting_algorithm.algorithm

import com.example.visualizesorting.INSERTION_SORT_DISPLAY_INFO
import com.example.visualizesorting.sorting_algorithm.insert.Insert

class InsertionSort<T: Comparable<T>>(
    private val _insert: Insert<T>
): SortingAlgorithm<T> {
    override val displayInfo: String = INSERTION_SORT_DISPLAY_INFO
    override suspend fun sort(arr: MutableList<T>, low: Int, high: Int) {
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
}