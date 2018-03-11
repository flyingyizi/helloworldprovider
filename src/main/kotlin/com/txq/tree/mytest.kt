package com.txq.tree


import com.txq.tire.SmartForest
import java.util.*
import io.netty.util.internal.PlatformDependent.freeMemory
import java.io.*

import java.util.UUID
import java.util.Random


// 生成随机样本数据
// 生成随机样本数据，uuid代表SN，param代表homepage version
fun writeFile(size:Int,outfile:String="\\src\\testfile.txt") {
    val file = File(System.getProperty("user.dir"), outfile)
    val ra = Random()

    file.printWriter().use { out ->
        for ( i in 0 until size step 1) {
            val uuid = UUID.randomUUID().toString().replace("-", "").substring(0,10)
            val random = ra.nextInt(10000)+1
            out.println("${uuid} ,${random}")
        }
        // fixed for check
        out.println("hellobrowser, 12345")
    }
}

// 加入tree
fun readtoForest(forest: SmartForest<Int>, newparam:Boolean=false ,fromfile:String="\\src\\testfile.txt") {
    val file = File(System.getProperty("user.dir"), fromfile)

    val reader = Scanner(file.inputStream())
    var i=0
    val ra = Random()
    try {
        while(reader.hasNextLine()) {
            val strlist = reader.nextLine().split(",")
            if ( strlist.size == 2 ) {
                when (newparam){
                    false -> forest.add( strlist[0].trim(), strlist[1].trim().toInt() )
                    true ->  forest.add( strlist[0].trim(), ra.nextInt(10000)+1 )
                }

                i++
            }
 //           if(i>20000&&i%20000==0) {
 //               println("add num:${i}")
 //           }
        }
    }catch(e:IOException ){
        //logger.error(e);
    }finally {
            reader.close();
    }

/* 文件太大时，这种方式占用内存过多
    val bufferedReader = file.bufferedReader()
    bufferedReader.forEachLine {
        val strlist = it.split(",")
        if ( strlist.size == 2 ) {
            forest.add( strlist[0].trim(), strlist[1].trim().toInt() )
        }
    }
  */
}

fun dupAddToForest(forest: SmartForest<Int>,dups:Int) {
    readtoForest(forest)
    for (i in 1..dups) {
        readtoForest(forest,true)
        println("add times:${i+1}")
    }
}

// 查询keyword，检查耗时
fun queryKey(forest: SmartForest<Int>,fromfile:String="\\src\\testfile.txt") {
    val file = File(System.getProperty("user.dir"), fromfile)

    val reader = Scanner(file.inputStream())
    var i=0
    var start = System.currentTimeMillis()
    try {
        while(reader.hasNextLine()) {
            val strlist = reader.nextLine().split(",")
            if ( strlist.size == 2 ) {
                start = System.currentTimeMillis()
                val result = forest.getBranch(strlist[0].trim()/**/)?.param
                println("key:${strlist[0].trim()}query consumed: ${System.currentTimeMillis() - start} ms。 result SET size:${result?.size}")
            }
            //           if(i>20000&&i%20000==0) {
            //               println("add num:${i}")
            //           }
        }
    }catch(e:IOException ){
        //logger.error(e);
    }finally {
        reader.close();
    }
}



fun main(args : Array<String>) {

    var start = System.currentTimeMillis()
    val testnum = 10_000 // 发布策略中accts 排重后数量
    val homepagenum = 10_000 // homepage配置的数量
    writeFile(testnum)
    println("create test file consumed: ${System.currentTimeMillis() - start} ms. the file record num:${testnum} ")
    val run = Runtime.getRuntime()
    System.`in`.read()   // 暂停程序执行,因为要用jvisualvm.exe观察内存

    //词典的构造.一行一个词后面是参数.可以从文件读取.可以是read流.
    start = System.currentTimeMillis()
    val forest = SmartForest<Int>()
    //readtoForest(forest)
    dupAddToForest(forest,homepagenum)

    println("load file to forest consumed: ${System.currentTimeMillis() - start} ms")
    /*
    start = System.currentTimeMillis()
    // 检查query
    val result = forest.getBranch("hellobrowser")?.param
    println("query consumed: ${System.currentTimeMillis() - start} ms")
    println("result SET size:${result?.size}")
    */
    queryKey(forest)
}
