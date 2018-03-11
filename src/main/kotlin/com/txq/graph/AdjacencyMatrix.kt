package com.txq.graph

// https://www.programiz.com/dsa/graph-adjacency-matrix
data class AdjacencyMatrix (val numVertices: Int){
    val adjMatrix: Array<IntArray> = Array(numVertices) { IntArray(numVertices) }
    fun addEdge(i: Int, j: Int, w:Int=1) {
        adjMatrix[i][j] = w
    }

    fun removeEdge(i: Int, j: Int) {
        adjMatrix[i][j] = 0
    }

    fun clean() {
        for (i in 0..numVertices) {
            for (j in 0..numVertices) {
                adjMatrix[i][j] = 0
            }
        }
    }
    fun isEdge(i: Int, j: Int): Boolean {
        return adjMatrix[i][j] > 0
    }

    override fun toString(): String {
        val s = StringBuilder()
        for (i in 0 until numVertices) {
            s.append(i.toString() + ": ")
            for (j in adjMatrix[i]) {
                s.append((if (j>0) 1 else 0).toString() + " ")
            }
            s.append("\n")
        }
        return s.toString()
    }
}
/*
fun main(args: Array<String>) {
    val g = AdjacencyMatrix(4)

    g.addEdge(0, 1)
    g.addEdge(0, 2)
    g.addEdge(1, 2)
    g.addEdge(2, 0)
    g.addEdge(2, 3)

    print(g.toString())
    /* Outputs
   0: 0 1 1 0
   1: 1 0 1 0
   2: 1 1 0 1
   3: 0 0 1 0
  */
}
*/