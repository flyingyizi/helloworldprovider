package com.txq.graph

import java.util.LinkedList

//https://www.programiz.com/dsa/graph-adjacency-list
class AdjacencyList(private val numVertices: Int) {
    private val adjLists: Array<LinkedList<Int>?>

    init {
        adjLists = arrayOfNulls< LinkedList<Int> >(numVertices)
        //Array(numVertices) { BooleanArray(numVertices) }
        for (i in 0 until numVertices)
            adjLists[i] = LinkedList()
    }

    fun addEdge(src: Int, dest: Int) {
        adjLists[src]?.add(dest)
    }

    companion object {
    }
}

fun main(args: Array<String>) {
    val g = AdjacencyList(4)

    g.addEdge(0, 1)
    g.addEdge(0, 2)
    g.addEdge(1, 2)
    g.addEdge(2, 3)
}
