package com.vault.models.psd2;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Standardised definition of reporting error information according to [RFC7807]  in case of a HTTP error code 405 for a pament cancelation (PIS). 
 */
@ApiModel(description = "Standardised definition of reporting error information according to [RFC7807]  in case of a HTTP error code 405 for a pament cancelation (PIS). ")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-01-08T15:31:32.742+01:00[Europe/Belgrade]")

public class Error405PISCANC   {
  @JsonProperty("type")
  private String type = null;

  @JsonProperty("title")
  private String title = null;

  @JsonProperty("detail")
  private String detail = null;

  @JsonProperty("code")
  private MessageCode405PISCANC code = null;

  @JsonProperty("additionalErrors")
  @Valid
  private List<Error405PISCANCAdditionalErrors> additionalErrors = null;

  @JsonProperty("_links")
  private Map _links = null;

  public Error405PISCANC type(String type) {
    this.type = type;
    return this;
  }

  /**
   * A URI reference [RFC3986] that identifies the problem type.  Remark For Future: These URI will be provided by NextGenPSD2 in future. 
   * @return type
  **/
  @ApiModelProperty(required = true, value = "A URI reference [RFC3986] that identifies the problem type.  Remark For Future: These URI will be provided by NextGenPSD2 in future. ")
  @NotNull

@Size(max=70) 
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Error405PISCANC title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Short human readable description of error type.  Could be in local language.  To be provided by ASPSPs. 
   * @return title
  **/
  @ApiModelProperty(value = "Short human readable description of error type.  Could be in local language.  To be provided by ASPSPs. ")

@Size(max=70) 
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Error405PISCANC detail(String detail) {
    this.detail = detail;
    return this;
  }

  /**
   * Detailed human readable text specific to this instance of the error.  XPath might be used to point to the issue generating the error in addition. Remark for Future: In future, a dedicated field might be introduced for the XPath. 
   * @return detail
  **/
  @ApiModelProperty(value = "Detailed human readable text specific to this instance of the error.  XPath might be used to point to the issue generating the error in addition. Remark for Future: In future, a dedicated field might be introduced for the XPath. ")

@Size(max=512) 
  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public Error405PISCANC code(MessageCode405PISCANC code) {
    this.code = code;
    return this;
  }

  /**
   * Get code
   * @return code
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public MessageCode405PISCANC getCode() {
    return code;
  }

  public void setCode(MessageCode405PISCANC code) {
    this.code = code;
  }

  public Error405PISCANC additionalErrors(List<Error405PISCANCAdditionalErrors> additionalErrors) {
    this.additionalErrors = additionalErrors;
    return this;
  }

  public Error405PISCANC addAdditionalErrorsItem(Error405PISCANCAdditionalErrors additionalErrorsItem) {
    if (this.additionalErrors == null) {
      this.additionalErrors = new ArrayList<>();
    }
    this.additionalErrors.add(additionalErrorsItem);
    return this;
  }

  /**
   * Array of Error Information Blocks.  Might be used if more than one error is to be communicated 
   * @return additionalErrors
  **/
  @ApiModelProperty(value = "Array of Error Information Blocks.  Might be used if more than one error is to be communicated ")

  @Valid

  public List<Error405PISCANCAdditionalErrors> getAdditionalErrors() {
    return additionalErrors;
  }

  public void setAdditionalErrors(List<Error405PISCANCAdditionalErrors> additionalErrors) {
    this.additionalErrors = additionalErrors;
  }

  public Error405PISCANC _links(Map _links) {
    this._links = _links;
    return this;
  }

  /**
   * Get _links
   * @return _links
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Map getLinks() {
    return _links;
  }

  public void setLinks(Map _links) {
    this._links = _links;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Error405PISCANC error405PISCANC = (Error405PISCANC) o;
    return Objects.equals(this.type, error405PISCANC.type) &&
        Objects.equals(this.title, error405PISCANC.title) &&
        Objects.equals(this.detail, error405PISCANC.detail) &&
        Objects.equals(this.code, error405PISCANC.code) &&
        Objects.equals(this.additionalErrors, error405PISCANC.additionalErrors) &&
        Objects.equals(this._links, error405PISCANC._links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, title, detail, code, additionalErrors, _links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Error405PISCANC {\n");

    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    additionalErrors: ").append(toIndentedString(additionalErrors)).append("\n");
    sb.append("    _links: ").append(toIndentedString(_links)).append("\n");
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

