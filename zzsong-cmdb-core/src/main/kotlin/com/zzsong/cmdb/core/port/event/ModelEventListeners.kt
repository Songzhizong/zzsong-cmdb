package com.zzsong.cmdb.core.port.event

import com.zzsong.cmdb.common.event.model.ModelCreated
import com.zzsong.framework.core.event.EventListenerManager
import com.zzsong.framework.core.kotlin.toJsonString
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author 宋志宗 on 2022/8/14
 */
@Configuration
class ModelEventListeners(
  private val eventListenerManager: EventListenerManager
) {
  companion object {
    private val log: Logger = LoggerFactory.getLogger(ModelEventListeners::class.java)
  }

  @Bean("cmdb.cmdbModelCreated.general")
  fun modelCreatedListener() = eventListenerManager.listen(
    "cmdb.cmdbModelCreated.general",
    ModelCreated.TOPIC,
    ModelCreated::class.java
  ) {
    val model = it.model
    val jsonString = model.toJsonString()
    log.info("监听到新增模型事件: {}", jsonString)
  }

}
