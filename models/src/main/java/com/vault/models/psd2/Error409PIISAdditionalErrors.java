package com.vault.models.psd2;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * This is a data element to support the declaration of additional errors in the context of [RFC7807].
 */
@ApiModel(description = "This is a data element to support the declaration of additional errors in the context of [RFC7807].")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-01-08T15:31:32.742+01:00[Europe/Belgrade]")

public class Error409PIISAdditionalErrors   {
  @JsonProperty("title")
  private String title = null;

  @JsonProperty("detail")
  private String detail = null;

  @JsonProperty("code")
  private MessageCode409PIIS code = null;

  public Error409PIISAdditionalErrors title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
  **/
  @ApiModelProperty(value = "")

@Size(max=70) 
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Error409PIISAdditionalErrors detail(String detail) {
    this.detail = detail;
    return this;
  }

  /**
   * Get detail
   * @return detail
  **/
  @ApiModelProperty(value = "")

@Size(max=512) 
  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public Error409PIISAdditionalErrors code(MessageCode409PIIS code) {
    this.code = code;
    return this;
  }

  /**
   * Get code
   * @return code
  **/
  @ApiModelProperty(value = "")

  @Valid

  public MessageCode409PIIS getCode() {
    return code;
  }

  public void setCode(MessageCode409PIIS code) {
    this.code = code;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Error409PIISAdditionalErrors error409PIISAdditionalErrors = (Error409PIISAdditionalErrors) o;
    return Objects.equals(this.title, error409PIISAdditionalErrors.title) &&
        Objects.equals(this.detail, error409PIISAdditionalErrors.detail) &&
        Objects.equals(this.code, error409PIISAdditionalErrors.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, detail, code);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Error409PIISAdditionalErrors {\n");

    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
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

