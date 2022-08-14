package com.zzsong.cmdb.launcher;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mongodb.MongoCommandException;
import com.mongodb.MongoWriteException;
import com.mongodb.WriteError;
import com.zzsong.framework.core.exception.VisibleException;
import com.zzsong.framework.core.json.JsonFormatException;
import com.zzsong.framework.core.json.JsonParseException;
import com.zzsong.framework.core.json.JsonUtils;
import com.zzsong.framework.core.lang.StringUtils;
import com.zzsong.framework.core.spring.ExchangeUtils;
import com.zzsong.framework.core.transmission.Result;
import com.zzsong.framework.core.utils.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * @author 宋志宗 on 2022/7/14
 */
@Component
public class FilterExceptionHandler implements ErrorWebExceptionHandler, Ordered {
  private static final Logger log = LoggerFactory.getLogger(FilterExceptionHandler.class);
  private static final HttpHeaders HTTP_HEADERS = new HttpHeaders();

  static {
    HTTP_HEADERS.set("Content-Type", "application/json;charset=utf-8");
  }


  @Nonnull
  @Override
  public Mono<Void> handle(@Nonnull ServerWebExchange exchange, @Nonnull Throwable ex) {
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    Result<Object> res = null;

    if (ex instanceof VisibleException exception) {
      String message = exception.getMessage();
      int status = exception.getHttpStatus();
      httpStatus = HttpStatus.valueOf(status);
      log.info("{}", message);
      res = Result.exception(ex);
    }

    // json序列化异常
    if (ex instanceof JsonFormatException) {
      log.info("JsonFormatException: ", ex);
      res = Result.exception(ex);
    }

    // json解析异常
    if (ex instanceof JsonParseException) {
      log.info("JsonParseException: ", ex);
      res = Result.exception(ex);
    }

    // 参数校验不通过异常处理
    if (ex instanceof MethodArgumentNotValidException exception) {
      httpStatus = HttpStatus.BAD_REQUEST;
      String message = exception.getBindingResult().getFieldErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining(", "));
      log.info("MethodArgumentNotValidException {}", message);
      res = Result.failure(message);
    }

    // 参数校验不通过异常处理
    if (ex instanceof BindException exception) {
      httpStatus = HttpStatus.BAD_REQUEST;
      String message = exception.getBindingResult().getFieldErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining(", "));
      log.info("BindException {}", message);
      res = Result.failure(message);
    }

    if (ex instanceof HttpMessageNotReadableException exception) {
      httpStatus = HttpStatus.BAD_REQUEST;
      Throwable rootCause = exception.getRootCause();
      String originalMessage;
      if (rootCause instanceof InvalidFormatException) {
        originalMessage = ((InvalidFormatException) rootCause).getOriginalMessage();
      } else {
        originalMessage = getOrgExMessage(exception);
      }
      if (originalMessage == null) {
        originalMessage = "HttpMessageNotReadableException";
      }
      log.info("HttpMessageNotReadableException: {}", originalMessage);
      res = Result.failure(originalMessage);
      String prefix = "Cannot coerce empty String";
      if (originalMessage.startsWith(prefix)) {
        res.setMessage("枚举类型值为空时请传null,而非空白字符串");
      }
    }

    if (ex instanceof IllegalArgumentException exception) {
      httpStatus = HttpStatus.BAD_REQUEST;
      String message = exception.getLocalizedMessage();
      if (message == null) {
        message = "illegal argument";
      }
      log.info("", exception);
      res = Result.failure(message);
    }

    if (ex instanceof IllegalStateException exception) {
      httpStatus = HttpStatus.BAD_REQUEST;
      String message = getOrgExMessage(exception);
      if (message == null) {
        message = "illegal status";
      }
      log.info("", exception);
      res = Result.failure(message);
    }

    if (ex instanceof MethodArgumentTypeMismatchException exception) {
      httpStatus = HttpStatus.BAD_REQUEST;
      String message = getOrgExMessage(exception);
      if (message == null) {
        message = exception.getClass().getName();
      }
      log.info("MethodArgumentTypeMismatchException, {}", message);
      res = Result.failure(message);
    }

    if (ex instanceof ServerWebInputException exception) {
      httpStatus = HttpStatus.BAD_REQUEST;
      String message = exception.getReason();
      Throwable cause = exception.getCause();
      if (cause != null) {
        message = getOrgExMessage(exception);
      }
      log.info("ServerWebInputException {}", message);
      res = Result.failure(message);
    }

    if (ex instanceof UncategorizedMongoDbException exception) {
      httpStatus = HttpStatus.BAD_REQUEST;
      Throwable rootCause = ExceptionUtils.getRootCause(exception);
      String message = rootCause.getMessage();
      if (StringUtils.isBlank(message)) {
        message = rootCause.getClass().getName();
      }
      log.info("UncategorizedMongoDbException {}", message);
      res = Result.failure(message);
    }

    if (ex instanceof MongoCommandException exception) {
      httpStatus = HttpStatus.BAD_REQUEST;
      Throwable rootCause = ExceptionUtils.getRootCause(exception);
      String message = rootCause.getMessage();
      if (StringUtils.isBlank(message)) {
        message = rootCause.getClass().getName();
      }
      log.info("MongoCommandException {}", message);
      res = Result.failure(message);
    }

    if (ex instanceof DuplicateKeyException exception) {
      String message = getDuplicateMessage(exception);
      log.info("DuplicateKeyException {}", message);
      res = Result.failure(message);
    }

    if (res == null) {
      String message = getOrgExMessage(ex);
      if (message == null) {
        message = ex.getClass().getSimpleName();
      }
      log.warn("未针对处理的异常: ", ex);
      res = Result.failure(message);
    }
    String jsonString = JsonUtils.toJsonString(res);
    byte[] bytes = jsonString.getBytes(StandardCharsets.UTF_8);
    return ExchangeUtils.writeResponse(exchange, httpStatus, HTTP_HEADERS, bytes);
  }

  @Nullable
  public String getOrgExMessage(@Nonnull Throwable throwable) {
    Throwable cause = throwable.getCause();
    if (cause != null) {
      return getOrgExMessage(cause);
    }
    return throwable.getMessage();
  }

  @Override
  public int getOrder() {
    return -1;
  }

  @Nonnull
  private String getDuplicateMessage(@Nonnull DuplicateKeyException e) {
    Throwable cause = e.getCause();
    if (cause instanceof MongoWriteException mongoWriteException) {
      WriteError writeError = mongoWriteException.getError();

      String message = writeError.getMessage();
      if (StringUtils.isNotBlank(message)) {
        int index = message.lastIndexOf("key:");
        if (index < 0) {
          return message;
        }
        return "违反唯一性约束 " + message.substring(index + 4).replace("\"", "");
      }
    }
    String defaultMessage = e.getMessage();
    if (defaultMessage == null) {
      defaultMessage = "";
    }
    return "违反唯一性约束 " + defaultMessage;
  }
}
