package com.txq.tire.splitSN


import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.txq.tree.ObjTree


class ObjTreeTest {
    @Test
    @Throws(Exception::class)
    fun test() {
        val tree = ObjTree<String>()

        tree.add("aa孙健", "中国", "佑佑", 123)
        tree.add("孙健", 11)
        tree.add("孙健", "中国", "佑佑", 321)
        tree.add("孙健", "中国", "蔡晴", 456)
        tree.add("孙健", "中国", "佑佑", "蔡晴", 789)


        println(tree["孙健"])
        println(tree["孙健", "中国", "佑佑"])
        println(tree["孙健", "中国", "佑佑", "蔡晴"])
        println(tree["孙健", "中国"])
        println(tree["孙健", "中国", "蔡晴"])
        println(tree["中国", "佑佑", "蔡晴", 789])
        println(tree["中国", "佑佑", 123])
    }
}