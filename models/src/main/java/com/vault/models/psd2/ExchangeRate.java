package com.vault.models.psd2;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Exchange Rate
 */
@ApiModel(description = "Exchange Rate")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-01-08T15:31:32.742+01:00[Europe/Belgrade]")

public class ExchangeRate   {
  @JsonProperty("sourceCurrency")
  private String sourceCurrency = null;

  @JsonProperty("rate")
  private String rate = null;

  @JsonProperty("unitCurrency")
  private String unitCurrency = null;

  @JsonProperty("targetCurrency")
  private String targetCurrency = null;

  @JsonProperty("rateDate")
  private LocalDate rateDate = null;

  @JsonProperty("rateContract")
  private String rateContract = null;

  public ExchangeRate sourceCurrency(String sourceCurrency) {
    this.sourceCurrency = sourceCurrency;
    return this;
  }

  /**
   * Get sourceCurrency
   * @return sourceCurrency
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

@Pattern(regexp="[A-Z]{3}") 
  public String getSourceCurrency() {
    return sourceCurrency;
  }

  public void setSourceCurrency(String sourceCurrency) {
    this.sourceCurrency = sourceCurrency;
  }

  public ExchangeRate rate(String rate) {
    this.rate = rate;
    return this;
  }

  /**
   * Get rate
   * @return rate
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getRate() {
    return rate;
  }

  public void setRate(String rate) {
    this.rate = rate;
  }

  public ExchangeRate unitCurrency(String unitCurrency) {
    this.unitCurrency = unitCurrency;
    return this;
  }

  /**
   * Get unitCurrency
   * @return unitCurrency
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getUnitCurrency() {
    return unitCurrency;
  }

  public void setUnitCurrency(String unitCurrency) {
    this.unitCurrency = unitCurrency;
  }

  public ExchangeRate targetCurrency(String targetCurrency) {
    this.targetCurrency = targetCurrency;
    return this;
  }

  /**
   * Get targetCurrency
   * @return targetCurrency
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

@Pattern(regexp="[A-Z]{3}") 
  public String getTargetCurrency() {
    return targetCurrency;
  }

  public void setTargetCurrency(String targetCurrency) {
    this.targetCurrency = targetCurrency;
  }

  public ExchangeRate rateDate(LocalDate rateDate) {
    this.rateDate = rateDate;
    return this;
  }

  /**
   * Get rateDate
   * @return rateDate
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public LocalDate getRateDate() {
    return rateDate;
  }

  public void setRateDate(LocalDate rateDate) {
    this.rateDate = rateDate;
  }

  public ExchangeRate rateContract(String rateContract) {
    this.rateContract = rateContract;
    return this;
  }

  /**
   * Get rateContract
   * @return rateContract
  **/
  @ApiModelProperty(value = "")


  public String getRateContract() {
    return rateContract;
  }

  public void setRateContract(String rateContract) {
    this.rateContract = rateContract;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExchangeRate exchangeRate = (ExchangeRate) o;
    return Objects.equals(this.sourceCurrency, exchangeRate.sourceCurrency) &&
        Objects.equals(this.rate, exchangeRate.rate) &&
        Objects.equals(this.unitCurrency, exchangeRate.unitCurrency) &&
        Objects.equals(this.targetCurrency, exchangeRate.targetCurrency) &&
        Objects.equals(this.rateDate, exchangeRate.rateDate) &&
        Objects.equals(this.rateContract, exchangeRate.rateContract);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sourceCurrency, rate, unitCurrency, targetCurrency, rateDate, rateContract);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExchangeRate {\n");

    sb.append("    sourceCurrency: ").append(toIndentedString(sourceCurrency)).append("\n");
    sb.append("    rate: ").append(toIndentedString(rate)).append("\n");
    sb.append("    unitCurrency: ").append(toIndentedString(unitCurrency)).append("\n");
    sb.append("    targetCurrency: ").append(toIndentedString(targetCurrency)).append("\n");
    sb.append("    rateDate: ").append(toIndentedString(rateDate)).append("\n");
    sb.append("    rateContract: ").append(toIndentedString(rateContract)).append("\n");
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

