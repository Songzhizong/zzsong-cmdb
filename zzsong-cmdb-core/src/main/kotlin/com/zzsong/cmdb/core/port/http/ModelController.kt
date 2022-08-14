package com.zzsong.cmdb.core.port.http

import com.zzsong.cmdb.common.pojo.Model
import com.zzsong.cmdb.core.application.ModelService
import com.zzsong.cmdb.core.dto.args.CreateModelArgs
import com.zzsong.framework.core.transmission.Result
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 模型管理
 *
 * @author 宋志宗 on 2022/8/13
 */
@RestController
@RequestMapping("/cmdb/model")
class ModelController(private val modelService: ModelService) {

  /**
   * 新增模型
   *
   * @author 宋志宗 on 2022/8/14
   */
  @PostMapping("/create_model")
  suspend fun createModel(@RequestBody args: CreateModelArgs): Result<Model> {
    val modelDo = modelService.create(args)
    val model = modelDo.toModel()
    return Result.success(model)
  }
}
