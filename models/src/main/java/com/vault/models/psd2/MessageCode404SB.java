package com.vault.models.psd2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Message codes defined for signing baskets for HTTP Error code 404 (NOT FOUND).
 */
public enum MessageCode404SB {
  
  UNKNOWN("RESOURCE_UNKNOWN");

  private String value;

  MessageCode404SB(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static MessageCode404SB fromValue(String text) {
    for (MessageCode404SB b : MessageCode404SB.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

