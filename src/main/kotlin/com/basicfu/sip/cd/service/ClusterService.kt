package com.basicfu.sip.cd.service

import com.basicfu.sip.cd.common.Enum
import com.basicfu.sip.cd.mapper.ClusterMapper
import com.basicfu.sip.cd.mapper.HelmMapper
import com.basicfu.sip.cd.model.dto.ClusterDto
import com.basicfu.sip.cd.model.po.Cluster
import com.basicfu.sip.cd.model.po.Helm
import com.basicfu.sip.cd.model.vo.ClusterVo
import com.basicfu.sip.core.common.example
import com.basicfu.sip.core.common.exception.CustomException
import com.basicfu.sip.core.common.generate
import com.basicfu.sip.core.service.BaseService
import com.github.pagehelper.PageInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author basicfu
 * @date 2019/5/31
 */
@Service
class ClusterService : BaseService<ClusterMapper, Cluster>() {
    @Autowired
    lateinit var helmMapper: HelmMapper

    fun list(vo: ClusterVo): PageInfo<ClusterDto> {
        return selectPage()
    }

    fun insert(vo: ClusterVo): Int {
        if (mapper.selectCount(generate {
                name = vo.name
            }) != 0) throw CustomException(Enum.EXIST_NAME)
        val po = dealInsert(to<Cluster>(vo))
        return mapper.insertSelective(po)
    }

    fun update(vo: ClusterVo): Int {
        val checkName = mapper.selectOne(generate {
            name = vo.name
        })
        if (checkName != null && checkName.id != vo.id) throw CustomException(Enum.EXIST_NAME)
        val po = dealUpdate(to<Cluster>(vo))
        return mapper.updateByPrimaryKeySelective(po)
    }

    fun delete(ids: List<Long>): Int {
        if (ids.isNotEmpty()) {
            helmMapper.deleteByExample(example<Helm> {
                andIn(Helm::clusterId, ids)
            })
        }
        return deleteByIds(ids)
    }
}
