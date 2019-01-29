package com.vault.models.psd2;

import io.swagger.annotations.ApiModel;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * This parameter is requesting a valid until date for the requested consent.  The content is the local ASPSP date in ISO-Date Format, e.g. 2017-10-30.   Future dates might get adjusted by ASPSP.   If a maximal available date is requested, a date in far future is to be used: \&quot;9999-12-31\&quot;.   In both cases the consent object to be retrieved by the GET Consent Request will contain the adjusted date. 
 */
@ApiModel(description = "This parameter is requesting a valid until date for the requested consent.  The content is the local ASPSP date in ISO-Date Format, e.g. 2017-10-30.   Future dates might get adjusted by ASPSP.   If a maximal available date is requested, a date in far future is to be used: \"9999-12-31\".   In both cases the consent object to be retrieved by the GET Consent Request will contain the adjusted date. ")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-01-08T15:31:32.742+01:00[Europe/Belgrade]")

public class ValidUntil   {

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ValidUntil {\n");

    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

