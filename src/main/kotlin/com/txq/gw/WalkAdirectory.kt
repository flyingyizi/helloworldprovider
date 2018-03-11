package com.txq.gw


import java.io.File

/**
 * Task
   Walk a given directory tree and print files matching a given pattern.
   Note: This task is for recursive methods.   These tasks should read an entire directory tree, not a single directory.
 */
fun walkDirectoryRecursively(dirPath: String, pattern: Regex): Sequence<String> {
    val d = File(dirPath)
    require (d.exists() && d.isDirectory())
    return d.walk().map { it.name }.filter { it.matches(pattern) }.sorted().distinct() }

fun main(args: Array<String>) {
    //val r = Regex("""^v(a|f).*\.h$""")  // get all C header files beginning with 'va' or 'vf'
    val r = Regex(""".*\.h$""")
    require( args.size == 1 ) {"请指定路径"}

    //val files = walkDirectoryRecursively("/usr/include", r)
    val files = walkDirectoryRecursively( args[0], r)
    for (file in files) println(file)
}
