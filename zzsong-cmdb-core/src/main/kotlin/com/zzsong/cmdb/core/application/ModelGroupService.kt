package com.zzsong.cmdb.core.application

import com.zzsong.cmdb.core.domain.model.model.ModelGroupDo
import com.zzsong.cmdb.core.domain.model.model.repository.ModelGroupRepository
import com.zzsong.cmdb.core.dto.args.CreateModelGroupArgs
import com.zzsong.framework.core.kotlin.requireNotBlank
import org.springframework.stereotype.Component

/**
 * @author 宋志宗 on 2022/8/14
 */
@Component
class ModelGroupService(private val modelGroupRepository: ModelGroupRepository) {

  suspend fun create(args: CreateModelGroupArgs): ModelGroupDo {
    val name = args.name.requireNotBlank { "分组名称为空" }
    val note = args.note
    val groupDo = ModelGroupDo.create(name, note)
    return modelGroupRepository.save(groupDo)
  }

}
