package com.zzsong.cmdb.common.constants;

import com.zzsong.cmdb.common.resource.ResourceUtils;
import com.zzsong.framework.core.exception.BadRequestException;
import com.zzsong.framework.core.lang.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 模型属性类型
 *
 * @author 宋志宗 on 2022/5/10
 */
public enum AttributeTypeEnum {
  /** 短字符 */
  SHORT_CHARACTER {
    @Override
    public void checkOption(@Nullable Object option) {
      super.checkOption(option);
      // 短字符的配置选项为正则表达式字符串
      AttributeTypeEnum.checkAndGetRegex(option);
    }

    @Override
    public void checkField(@Nonnull Object field, @Nullable Object option) {
      super.checkField(field, option);
      if (!(field instanceof String)) {
        throw new BadRequestException("短字符参数非字符串类型");
      }
      if (StringUtils.isBlank((String) field)) {
        return;
      }
      Pattern pattern = AttributeTypeEnum.checkAndGetRegex(option);
      if (pattern == null) {
        return;
      }
      Matcher matcher = pattern.matcher((String) field);
      if (!matcher.matches()) {
        throw new BadRequestException("短字符值不符合正则校验");
      }
    }
  },
  /** 长字符 */
  LONG_CHARACTER {
    @Override
    public void checkOption(@Nullable Object option) {
      super.checkOption(option);
      // 长字符的配置选项为正则表达式字符串
      AttributeTypeEnum.checkAndGetRegex(option);
    }

    @Override
    public void checkField(@Nonnull Object field, @Nullable Object option) {
      super.checkField(field, option);
      if (!(field instanceof String)) {
        throw new BadRequestException("长字符参数非字符串类型");
      }
      if (StringUtils.isBlank((String) field)) {
        return;
      }
      Pattern pattern = AttributeTypeEnum.checkAndGetRegex(option);
      if (pattern == null) {
        return;
      }
      Matcher matcher = pattern.matcher((String) field);
      if (!matcher.matches()) {
        throw new BadRequestException("长字符值不符合正则校验");
      }
    }
  },
  /** 数字 */
  DIGITAL {
    @Override
    public void checkOption(@Nullable Object option) {
      super.checkOption(option);
      if (option == null) {
        return;
      }
      if (!(option instanceof Map)) {
        throw new BadRequestException("无效的整数区间配置");
      }
    }

    @Override
    public void checkField(@Nonnull Object field, @Nullable Object option) {
      super.checkField(field, option);
      if (!(field instanceof Integer) && !(field instanceof Long)) {
        throw new BadRequestException("数字参数非数值类型");
      }
    }

    @Nonnull
    @Override
    public Object converter(@Nonnull Object field) {
      if (field instanceof Integer) {
        return ((Integer) field).longValue();
      }
      return field;
    }
  },
  /** 浮点型 */
  FLOAT {
    @Override
    public void checkOption(@Nullable Object option) {
      super.checkOption(option);
      if (option == null) {
        return;
      }
      if (!(option instanceof Map)) {
        throw new BadRequestException("无效的浮点区间配置");
      }
    }

    @Override
    public void checkField(@Nonnull Object field, @Nullable Object option) {
      super.checkField(field, option);
      if (field instanceof Number) {
        return;
      }
      if ((field instanceof String) && StringUtils.isNotBlank((String) field)) {
        return;
      }
      throw new BadRequestException("浮点型参数非浮点类型");
    }

    @Nonnull
    @Override
    public Object converter(@Nonnull Object field) {
      if (field instanceof Number) {
        return ((Number) field).doubleValue();
      }
      if (field instanceof String) {
        return Double.valueOf((String) field);
      }
      throw new BadRequestException("浮点型参数非浮点类型");
    }
  },

