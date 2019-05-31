package com.basicfu.sip.cd.model.po

import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

class Helm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @Column(name = "cluster_id")
    var clusterId: Long? = null
    var name: String? = null
    var namespace: String? = null
    var version: String? = null
    var template: String? = null
    @Column(name = "create_time")
    var createTime: Long? = null
    @Column(name = "update_time")
    var updateTime: Long? = null
}
