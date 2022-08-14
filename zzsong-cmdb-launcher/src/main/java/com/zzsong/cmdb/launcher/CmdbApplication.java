package com.zzsong.cmdb.launcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.nativex.hint.JdkProxyHint;
import org.springframework.nativex.hint.NativeHint;

@NativeHint(
  jdkProxies = @JdkProxyHint(
    types = {
      org.springframework.data.mongodb.core.mapping.Document.class,
      org.springframework.core.annotation.SynthesizedAnnotation.class
    }
  )
)
@SpringBootApplication(proxyBeanMethods = false)
public class CmdbApplication {
  private static final Logger log = LoggerFactory.getLogger(CmdbApplication.class);

  public static void main(String[] args) {
    int ioWorkerCount = Runtime.getRuntime().availableProcessors();
    log.info("ioWorkerCount: {}", ioWorkerCount);
    System.setProperty("reactor.netty.ioWorkerCount", String.valueOf(ioWorkerCount));
    SpringApplication.run(CmdbApplication.class, args);
  }
}
