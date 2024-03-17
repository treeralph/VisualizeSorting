package com.example.visualizesorting.sorting_algorithm.swap

interface Swap<T> {
    suspend fun swap(arr: MutableList<T>, i: Int, j: Int)
}