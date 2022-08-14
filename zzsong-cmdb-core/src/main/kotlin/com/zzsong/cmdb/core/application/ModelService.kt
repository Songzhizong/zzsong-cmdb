package com.zzsong.cmdb.core.application

import com.zzsong.cmdb.core.domain.model.model.ModelDo
import com.zzsong.cmdb.core.domain.model.model.repository.ModelGroupRepository
import com.zzsong.cmdb.core.domain.model.model.repository.ModelRepository
import com.zzsong.cmdb.core.dto.args.CreateModelArgs
import com.zzsong.framework.core.event.ReactiveTransactionalEventPublisher
import com.zzsong.framework.core.exception.ResourceNotFoundException
import com.zzsong.framework.core.kotlin.publishAndAwait
import com.zzsong.framework.core.kotlin.requireNonnull
import com.zzsong.framework.core.kotlin.requireNotBlank
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * @author 宋志宗 on 2022/8/13
 */
@Service
class ModelService(
  private val modelRepository: ModelRepository,
  private val modelGroupRepository: ModelGroupRepository,
  private val transactionalEventPublisher: ReactiveTransactionalEventPublisher
) {
  companion object {
    private val log: Logger = LoggerFactory.getLogger(ModelService::class.java)
  }

  suspend fun create(args: CreateModelArgs): ModelDo {
    val groupId = args.groupId.requireNonnull { "模型分组为空" }
      .also { mid ->
        modelGroupRepository.findById(mid) ?: let {
          log.info("新增模型失败, 模型分组 {} 不存在", mid)
          throw ResourceNotFoundException("新增模型失败, 模型分组不存在")
        }
      }
    val ident = args.ident.requireNotBlank { "模型标识为空" }
    val name = args.name.requireNotBlank { "模型名称为空" }
    val note = args.note
    val tuple = ModelDo.create(groupId, ident, name, note)
    val modelDo = modelRepository.save(tuple.value)
    transactionalEventPublisher.publishAndAwait(tuple)
    return modelDo
  }

}
