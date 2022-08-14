package com.zzsong.cmdb.common.event.model;

import com.zzsong.cmdb.common.pojo.Model;
import com.zzsong.framework.core.event.BaseEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * @author 宋志宗 on 2022/8/14
 */
public class ModelCreated extends BaseEvent {
  public static final String TOPIC = "cmdb.model.created";

  private Model model;

  public ModelCreated() {
  }

  public ModelCreated(Model model) {
    this.model = model;
  }

  @NotNull
  @Override
  public String getTopic() {
    return TOPIC;
  }

  @Nonnull
  public Model getModel() {
    return model;
  }

  public void setModel(@Nonnull Model model) {
    this.model = model;
  }
}
