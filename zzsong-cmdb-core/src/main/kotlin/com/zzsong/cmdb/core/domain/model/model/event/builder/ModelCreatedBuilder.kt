package com.zzsong.cmdb.core.domain.model.model.event.builder

import com.zzsong.cmdb.common.event.model.ModelCreated
import com.zzsong.cmdb.core.domain.model.model.ModelDo
import com.zzsong.framework.core.event.Event
import com.zzsong.framework.core.event.EventBuilder

/**
 * @author 宋志宗 on 2022/8/14
 */
class ModelCreatedBuilder(private val modelDo: ModelDo) : EventBuilder {

  override fun build(): Event {
    val event = ModelCreated()
    event.model = modelDo.toModel()
    return event
  }
}
