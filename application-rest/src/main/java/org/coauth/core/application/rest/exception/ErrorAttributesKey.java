package org.coauth.core.application.rest.exception;

import lombok.Getter;

@Getter
public enum ErrorAttributesKey {
  CODE("code"),
  MESSAGE("message");

  private final String key;

  ErrorAttributesKey(String key) {
    this.key = key;
  }
}