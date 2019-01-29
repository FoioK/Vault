package com.vault.models.psd2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Message codes defined for signing baskets for HTTP Error code 401 (UNAUTHORIZED).
 */
public enum MessageCode401SB {
  
  CERTIFICATE_INVALID("CERTIFICATE_INVALID"),
  
  CERTIFICATE_EXPIRED("CERTIFICATE_EXPIRED"),
  
  CERTIFICATE_BLOCKED("CERTIFICATE_BLOCKED"),
  
  CERTIFICATE_REVOKE("CERTIFICATE_REVOKE"),
  
  CERTIFICATE_MISSING("CERTIFICATE_MISSING"),
  
  SIGNATURE_INVALID("SIGNATURE_INVALID"),
  
  SIGNATURE_MISSING("SIGNATURE_MISSING"),
  
  CORPORATE_ID_INVALID("CORPORATE_ID_INVALID"),
  
  PSU_CREDENTIALS_INVALID("PSU_CREDENTIALS_INVALID"),
  
  CONSENT_INVALID("CONSENT_INVALID"),
  
  CONSENT_EXPIRED("CONSENT_EXPIRED"),
  
  TOKEN_UNKNOWN("TOKEN_UNKNOWN"),
  
  TOKEN_INVALID("TOKEN_INVALID"),
  
  TOKEN_EXPIRED("TOKEN_EXPIRED");

  private String value;

  MessageCode401SB(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static MessageCode401SB fromValue(String text) {
    for (MessageCode401SB b : MessageCode401SB.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

