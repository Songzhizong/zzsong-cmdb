package com.zzsong.cmdb.core.infrastructure

import com.zzsong.framework.core.id.IDGenerator
import com.zzsong.framework.core.id.IDGeneratorFactory
import org.springframework.stereotype.Component

/**
 * @author 宋志宗 on 2022/8/14
 */
@Component
class CmdbIDGenerator(idGeneratorFactory: IDGeneratorFactory) : IDGenerator {
  private val idGenerator: IDGenerator

  init {
    idGenerator = idGeneratorFactory.getGenerator("cmdb")
  }

  override fun generate() = idGenerator.generate()
}
