package com.example.visualizesorting.sorting_algorithm.algorithm

interface SortingAlgorithm<T: Comparable<T>> {
    val displayInfo: String
    suspend fun sort(arr: MutableList<T>, low: Int, high: Int)
}