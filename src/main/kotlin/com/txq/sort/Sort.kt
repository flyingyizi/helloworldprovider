package com.txq.com.txq.sort

//https://rosettacode.org/wiki/Category:Sorting_Algorithms

import java.util.Comparator
import java.util.Arrays

private val COMPARATOR = Comparator<Int> { o1, o2 ->
    when  {
        o1 < o2 -> -1
        o1 > o2 -> 1
        else ->    0
    }
}

fun main(args: Array<String>) {
    val list = arrayOf<Int>(2, 14, 4, 6, 8, 1, 3, 5, 7, 11, 0, 13, 12, -1,2, 14, 4, 6, 8, 1, 3, 5, 7, 11, 0, 13, 12, -1)
    println( "Original:\n${list.asList()}" )
    /**
     * 计算两个时间点直接逝去的毫秒数
     *
     */
    val start = System.currentTimeMillis()
    //BubbleSort< Int > (list, COMPARATOR)
    //CircleSort< Int > (list, COMPARATOR)
    //HeapSort< Int > (list, COMPARATOR)
    QuickSort<Int>(list, COMPARATOR)

    val end = System.currentTimeMillis()

    println( "Sorted  :\n${list.asList()}. consumed ${(end - start)} ms. " )
}

private fun <T> Array<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // “this”对应该列表
    this[index1] = this[index2]
    this[index2] = tmp
}

fun <T> BubbleSort(a: Array<T>, c: Comparator<T>) {
    var changed: Boolean
    do {
        changed = false
        for (i in 0..a.size - 2) {
            if (c.compare(a[i], a[i + 1]) > 0) {
                a.swap( i, i + 1)
                changed = true
            }
        }
    } while (changed)
}

//https://rosettacode.org/wiki/Sorting_Algorithms/Circle_Sort#Java
fun<T> CircleSort( a: Array<T>, c: Comparator<T> ) {
    if (a.size > 0)
        do {
            println(Arrays.toString(a))
        } while (circleSortR(a, 0, a.size - 1, 0, c) != 0)
}
private fun<T> circleSortR(arr: Array<T>, lo: Int, hi: Int, numSwaps: Int,  c: Comparator<T> ): Int {
    var lo = lo
    var hi = hi
    var swaped = numSwaps
    if (lo == hi)
        return swaped

    val high = hi
    val low = lo
    val mid = (hi - lo) / 2

    while (lo < hi  ) {
        if ( c.compare(arr[lo], arr[hi])>0 ) {
            arr.swap( lo, hi)
            swaped++
        }
        lo++
        hi--
    }
    if (lo == hi && c.compare(arr[lo], arr[hi + 1])>0  ) {
        arr.swap( lo, hi + 1)
        swaped++
    }
    swaped = circleSortR(arr, low, low + mid, swaped, c)
    swaped = circleSortR(arr, low + mid + 1, high, swaped, c)

    return swaped
}


fun<T> HeapSort(a: Array<T>, c: Comparator<T> ) {
    // 建堆
    heapify(a, c)

    // 堆排序
    var end = a.size - 1
    while (end > 0) {
        val temp = a[end]
        a[end] = a[0]
        a[0] = temp
        end--
        siftDown(a, 0, end, c)
    }
}
private fun<T> heapify(a: Array<T>, c: Comparator<T> ) {
/*
数组存放二叉堆:
            root
             /\
           /   \
         left   right
index 关系(下标从0开始):
         left = root*2+ 1
         right = left + 1
*/
    //从右往左，处理每一个二叉堆
    var root = (a.size - 2) / 2
    while (root >= 0) {
        siftDown(a, root, a.size - 1, c)
        root--
    }
}

private fun<T> siftDown(a: Array<T>, start: Int, end: Int, c: Comparator<T> ) {
    var root = start
    while (root * 2 + 1 <= end) {
        var child = root * 2 + 1
        if (child + 1 <= end && c.compare(a[child], a[child+1])<0 ) child++
        if ( c.compare(a[root], a[child])<0 ) {
            a.swap(root,child)
            root = child
        }
        else return
    }
}

fun<T> QuickSort(array: Array<T>, c: Comparator<T>) {
    subQuickSort(array, 0, array.size - 1, c)
}

private fun<T> subQuickSort(a: Array<T>?, start: Int, end: Int, c: Comparator<T>) {
    if (a == null || end - start + 1 < 2) {
        return
    }
    val part = partition(a, start, end, c)

    if (part == start) {
        subQuickSort(a, part + 1, end, c)
    } else if (part == end) {
        subQuickSort(a, start, part - 1, c)
    } else {
        subQuickSort(a, start, part - 1, c)
        subQuickSort(a, part + 1, end, c)
    }
}
private fun<T> partition(a: Array<T>, start: Int, end: Int, c: Comparator<T>): Int {
    val value = a[end]
    var index = start - 1

    for (i in start until end) {
        if ( c.compare(a[i], value)<0 ) {
            index++
            if (index != i) {
                a.swap(index,i)
            }
        }
    }
    if (index + 1 != end) {
        a.swap(index+1,end)
    }
    return index + 1
}


