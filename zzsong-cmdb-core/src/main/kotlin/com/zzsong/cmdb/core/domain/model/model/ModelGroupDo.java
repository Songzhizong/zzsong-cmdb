package com.zzsong.cmdb.core.domain.model.model;

import com.zzsong.cmdb.common.pojo.ModelGroup;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

/**
 * 模型分组
 *
 * @author 宋志宗 on 2022/8/14
 */
@Document("cmdb_model_group")
@CompoundIndexes({
  @CompoundIndex(name = "uk_id", def = "{id:1}", unique = true),
  @CompoundIndex(name = "uk_name", def = "{name:1}", unique = true),
})
public class ModelGroupDo {

  @Id
  private long id;

  /** 分组名称 */
  @Nonnull
  private String name = "";

  /** 分组备注 */
  @Nullable
  private String note;

  @Version
  private long version;

  @CreatedDate
  private LocalDateTime createdTime;

  @LastModifiedDate
  private LocalDateTime updatedTime;

  @Nonnull
  public static ModelGroupDo create(@Nonnull String name,
                                    @Nullable String note) {
    ModelGroupDo modelGroupDo = new ModelGroupDo();
    modelGroupDo.setName(name);
    modelGroupDo.setNote(note);
    return modelGroupDo;
  }

  @Nonnull
  public ModelGroup toGroup() {
    ModelGroup modelGroup = new ModelGroup();
    modelGroup.setId(getId());
    modelGroup.setName(getName());
    modelGroup.setNote(getNote());
    modelGroup.setCreatedTime(getCreatedTime());
    modelGroup.setUpdatedTime(getUpdatedTime());
    return modelGroup;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public long getVersion() {
    return version;
  }

  public void setVersion(long version) {
    this.version = version;
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
