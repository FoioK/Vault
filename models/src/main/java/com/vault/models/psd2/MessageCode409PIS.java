package com.vault.models.psd2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Message codes defined for PIS for HTTP Error code 409 (CONFLICT).
 */
public enum MessageCode409PIS {
  
  INVALID("STATUS_INVALID");

  private String value;

  MessageCode409PIS(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static MessageCode409PIS fromValue(String text) {
    for (MessageCode409PIS b : MessageCode409PIS.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

