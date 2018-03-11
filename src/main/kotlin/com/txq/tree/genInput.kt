package com.txq.tree


import java.util.UUID


class UUIDTool {
    companion object {
        /**
         * 自动生成32位的UUid，对应数据库的主键id进行插入用。
         * @return
         */
        /*UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        // 去掉"-"符号
        String temp = str.substring(0, 8) + str.substring(9, 13)
                + str.substring(14, 18) + str.substring(19, 23)
                + str.substring(24);
        return temp;*/
        val uuid: String  get() = UUID.randomUUID().toString().replace("-", "")


        @JvmStatic
        fun main(args: Array<String>) {
            //      String[] ss = getUUID(10);
            for (i in 0..9) {
                println("ss[$i]=====$uuid")
            }
        }
    }
}