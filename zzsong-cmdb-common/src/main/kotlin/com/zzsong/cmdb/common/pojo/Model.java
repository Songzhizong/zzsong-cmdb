package com.zzsong.cmdb.common.pojo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

/**
 * @author 宋志宗 on 2022/8/14
 */
public class Model {

  /** 分组ID */
  private long groupId;

  /** 模型唯一标识 */
  @Nonnull
  private String ident = "";

  /** 模型名称 */
  @Nonnull
  private String name = "";

  /** 模型备注 */
  @Nullable
  private String note;

  private LocalDateTime createdTime;

  private LocalDateTime updatedTime;

  public long getGroupId() {
    return groupId;
  }

  public void setGroupId(long groupId) {
    this.groupId = groupId;
  }

  @Nonnull
  public String getIdent() {
    return ident;
  }

  public void setIdent(@Nonnull String ident) {
    this.ident = ident;
  }

  @Nonnull
  public String getName() {
    return name;
  }

  public void setName(@Nonnull String name) {
    this.name = name;
  }

  @Nullable
  public String getNote() {
    return note;
  }

  public void setNote(@Nullable String note) {
    this.note = note;
  }

  public LocalDateTime getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(LocalDateTime createdTime) {
    this.createdTime = createdTime;
  }

  public LocalDateTime getUpdatedTime() {
    return updatedTime;
  }

  public void setUpdatedTime(LocalDateTime updatedTime) {
    this.updatedTime = updatedTime;
  }
}
