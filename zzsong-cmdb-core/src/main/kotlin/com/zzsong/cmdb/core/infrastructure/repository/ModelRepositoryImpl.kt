package com.zzsong.cmdb.core.infrastructure.repository

import com.zzsong.cmdb.core.domain.model.model.ModelDo
import com.zzsong.cmdb.core.domain.model.model.repository.ModelRepository
import com.zzsong.cmdb.core.infrastructure.CmdbIDGenerator
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Repository

/**
 * @author 宋志宗 on 2022/8/14
 */
@Repository
class ModelRepositoryImpl(
  private val idGenerator: CmdbIDGenerator,
  private val mongoTemplate: ReactiveMongoTemplate
) : ModelRepository {

  override suspend fun save(modelDo: ModelDo): ModelDo {
    if (modelDo.id < 1) {
      modelDo.id = idGenerator.generate()
    }
    return mongoTemplate.save(modelDo).awaitSingle()
  }
}
