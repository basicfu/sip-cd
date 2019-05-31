package com.basicfu.sip.cd.service

import com.basicfu.sip.cd.common.Enum
import com.basicfu.sip.cd.mapper.HelmMapper
import com.basicfu.sip.cd.model.dto.ClusterDto
import com.basicfu.sip.cd.model.po.Helm
import com.basicfu.sip.cd.model.vo.HelmVo
import com.basicfu.sip.core.common.exception.CustomException
import com.basicfu.sip.core.common.generate
import com.basicfu.sip.core.service.BaseService
import com.github.pagehelper.PageInfo
import org.springframework.stereotype.Service

/**
 * @author basicfu
 * @date 2019/5/31
 */
@Service
class HelmService : BaseService<HelmMapper, Helm>() {

    fun list(vo: HelmVo): PageInfo<ClusterDto> {
        return selectPage()
    }

    fun insert(vo: HelmVo): Int {
        if (mapper.selectCount(generate {
                clusterId = vo.clusterId
                name = vo.name
            }) != 0) throw CustomException(Enum.EXIST_NAME)
        val po = dealInsert(to<Helm>(vo))
        return mapper.insertSelective(po)
    }

    fun update(vo: HelmVo): Int {
        val checkName = mapper.selectOne(generate {
            clusterId = vo.clusterId
            name = vo.name
        })
        if (checkName != null && checkName.id != vo.id) throw CustomException(Enum.EXIST_NAME)
        val po = dealUpdate(to<Helm>(vo))
        return mapper.updateByPrimaryKeySelective(po)
    }

    fun delete(ids: List<Long>): Int {
        return deleteByIds(ids)
    }
}
