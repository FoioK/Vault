package com.vault.models.psd2;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Content of the body of a Select PSU Authentication Method Request 
 */
@ApiModel(description = "Content of the body of a Select PSU Authentication Method Request ")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-01-08T15:31:32.742+01:00[Europe/Belgrade]")

public class SelectPsuAuthenticationMethod   {
  @JsonProperty("authenticationMethodId")
  private String authenticationMethodId = null;

  public SelectPsuAuthenticationMethod authenticationMethodId(String authenticationMethodId) {
    this.authenticationMethodId = authenticationMethodId;
    return this;
  }

  /**
   * Get authenticationMethodId
   * @return authenticationMethodId
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

@Size(max=35) 
  public String getAuthenticationMethodId() {
    return authenticationMethodId;
  }

  public void setAuthenticationMethodId(String authenticationMethodId) {
    this.authenticationMethodId = authenticationMethodId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SelectPsuAuthenticationMethod selectPsuAuthenticationMethod = (SelectPsuAuthenticationMethod) o;
    return Objects.equals(this.authenticationMethodId, selectPsuAuthenticationMethod.authenticationMethodId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authenticationMethodId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SelectPsuAuthenticationMethod {\n");

    sb.append("    authenticationMethodId: ").append(toIndentedString(authenticationMethodId)).append("\n");
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

