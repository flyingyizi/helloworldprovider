package com.txq.esclient.restful

import  org.elasticsearch.client.RestClient
import  org.elasticsearch.client.RestClientBuilder
import org.apache.http.client.config.RequestConfig
import org.apache.http.HttpHost
import org.apache.http.entity.ContentType
import org.apache.http.nio.entity.NStringEntity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

class ESJavaRestClient {
    companion object {
        @JvmStatic fun  getESClient() :RestClient? {
            val restClient:RestClient
            try {
                val requestConfigCallback = RestClientBuilder.RequestConfigCallback { requestConfigBuilder ->
                    requestConfigBuilder.setContentCompressionEnabled(true)
                            .setConnectTimeout(5000)
                            .setSocketTimeout(30000)
                    return@RequestConfigCallback requestConfigBuilder
                }
                val b = RestClientBuilder.HttpClientConfigCallback { httpClientBuilder ->
                    httpClientBuilder.setMaxConnPerRoute(100)
                            .setMaxConnTotal(200)
                    return@HttpClientConfigCallback httpClientBuilder
                }

                val httpHost = HttpHost("localhost", 9200)
                restClient = RestClient.builder(httpHost)
                        .setRequestConfigCallback(requestConfigCallback)
                        .setHttpClientConfigCallback(b)
                        .build()
                return restClient
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }
}

fun main(args: Array<String>) {
    try {
        val restClient = ESJavaRestClient.getESClient()
        restClient?.let { bulkSample(restClient)  }
    }catch(e: Exception) {
        e.printStackTrace()
    }
}

fun bulkSample(restClient:RestClient) {
    try {
        val dataJson = """
            >{"first_name" : "jJohn",
            >"last_name" : "Smith",
            >"age" : 25,
            >"about" : "I love to go rock climbing",
            >"interests": [ "sports", "music" ]}
            """.trimMargin(">").replace("\n","")

        val index = "megacorp"
        val type = "employee"
        val actionMetaData = """
            >{"index":{"_index":"${index}","_type":"${type}" }}
            """.trimMargin(">").replace("\n","")
        val bulkData=ArrayList<String>()
        bulkData.add(dataJson)

        val bulkRequestBody = StringBuilder()
        for (s in bulkData) {
            bulkRequestBody
                    .append(actionMetaData)
                    .append("\n")
                    .append(s)
                    .append("\n")
        }

        val httpEntity = NStringEntity(bulkRequestBody.toString(), ContentType.APPLICATION_JSON)
        val indexResponse = restClient.performRequest("POST","/_bulk", Collections.emptyMap(),httpEntity)

        val reader = BufferedReader( InputStreamReader( indexResponse?.getEntity()?.content ))

        val stringBuffer = StringBuffer()
        reader.forEachLine {
            stringBuffer.append(it)
        }
        val responseJson = stringBuffer.toString()
        println(responseJson)
    }catch(e: Exception) {
        e.printStackTrace()
    }
}
