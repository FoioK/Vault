package com.vault.models.psd2;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * TppMessage409SB
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-01-08T15:31:32.742+01:00[Europe/Belgrade]")

public class TppMessage409SB   {
  @JsonProperty("category")
  private TppMessageCategory category = null;

  @JsonProperty("code")
  private MessageCode409SB code = null;

  @JsonProperty("path")
  private String path = null;

  @JsonProperty("text")
  private String text = null;

  public TppMessage409SB category(TppMessageCategory category) {
    this.category = category;
    return this;
  }

  /**
   * Get category
   * @return category
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public TppMessageCategory getCategory() {
    return category;
  }

  public void setCategory(TppMessageCategory category) {
    this.category = category;
  }

  public TppMessage409SB code(MessageCode409SB code) {
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

  public MessageCode409SB getCode() {
    return code;
  }

  public void setCode(MessageCode409SB code) {
    this.code = code;
  }

  public TppMessage409SB path(String path) {
    this.path = path;
    return this;
  }

  /**
   * Get path
   * @return path
  **/
  @ApiModelProperty(value = "")


  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public TppMessage409SB text(String text) {
    this.text = text;
    return this;
  }

  /**
   * Get text
   * @return text
  **/
  @ApiModelProperty(value = "")

@Size(max=512) 
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TppMessage409SB tppMessage409SB = (TppMessage409SB) o;
    return Objects.equals(this.category, tppMessage409SB.category) &&
        Objects.equals(this.code, tppMessage409SB.code) &&
        Objects.equals(this.path, tppMessage409SB.path) &&
        Objects.equals(this.text, tppMessage409SB.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(category, code, path, text);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TppMessage409SB {\n");

    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
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

