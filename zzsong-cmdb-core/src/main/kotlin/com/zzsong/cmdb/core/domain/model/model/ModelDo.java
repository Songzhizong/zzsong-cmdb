package com.zzsong.cmdb.core.domain.model.model;

import com.zzsong.cmdb.common.pojo.Model;
import com.zzsong.cmdb.core.domain.model.model.event.builder.ModelCreatedBuilder;
import com.zzsong.framework.core.event.EventTuple;
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
 * @author 宋志宗 on 2022/8/14
 */
@Document("cmdb_model")
@CompoundIndexes({
  @CompoundIndex(name = "uk_ident", def = "{ident:1}", unique = true),
  @CompoundIndex(name = "uk_name", def = "{name:1}", unique = true),
})
public class ModelDo {

  @Id
  private long id;

  /** 模型分组ID */
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

  @Version
  private long version;

  @CreatedDate
  private LocalDateTime createdTime;

  @LastModifiedDate
  private LocalDateTime updatedTime;

  @Nonnull
  public static EventTuple<ModelDo> create(long groupId,
                                           @Nonnull String ident,
                                           @Nonnull String name,
                                           @Nullable String note) {
    ModelDo modelDo = new ModelDo();
    modelDo.setGroupId(groupId);
    modelDo.setIdent(ident);
    modelDo.setName(name);
    modelDo.setNote(note);
    return EventTuple.of(modelDo, new ModelCreatedBuilder(modelDo));
  }

  @Nonnull
  public Model toModel() {
    Model model = new Model();
    model.setGroupId(getGroupId());
    model.setIdent(getIdent());
    model.setName(getName());
    model.setNote(getNote());
    model.setCreatedTime(getCreatedTime());
    model.setUpdatedTime(getUpdatedTime());
    return model;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

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
