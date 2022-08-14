package com.zzsong.cmdb.core.domain.model.model.repository

import com.zzsong.cmdb.core.domain.model.model.ModelGroupDo

/**
 * @author 宋志宗 on 2022/8/14
 */
interface ModelGroupRepository {

  suspend fun save(modelGroupDo: ModelGroupDo): ModelGroupDo

  suspend fun findById(id: Long): ModelGroupDo?
}
