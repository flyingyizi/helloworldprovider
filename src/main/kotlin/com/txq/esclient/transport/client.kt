package com.txq.esclient.transport

import org.elasticsearch.action.admin.cluster.state.ClusterStateRequest
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsRequest
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.transport.TransportAddress
import org.elasticsearch.transport.client.PreBuiltTransportClient
import java.net.InetAddress

class ESTransport(val clusterName: String = "es-forclient",
                  val clusterAddrs : String = "localhost",  //集群服务IP集合,逗号分隔
                  val port: Int? = null//ES集群端口
                          ) {
    companion object {
        private  var client: TransportClient? = null
    }

    fun initESClient() {
        if (client != null) {
            return
        }

        if (clusterAddrs == null || "" == clusterAddrs.trim { it <= ' ' }) {
            return
        }

        try {
                val settings =Settings.builder()
                    .put("cluster name", clusterName) // 集群名
                    .put("client.transport.sniff",false)  //如果为true：同一内网Ip段，嗅的方式自己查找，组成集群。
                    .put("client.transport.ping_timeout","10s")
                    .put("client.transport.ignore_cluster_name",true)
                    .build()

                // 自动把集群下的机器添加到列表中
                client = PreBuiltTransportClient(settings)
                val esIps = clusterAddrs.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (ip in esIps) {//添加集群IP列表
                    val transportAddress = TransportAddress(InetAddress.getByName(ip), 9300)
                    client!!.addTransportAddresses(transportAddress)
                }
        } catch (e: Exception) {
            e.printStackTrace()
            client?.close()
            client = null
        }
    }

    fun  sample() {
        initESClient()
        val admin = client?.admin()?.cluster()
        val x = ClusterStatsRequest()
        val resp=admin?.clusterStats( x )?.actionGet()
        println(resp?.status?.name)
    }


}

