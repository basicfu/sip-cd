package com.basicfu.sip.cd.controller

import com.basicfu.sip.cd.model.vo.ClusterVo
import com.basicfu.sip.cd.service.ClusterService
import com.basicfu.sip.core.annotation.Insert
import com.basicfu.sip.core.annotation.Update
import com.basicfu.sip.core.model.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * @author basicfu
 * @date 2019/5/31
 */
@RestController
@RequestMapping("/cluster")
class ClusterController {
    @Autowired
    lateinit var clusterService: ClusterService

    @GetMapping("/list")
    fun list(vo: ClusterVo): Result<Any> {
        return Result.success(clusterService.list(vo))
    }

    @PostMapping("/insert")
    fun insert(@RequestBody @Validated(Insert::class) vo: ClusterVo): Result<Any> {
        return Result.success(clusterService.insert(vo))
    }

    @PostMapping("/update")
    fun update(@RequestBody @Validated(Update::class) vo: ClusterVo): Result<Any> {
        return Result.success(clusterService.update(vo))
    }

    @DeleteMapping("/delete")
    fun delete(@RequestBody ids: List<Long>): Result<Any> {
        return Result.success(clusterService.delete(ids))
    }
}
