package com.vault.models.psd2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Message codes defined for signing baskets for HTTP Error code 403 (FORBIDDEN).
 */
public enum MessageCode403SB {
  
  CONSENT_UNKNOWN("CONSENT_UNKNOWN"),
  
  SERVICE_BLOCKED("SERVICE_BLOCKED"),
  
  RESOURCE_UNKNOWN("RESOURCE_UNKNOWN"),
  
  RESOURCE_EXPIRED("RESOURCE_EXPIRED");

  private String value;

  MessageCode403SB(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static MessageCode403SB fromValue(String text) {
    for (MessageCode403SB b : MessageCode403SB.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

