package com.txq.gw
/*
import org.apache.servicecomb.foundation.common.utils.BeanUtils
import org.apache.servicecomb.foundation.common.utils.Log4jUtils
*/
import io.servicecomb.foundation.common.utils.BeanUtils
import io.servicecomb.foundation.common.utils.Log4jUtils
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.ElementType

object MainServer {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        Log4jUtils.init()        // 日志初始化
        BeanUtils.init()         // Spring bean初始化
    }
}

annotation class TestAnnotation(val id: Int = -1, val msg: String = "Hi")

@TestAnnotation
object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        val hasAnnotation = Test::class.java.isAnnotationPresent(TestAnnotation::class.java)
        if (hasAnnotation) {
            val testAnnotation = Test::class.java.getAnnotation(TestAnnotation::class.java)
            println("id:" + testAnnotation.id)
            println("msg:" + testAnnotation.msg)
        }
    }
}




