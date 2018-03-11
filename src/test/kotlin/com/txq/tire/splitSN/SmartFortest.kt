package com.txq.tire.splitSN


import org.junit.Test
import com.txq.tire.SmartForest

class SmartFortestTest {
    @Test
    fun test() {
        /**
         * 词典的构造.一行一个词后面是参数.可以从文件读取.可以是read流.
         */
        val start = System.currentTimeMillis()
        val forest = SmartForest<Int>()

        forest.add("中国", 11)
        forest.add("中国人", 12)
        forest.add("java", 21)
        forest.add("javaandroid", 22)

        var content = " Android-java-中国人"
        //forest.remove("中国人")
/*
        content = StringUtil.rmHtmlTag(content)

        for (i in 0..0) {
            val udg = forest.getWord(content.toLowerCase().toCharArray())

            var temp: String
            while ((temp = udg.getFrontWords()) != null) {
                println(temp + "\t" + udg.getParam())
            }
        }
        println(System.currentTimeMillis() - start)
*/
        println(forest.getBranch("java")?.param)
        println(forest.getBranch("中国")?.param)
        println(forest.getBranch("中国人")?.param)
        println(System.currentTimeMillis() - start)
    }
}
