package com.zzsong.cmdb.core.dto.args;

import javax.annotation.Nullable;

/**
 * @author 宋志宗 on 2022/8/14
 */
public class CreateModelArgs {

  /**
   * 分组ID
   *
   * @required
   */
  @Nullable
  private Long groupId;

  /**
   * 模型标识
   *
   * @required
   */
  @Nullable
  private String ident;

  /**
   * 模型名称
   *
   * @required
   */
  @Nullable
  private String name;

  /** 模型备注 */
  @Nullable
  private String note;

  @Nullable
  public Long getGroupId() {
    return groupId;
  }

  public void setGroupId(@Nullable Long groupId) {
    this.groupId = groupId;
  }

  @Nullable
  public String getIdent() {
    return ident;
  }

  public void setIdent(@Nullable String ident) {
    this.ident = ident;
  }

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
