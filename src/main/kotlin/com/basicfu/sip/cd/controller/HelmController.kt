package com.basicfu.sip.cd.controller

import com.basicfu.sip.cd.model.vo.HelmVo
import com.basicfu.sip.cd.service.HelmService
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
@RequestMapping("/helm")
class HelmController {
    @Autowired
    lateinit var helmService: HelmService

    @GetMapping("/list")
    fun list(vo: HelmVo): Result<Any> {
        return Result.success(helmService.list(vo))
    }

    @PostMapping("/insert")
    fun insert(@RequestBody @Validated(Insert::class) vo: HelmVo): Result<Any> {
        return Result.success(helmService.insert(vo))
    }

    @PostMapping("/update")
    fun update(@RequestBody @Validated(Update::class) vo: HelmVo): Result<Any> {
        return Result.success(helmService.update(vo))
    }

    @DeleteMapping("/delete")
    fun delete(@RequestBody ids: List<Long>): Result<Any> {
        return Result.success(helmService.delete(ids))
    }
}
