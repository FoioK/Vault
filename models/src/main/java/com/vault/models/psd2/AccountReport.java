package com.vault.models.psd2;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Objects;

/**
 * JSON based account report. This account report contains transactions resulting from the query parameters.  &#39;booked&#39; shall be contained if bookingStatus parameter is set to \&quot;booked\&quot; or \&quot;both\&quot;.  &#39;pending&#39; is not contained if the bookingStatus parameter is set to \&quot;booked\&quot;. 
 */
@ApiModel(description = "JSON based account report. This account report contains transactions resulting from the query parameters.  'booked' shall be contained if bookingStatus parameter is set to \"booked\" or \"both\".  'pending' is not contained if the bookingStatus parameter is set to \"booked\". ")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-01-08T15:31:32.742+01:00[Europe/Belgrade]")

public class AccountReport   {
  @JsonProperty("booked")
  private TransactionList booked = null;

  @JsonProperty("pending")
  private TransactionList pending = null;

  @JsonProperty("_links")
  private Map _links = null;

  public AccountReport booked(TransactionList booked) {
    this.booked = booked;
    return this;
  }

  /**
   * Get booked
   * @return booked
  **/
  @ApiModelProperty(value = "")

  @Valid

  public TransactionList getBooked() {
    return booked;
  }

  public void setBooked(TransactionList booked) {
    this.booked = booked;
  }

  public AccountReport pending(TransactionList pending) {
    this.pending = pending;
    return this;
  }

  /**
   * Get pending
   * @return pending
  **/
  @ApiModelProperty(value = "")

  @Valid

  public TransactionList getPending() {
    return pending;
  }

  public void setPending(TransactionList pending) {
    this.pending = pending;
  }

  public AccountReport _links(Map _links) {
    this._links = _links;
    return this;
  }

  /**
   * Get _links
   * @return _links
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

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
    AccountReport accountReport = (AccountReport) o;
    return Objects.equals(this.booked, accountReport.booked) &&
        Objects.equals(this.pending, accountReport.pending) &&
        Objects.equals(this._links, accountReport._links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(booked, pending, _links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccountReport {\n");

    sb.append("    booked: ").append(toIndentedString(booked)).append("\n");
    sb.append("    pending: ").append(toIndentedString(pending)).append("\n");
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

