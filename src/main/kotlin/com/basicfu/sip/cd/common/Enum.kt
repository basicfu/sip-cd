package com.basicfu.sip.cd.common

/**
 * @author basicfu
 * @date 2018/8/16
 */
@Suppress("UNUSED_PARAMETER", "unused")
enum class Enum constructor(val value: Int, val msg:String) {
    SUCCESS(0,"成功"),
    ILLEGAL_REQUEST(6,"非法请求"),
    EXIST_NAME(1,"已存在的名字");

}
