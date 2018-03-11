package com.txq.etcd

import mousio.etcd4j.EtcdClient
import com.oracle.util.Checksums.update
import com.sun.jndi.ldap.LdapPoolManager.expire
import mousio.etcd4j.responses.EtcdKeysResponse
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import mousio.etcd4j.responses.EtcdKeyAction
import java.util.*


val deferred = (1..1_000_000).map { n ->
    async {
        n
    }
}
val x=runBlocking {
    val sum = deferred.sumBy { it.await() }
    println("Sum: $sum")
}

suspend fun workload(n: Int): Int {
    delay(1000)
    return n
}

fun main(args :Array<String>) {
    async {
        workload(10)
    }
}

