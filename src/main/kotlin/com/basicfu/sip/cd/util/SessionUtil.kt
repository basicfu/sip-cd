package com.basicfu.sip.cd.util

import com.basicfu.sip.client.util.UserUtil
import com.basicfu.sip.cd.model.biz.User

object SessionUtil {

    fun getCurrentUser(): User {
        return UserUtil.getCurrentUser()!!
    }

    fun getCurrentUserId(): Long {
        return UserUtil.getCurrentUser<User>()!!.id!!
    }
}
