package com.vault.models.psd2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Message codes defined for SB for HTTP Error code 405 (METHOD NOT ALLOWED).
 */
public enum MessageCode405SB {
  
  INVALID("SERVICE_INVALID");

  private String value;

  MessageCode405SB(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static MessageCode405SB fromValue(String text) {
    for (MessageCode405SB b : MessageCode405SB.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