  /**
   * 枚举型
   * <pre>
   *   [
   *     {
   *       "id": "sugon",
   *       "defaultValue": true,
   *       "name": "曙光",
   *       "type": "text"
   *     }
   *   ]
   * </pre>
   */
  ENUM {
    @Override
    public void checkOption(@Nullable Object option) {
      super.checkOption(option);
      if (option == null) {
        return;
      }
      if (!(option instanceof List)) {
        throw new BadRequestException("无效的枚举配置");
      }
      @SuppressWarnings("unchecked")
      List<Object> enumNodes = (List<Object>) option;
      Set<String> keys = new HashSet<>();
      Set<String> names = new HashSet<>();
      boolean hasDefault = false;
      for (Object node : enumNodes) {
        if (!(node instanceof Map)) {
          throw new BadRequestException("无效的枚举配置");
        }
        @SuppressWarnings("unchecked")
        Map<Object, Object> nodeMap = (Map<Object, Object>) node;
        Object id = nodeMap.get("id");
        if (id == null) {
          throw new BadRequestException("枚举id为空");
        }
        if (!(id instanceof String idStr)) {
          throw new BadRequestException("枚举id非字符串类型");
        }
        boolean addId = keys.add(idStr);
        if (!addId) {
          throw new BadRequestException("重复的枚举id: " + idStr);
        }
        Object name = nodeMap.get("name");
        if (name == null) {
          throw new BadRequestException("枚举名称为空");
        }
        if (!(name instanceof String nameStr)) {
          throw new BadRequestException("枚举名称非字符串类型");
        }
        boolean addName = names.add(nameStr);
        if (!addName) {
          throw new BadRequestException("重复的枚举名称: " + idStr);
        }
        Object defaultValue = nodeMap.get("defaultValue");
        if (defaultValue == null) {
          throw new BadRequestException("是否默认值配置为空");
        }
        if (!(defaultValue instanceof Boolean)) {
          throw new BadRequestException("枚举是否默认值非布尔类型");
        }
        boolean defaultValueBool = (boolean) defaultValue;
        if (defaultValueBool) {
          if (hasDefault) {
            throw new BadRequestException("重复的默认值配置");
          }
          hasDefault = true;
        }
      }
    }

    @Override
    public void checkField(@Nonnull Object field, @Nullable Object option) {
      super.checkField(field, option);
      if (!(field instanceof String)) {
        throw new BadRequestException("枚举型参数非字符串类型");
      }
    }
  },
  /** 日期 */
  DATE {
    @Override
    public void checkOption(@Nullable Object option) {
      super.checkOption(option);
    }

    @Override
    public void checkField(@Nonnull Object field, @Nullable Object option) {
      super.checkField(field, option);
      if (!(field instanceof String)) {
        throw new BadRequestException("日期类型非格式化字符串");
      }
    }

    @Nonnull
    @Override
    public Object converter(@Nonnull Object field) {
      if (!(field instanceof String)) {
        throw new BadRequestException("日期类型非格式化字符串");
      }
      return LocalDate.parse((String) field, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
  },
  /** 时间 */
  TIME {
    @Override
    public void checkField(@Nonnull Object field, @Nullable Object option) {
      super.checkField(field, option);
      if (!(field instanceof String)) {
        throw new BadRequestException("日期类型非格式化字符串");
      }
    }

    @Nonnull
    @Override
    public Object converter(@Nonnull Object field) {
      if (!(field instanceof String)) {
        throw new BadRequestException("日期类型非格式化字符串");
      }
      return LocalDateTime.parse((String) field, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }
  },
  /** 布尔型 */
  BOOL {
    @Override
    public void checkField(@Nonnull Object field, @Nullable Object option) {
      super.checkField(field, option);
      if (field instanceof Boolean) {
        return;
      }
      throw new BadRequestException("布尔型参数非boolean类型");
    }
  },
  /** 列表 */
  LIST {
    @Override
    public void checkOption(@Nullable Object option) {
      super.checkOption(option);
      if (option == null) {
        throw new BadRequestException("列表类型可选项为空");
      }
      if (!(option instanceof List<?> objects)) {
        throw new BadRequestException("列表型配置非数组类型");
      }
      for (Object object : objects) {
        if (!(object instanceof String)) {
          throw new BadRequestException("列表选项非字符串类型");
        }
      }
    }

    @Override
    public void checkField(@Nonnull Object field, @Nullable Object option) {
      super.checkField(field, option);
      if (field instanceof String) {
        return;
      }
      throw new BadRequestException("列表型参数非字符串类型");
    }
  },
  /** 用户, 用户直接保存用户id(字符串类型) */
  USER {
    @Override
    public void checkField(@Nonnull Object field, @Nullable Object option) {
      super.checkField(field, option);
      if (!(field instanceof List)) {
        throw new BadRequestException("非法的用户id列表类型：" + field.getClass().getName());
      }
      @SuppressWarnings("unchecked")
      List<Object> userIds = (List<Object>) field;
      for (Object userId : userIds) {
        if (userId instanceof String) {
          continue;
        }
        if (userId instanceof Number) {
          continue;
        }
        throw new BadRequestException("非法的用户id类型: " + field.getClass().getName());
      }
    }

    @Nonnull
    @Override
    public Object converter(@Nonnull Object field) {
      if (!(field instanceof List)) {
        throw new BadRequestException("非法的用户id列表类型：" + field.getClass().getName());
      }
      List<String> result = new ArrayList<>();
      @SuppressWarnings("unchecked")
      List<Object> userIds = (List<Object>) field;
      for (Object userId : userIds) {
        if (userId instanceof Number) {
          result.add(String.valueOf(userId));
          continue;
        }
        if (userId instanceof String) {
          result.add((String) userId);
          continue;
        }
        throw new BadRequestException("非法的用户id类型: " + field.getClass().getName());
      }
      return result;
    }
  },
  ;

  @Nullable
  private static Pattern checkAndGetRegex(@Nullable Object option) {
    if (option == null) {
      return null;
    }
    if (!(option instanceof String regex)) {
      throw new BadRequestException("正则表达式必须为字符串类型");
    }
    if (StringUtils.isBlank(regex)) {
      return null;
    }
    try {
      return Pattern.compile(regex);
    } catch (Exception e) {
      throw new BadRequestException("无效的正则表达式");
    }
  }

  /** 校验option配置的有效性 */
  public void checkOption(@Nullable Object option) {

  }

  /** 校验字段值的有效性 */
  public void checkField(@Nonnull Object field, @Nullable Object option) {
    if (field instanceof String s) {
      if (s.startsWith(ResourceUtils.ENC_PREFIX)) {
        throw new BadRequestException("非法的输入字符: " + ResourceUtils.ENC_PREFIX);
      }
    }
  }

  /** 对参数进行转换 */
  @Nonnull
  public Object converter(@Nonnull Object field) {
    return field;
  }
}
