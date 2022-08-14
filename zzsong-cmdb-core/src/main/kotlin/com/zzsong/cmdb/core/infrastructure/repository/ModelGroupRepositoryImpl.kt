package com.zzsong.cmdb.core.infrastructure.repository

import com.zzsong.cmdb.core.domain.model.model.ModelGroupDo
import com.zzsong.cmdb.core.domain.model.model.repository.ModelGroupRepository
import com.zzsong.cmdb.core.infrastructure.CmdbIDGenerator
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

/**
 * @author 宋志宗 on 2022/8/14
 */
@Repository
class ModelGroupRepositoryImpl(
  private val idGenerator: CmdbIDGenerator,
  private val mongoTemplate: ReactiveMongoTemplate
) : ModelGroupRepository {

  override suspend fun save(modelGroupDo: ModelGroupDo): ModelGroupDo {
    if (modelGroupDo.id < 1) {
      modelGroupDo.id = idGenerator.generate()
    }
    return mongoTemplate.save(modelGroupDo).awaitSingle()
  }

  override suspend fun findById(id: Long): ModelGroupDo? {
    val criteria = Criteria.where("id").`is`(id)
    val query = Query.query(criteria)
    return mongoTemplate.findOne(query, ModelGroupDo::class.java).awaitSingleOrNull()
  }
}
