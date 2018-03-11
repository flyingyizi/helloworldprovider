package com.txq.tree

//AVL: 所有子树的平衡因子绝对值都不大于1，只可能是-1，0.1
class AvlTree<E> {
    private var root: Node<E>? = null

    private class Node<E>(var key: E, var parent: Node<E>?) {
        var balance: Int = 0
        var left : Node<E>? = null
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
                val goLeft =if ( (n.key as Comparable<E>).compareTo(key) > 0 ) { true } else { false }

                n = if (goLeft) n.left else n.right
                if (n == null) {
                    if (goLeft)
                        parent.left  = Node(key, parent)
                    else
                        parent.right = Node(key, parent)
                    rebalance(parent)
                    break
                }
            }
        }
        return true
    }

    fun delete(delKey: E) {
        if (root == null) return
        var n:       Node<E>? = root
        var parent:  Node<E>? = root
        var delNode: Node<E>? = null
        var child:   Node<E>? = root
        while (child != null) {
            parent = n
            n = child
            child = /*if (delKey >= n.key) */if ( (delKey as Comparable<E>).compareTo(n.key) >= 0 )     n.right else n.left
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
                rebalance(parent)
            }
        }
    }

    private fun rebalance(n: Node<E>) {
        setBalance(n)
        var nn = n
        if (nn.balance == -2)
            if (height(nn.left!!.left) >= height(nn.left!!.right))
                nn = rotateRight(nn)
            else
                nn = rotateLeftThenRight(nn)
        else if (nn.balance == 2)
            if (height(nn.right!!.right) >= height(nn.right!!.left))
                nn = rotateLeft(nn)
            else
                nn = rotateRightThenLeft(nn)
        if (nn.parent != null) rebalance(nn.parent!!)
        else root = nn
    }

    private fun rotateLeft(a: Node<E>): Node<E> {
        val b: Node<E>? = a.right
        b!!.parent = a.parent
        a.right = b.left
        if (a.right != null) a.right!!.parent = a
        b.left = a
        a.parent = b
        if (b.parent != null) {
            if (b.parent!!.right == a)
                b.parent!!.right = b
            else
                b.parent!!.left = b
        }
        setBalance(a, b)
        return b
    }

    private fun rotateRight(a: Node<E>): Node<E> {
        val b: Node<E>? = a.left
        b!!.parent = a.parent
        a.left = b.right
        if (a.left != null) a.left!!.parent = a
        b.right = a
        a.parent = b
        if (b.parent != null) {
            if (b.parent!!.right == a)
                b.parent!!.right = b
            else
                b.parent!!.left = b
        }
        setBalance(a, b)
        return b
    }

    private fun rotateLeftThenRight(n: Node<E>): Node<E> {
        n.left = rotateLeft(n.left!!)
        return rotateRight(n)
    }

    private fun rotateRightThenLeft(n: Node<E>): Node<E> {
        n.right = rotateRight(n.right!!)
        return rotateLeft(n)
    }

    private fun height(n: Node<E>?): Int {
        if (n == null) return -1
        return 1 + Math.max(height(n.left), height(n.right))
    }

    private fun setBalance(vararg nodes: Node<E>) {
        for (n in nodes) n.balance = height(n.right) - height(n.left)
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

    fun printBalance() {
        printBalance(root)
        println()
    }

    private fun printBalance(n: Node<E>?) {
        if (n != null) {
            printBalance(n.left)
            print("${n.balance} ")
            printBalance(n.right)
        }
    }
}

fun main(args: Array<String>) {
    val tree = AvlTree<Int>()
    println("Inserting values 1 to 10")
    for (i in 1..10) tree.insert(i)
    print("Printing key     : ")
    tree.printKey()
    print("Printing balance : ")
    tree.printBalance()
}