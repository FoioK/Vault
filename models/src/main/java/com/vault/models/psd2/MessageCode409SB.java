package com.vault.models.psd2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Message codes defined for signing baskets for HTTP Error code 409 (CONFLICT).
 */
public enum MessageCode409SB {
  
  REFERENCE_STATUS_INVALID("REFERENCE_STATUS_INVALID"),
  
  STATUS_INVALID("STATUS_INVALID");

  private String value;

  MessageCode409SB(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static MessageCode409SB fromValue(String text) {
    for (MessageCode409SB b : MessageCode409SB.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

