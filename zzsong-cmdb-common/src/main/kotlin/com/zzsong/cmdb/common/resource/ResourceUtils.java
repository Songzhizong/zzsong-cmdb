package com.zzsong.cmdb.common.resource;

import com.zzsong.framework.core.crypto.AES;

import javax.annotation.Nonnull;

/**
 * @author 宋志宗 on 2022/8/2
 */
public final class ResourceUtils {
  public static final String ENC_PREFIX = "::$$enc$$::";
  private static final String SECRET = "TkdVeVkyWTNZek16TkdRM056QTJNbU14";
  private static final int ENC_PREFIX_LENGTH = ENC_PREFIX.length();

  private ResourceUtils() {
  }

  @Nonnull
  public static String encrypt(@Nonnull String text) {
    String encrypt = AES.encrypt(text, SECRET);
    return ENC_PREFIX + encrypt;
  }

  @Nonnull
  public static Object decrypt(@Nonnull Object object) {
    if (!(object instanceof String s)) {
      return object;
    }
    if (!s.startsWith(ENC_PREFIX)) {
      return object;
    }

    String substring = s.substring(ENC_PREFIX_LENGTH);
    return AES.decrypt(substring, SECRET);
  }
}
