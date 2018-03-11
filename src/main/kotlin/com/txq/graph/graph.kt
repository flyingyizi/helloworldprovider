package com.txq.graph


data class Graph (val numVertices: Int, val isDirection:Boolean=true) {
    val edage = AdjacencyMatrix(numVertices)
    val vertex =  arrayOfNulls<String>( numVertices)
    val isTrav =  arrayOfNulls<Boolean>( numVertices)

    //https://www.programiz.com/dsa/graph-dfs
    //Depth First Search
    fun DFS(start: String) {
        vertex.find { it==start }?.let { DFS(it) }
    }

    fun dfsR(start: Int) {
        isTrav[start] = true
        print(start.toString() + " ")
        edage.adjMatrix[start].forEachIndexed { index, i ->
            if ( edage.isEdge(start,index) ) {
                isTrav[index]?.let { if (!it) dfsR(index) } ?: dfsR(index)
            }
        }
    }
/*
* BFS pseudocode  Breadth First Search
create a queue Q
mark v as visited and put v into Q
while Q is non-empty
    remove the head u of Q
    mark and enqueue all (unvisited) neighbours of u
*/


    fun  CreateByConsloe(){
        var input :List<String>
        do {
            println("输入图中各顶点信息, 请输入${numVertices}个顶点，以空格分开：")
            input= readLine()!!.split(' ')
            if (input.size == numVertices) {
                for ( i in 0 until numVertices) {
                    vertex[i] = input[i]
                }
                break
            }
        } while (true)

        do {
            println("输入构成各边的顶点与权值. start end weight ：")
                val input = readLine()!!.split(' ')
            if(input.size!=3) break
            var ii = -1
            var jj=-1
            for ( i in 0 until numVertices) {
                if ( input[0] == vertex[i] ) ii=i
                if ( input[1] == vertex[i] ) jj=i
            }
            if (ii==-1 || jj==-1 ) {
                println("请输入正确的顶点 startV(${input[0]}), endV(${input[1]})不合法")
                continue
            }

            edage.addEdge(ii,jj,input[2].toInt() )
            if (!isDirection) edage.addEdge(jj,ii,input[2].toInt() )
        } while (true)
    }
    fun cleanEdage() {
        edage.clean()
    }

    override fun toString(): String {
        val s = StringBuilder()
        val title = StringBuilder()
        vertex.forEach { title.append(it + " ")  }
        println("   ${title}")
        for (i in 0 until numVertices) {
            s.append( vertex[i] + ": ")
            for (j in edage.adjMatrix[i]) {
                s.append( j/*(if (j>0) j else 0)*/.toString() + " ")
            }
            s.append("\n")
        }
        return s.toString()
    }

}

fun main(args: Array<String>) {
    //val g = AdjacencyMatrix(4)
    val g = Graph(4)
    g.CreateByConsloe()
    println(g.toString())

    var input :List<String>
    do {
        println("DFS 路径查找，输入图中起始顶点：")
        input= readLine()!!.split(' ')
        g.DFS(input[0])
    } while (true)

}
