package com.txq.tree

import java.util.HashMap
import java.io.Serializable


//https://www.jianshu.com/p/e54047b2b563

class ObjTree<T> {//: Serializable {

    private var branches: MutableMap<Any, ObjTree<T>>? = null

    var obj: T? = null

    /**
     * status 此字的状态1，继续 2，是个词语但是还可以继续 ,3确定 nature 词语性质
     */
    var status: Int = 0

    private fun getOrCreateObjTree(obj: Any): ObjTree<T> {
        if (this.branches == null) {
            this.branches = HashMap()
        }

        var objTree= this.branches!![obj]
        if (objTree == null) {
            objTree = ObjTree()
            this.branches!![obj] = objTree
        }
        return objTree
    }


    /**
     * 增加对象数组到树种
     *
     * @param objs
     */
    fun add(t: T, vararg objs: Any) {
        var objTree = this
        val len = objs.size
        for (i in 0 until len) {
            objTree = objTree.getOrCreateObjTree(objs[i])
            if (objTree.status == 3) {
                objTree.status = 2
            } else if (objTree.status == 0) {
                objTree.status = 1
            }
        }
        objTree.obj = t

        if (objTree.status == 1) {
            objTree.status = 2
        } else if (objTree.status == 0) {
            objTree.status = 3
        }
    }


    /**
     * 取得当前分支下所有的截止值
     *
     * @param objs
     */
    operator fun get(vararg objs: Any): T? {
        val objTree = getObjTree(*objs) ?: return null
        return objTree.obj
    }

    /**
     * 取得当然分支的节点
     *
     * @param objs
     * @return
     */
    fun getObjTree(vararg objs: Any): ObjTree<T>? {
        var objTree: ObjTree<*>? = this
        for (i in objs.indices) {
            objTree = objTree!!.getObjTree(objs[i])
            if (objTree == null) {
                return null
            }
        }
        return objTree  as ObjTree<T>?


    }

    /**
     * 判断一个路径是否存在.且是截止
     *
     * @param objs
     * @return
     */
    fun contains(vararg objs: Any): Boolean {
        var objTree: ObjTree<*>? = this
        for (i in objs.indices) {
            objTree = objTree!!.getObjTree(objs[i])
            if (objTree == null) {
                return false
            }
        }

        return if (objTree!!.status > 1) {
            true
        } else {
            false
        }
    }

    private fun getObjTree(obj: Any): ObjTree<*>? {
        return if (branches == null) {
            null
        } else branches!![obj]
    }



    fun getBranches(): Map<Any, ObjTree<T>>? {
        return branches
    }
}


    fun main(args: Array<String>) {
        System.gc()
        val total = Runtime.getRuntime().totalMemory() // byte
        val m1 = Runtime.getRuntime().freeMemory()
        println("before:" + (total - m1))

        val map = HashMap<Any, Any>()
        for (i in 0..99) {
            map[Any()] = Any()
        }
        val total1 = Runtime.getRuntime().totalMemory()
        val m2 = Runtime.getRuntime().freeMemory()
        println("after:" + (total1 - m2))
        println(map.toString())
    }

