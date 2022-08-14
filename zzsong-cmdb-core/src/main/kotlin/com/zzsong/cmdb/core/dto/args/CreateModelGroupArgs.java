package com.zzsong.cmdb.core.dto.args;

import javax.annotation.Nullable;

/**
 * @author 宋志宗 on 2022/8/14
 */
public class CreateModelGroupArgs {

  /**
   * 分组名称
   *
   * @required
   */
  @Nullable
  private String name;

  /** 备注信息 */
  @Nullable
  private String note;


  @Nullable
  public String getName() {
    return name;
  }

  public void setName(@Nullable String name) {
    this.name = name;
  }

  @Nullable
  public String getNote() {
    return note;
  }

  public void setNote(@Nullable String note) {
    this.note = note;
  }
}
