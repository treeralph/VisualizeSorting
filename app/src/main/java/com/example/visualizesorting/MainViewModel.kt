package com.example.visualizesorting

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.visualizesorting.sorting_algorithm.algorithm.InsertionSort
import com.example.visualizesorting.sorting_algorithm.algorithm.MergeSort
import com.example.visualizesorting.sorting_algorithm.algorithm.QuickSort
import com.example.visualizesorting.sorting_algorithm.algorithm.SortingAlgorithm
import com.example.visualizesorting.sorting_algorithm.algorithm.TimSort
import com.example.visualizesorting.sorting_algorithm.data.Element
import com.example.visualizesorting.sorting_algorithm.insert.ElementInsert
import com.example.visualizesorting.sorting_algorithm.swap.ElementSwap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _array = mutableStateListOf<Element>()
    private val _algorithmName = MutableLiveData<String>()

    val array: List<Element> = _array
    val algorithmName: LiveData<String> = _algorithmName

    private var sortingAlgorithm: SortingAlgorithm<Element>? = null

    val algorithmList = listOf(
        QUICK_SORT_DISPLAY_INFO,
        MERGE_SORT_DISPLAY_INFO,
        INSERTION_SORT_DISPLAY_INFO,
        TIM_SORT_DISPLAY_INFO
    )

    init {
        init(QuickSort(ElementSwap()))
    }

    private fun init(sortingAlgorithm: SortingAlgorithm<Element>) {
        this.sortingAlgorithm = sortingAlgorithm
        _algorithmName.value = this.sortingAlgorithm?.displayInfo
        _array.clear()
        val temp = (1..NUM_DATA).toMutableList()
        temp.shuffle()
        temp.forEach {
            _array.add(Element(value = it))
        }
    }

    val onPlayButtonClickListener: () -> Unit = {
        viewModelScope.launch(Dispatchers.IO) {
            sortingAlgorithm?.sort(_array, 0, NUM_DATA - 1)
        }
    }

    val onAlgorithmClickListener: (String) -> Unit = {
        when(it) {
            QUICK_SORT_DISPLAY_INFO -> init(QuickSort(ElementSwap()))
            MERGE_SORT_DISPLAY_INFO -> init(MergeSort(ElementInsert()))
            INSERTION_SORT_DISPLAY_INFO -> init(InsertionSort(ElementInsert()))
            TIM_SORT_DISPLAY_INFO -> init(TimSort(ElementInsert()))
        }
    }
}