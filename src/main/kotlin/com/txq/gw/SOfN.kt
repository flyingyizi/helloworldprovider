package com.txq.gw

import java.util.Random

// https://rosettacode.org/wiki/Knuth%27s_algorithm_S#Kotlin
/**
 * Knuth's algorithm S
   This is a method of randomly sampling n items from a set of M items, with equal probability; where M >= n and M,
   the number of items is unknown until the end. This means that the equal probability sampling should be maintained for
   all successive items > n as they become available (although the content of successive samples can change).
 * The algorithm
   Select the first n items as the sample as they become available;
   For the i-th item where i > n, have a random chance of n/i of keeping it. If failing this chance, the sample remains
   the same. If not, have it randomly (1/n) replace one of the previously selected n items of the sample.
   Repeat #2 for any subsequent items.
 * The Art of Computer Programming, Vol 2, 3.4.2 p.142
 */

class SOfN<T>(val n: Int) {
    private val sample = ArrayList<T>(n)
    private var i = 0

    private companion object {
        val rand = Random()
    }

    fun process(item: T): ArrayList<T> {
        if (++i <= n)
            sample.add(item)
        else if (rand.nextInt(i) < n)
            sample[rand.nextInt(n)] = item
        return sample
    }
}

fun main(args: Array<String>) {
    val bin = IntArray(10)
    (1..100_000).forEach {
        val sOfn = SOfN<Int>(3)
        var sample: ArrayList<Int>? = null
        for (i in 0..9) sample = sOfn.process(i)
        for (s in sample!!) bin[s]++
    }
    println(bin.contentToString())
}