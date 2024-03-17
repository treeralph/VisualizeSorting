package com.example.visualizesorting.sorting_algorithm.insert

interface Insert<T> {
    suspend fun insert(arr: MutableList<T>, index: Int, value: T)
}