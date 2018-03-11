package com.txq.tree

//BST: binary search tree  二叉搜索树，二叉查找树
class BST <E> {
    private var root: Node<E>? = null

    private class Node<E>(var key: E, var parent: Node<E>?) {
        var left: Node<E>? = null
        var right: Node<E>? = null
    }

    fun insert(key: E): Boolean {
        if (root == null)
            root = Node<E>(key, null)
        else {
            var n: Node<E>? = root
            var parent: Node<E>
            while (true) {
                if (n!!.key == key) return false
                parent = n
                //val goLeft = n.key > key
                val goLeft = if ((n.key as Comparable<E>).compareTo(key) > 0) {
                    true
                } else {
                    false
                }

                n = if (goLeft) n.left else n.right
                if (n == null) {
                    if (goLeft)
                        parent.left = Node(key, parent)
                    else
                        parent.right = Node(key, parent)
                    break
                }
            }
        }
        return true
    }

    fun delete(delKey: E) {
        if (root == null) return
        var n: Node<E>? = root
        var parent: Node<E>? = root
        var delNode: Node<E>? = null
        var child: Node<E>? = root
        while (child != null) {
            parent = n
            n = child
            child = /*if (delKey >= n.key) */if ((delKey as Comparable<E>).compareTo(n.key) >= 0) n.right else n.left
            if (delKey == n.key) delNode = n
        }
        if (delNode != null) {
            delNode.key = n!!.key
            child = if (n.left != null) n.left else n.right
            if (root!!.key == delKey)
                root = child
            else {
                if (parent!!.left == n)
                    parent.left = child
                else
                    parent.right = child
            }
        }
    }


    private fun height(n: Node<E>?): Int {
        if (n == null) return -1
        return 1 + Math.max(height(n.left), height(n.right))
    }


    fun printKey() {
        printKey(root)
        println()
    }

    private fun printKey(n: Node<E>?) {
        if (n != null) {
            printKey(n.left)
            print("${n.key} ")
            printKey(n.right)
        }
    }
}

fun main(args: Array<String>) {
    val tree = BST<Int>()
    println("Inserting values 1 to 10")
    for (i in 10 downTo 1 step 1) tree.insert(i)
    print("Printing key     : ")
    tree.printKey()
}