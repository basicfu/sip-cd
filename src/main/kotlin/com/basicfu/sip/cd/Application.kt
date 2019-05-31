package com.basicfu.sip.cd

import com.basicfu.sip.core.annotation.EnableSipCore
import org.springframework.boot.runApplication
import org.springframework.cloud.client.SpringCloudApplication
import tk.mybatis.spring.annotation.MapperScan

@MapperScan(basePackages = ["com.basicfu.sip.cd.mapper"])
@EnableSipCore
@SpringCloudApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
