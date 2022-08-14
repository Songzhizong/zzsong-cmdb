package com.zzsong.cmdb.core.port.http

import com.zzsong.cmdb.common.pojo.ModelGroup
import com.zzsong.cmdb.core.application.ModelGroupService
import com.zzsong.cmdb.core.dto.args.CreateModelGroupArgs
import com.zzsong.framework.core.transmission.Result
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 模型分组
 *
 * @author 宋志宗 on 2022/8/14
 */
@RestController
@RequestMapping("/cmdb/model_group")
class ModelGroupController(private val modelGroupService: ModelGroupService) {

  /**
   * 新增模型分组
   *
   * @author 宋志宗 on 2022/8/14
   */
  @PostMapping("/create_group")
  suspend fun createModelGroup(@RequestBody args: CreateModelGroupArgs): Result<ModelGroup> {
    val groupDo = modelGroupService.create(args)
    val modelGroup = groupDo.toGroup()
    return Result.success(modelGroup)
  }
}
