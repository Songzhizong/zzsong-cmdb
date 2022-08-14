package com.zzsong.cmdb.core.domain.model.model.repository

import com.zzsong.cmdb.core.domain.model.model.ModelDo

/**
 * @author 宋志宗 on 2022/8/14
 */
interface ModelRepository  {
  suspend fun save(modelDo: ModelDo): ModelDo
}
