package com.smallworld.data;

import java.util.Objects;

public class Transaction {
  // Represent your transaction data here.

  public int getMtn() {
    return mtn;
  }

  public void setMtn(int mtn) {
    this.mtn = mtn;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public String getSenderFullName() {
    return senderFullName;
  }

  public void setSenderFullName(String senderFullName) {
    this.senderFullName = senderFullName;
  }

  public int getSenderAge() {
    return senderAge;
  }

  public void setSenderAge(int senderAge) {
    this.senderAge = senderAge;
  }

  public String getBeneficiaryFullName() {
    return beneficiaryFullName;
  }

  public void setBeneficiaryFullName(String beneficiaryFullName) {
    this.beneficiaryFullName = beneficiaryFullName;
  }

  public int getBeneficiaryAge() {
    return beneficiaryAge;
  }

  public void setBeneficiaryAge(int beneficiaryAge) {
    this.beneficiaryAge = beneficiaryAge;
  }

  public int getIssueId() {
    return issueId;
  }

  public void setIssueId(int issueId) {
    this.issueId = issueId;
  }

  public boolean isIssueSolved() {
    return issueSolved;
  }

  public void setIssueSolved(boolean issueSolved) {
    this.issueSolved = issueSolved;
  }

  public String getIssueMessage() {
    return issueMessage;
  }

  public void setIssueMessage(String issueMessage) {
    this.issueMessage = issueMessage;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Transaction)) return false;
    Transaction that = (Transaction) o;
    return getMtn() == that.getMtn();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getMtn());
  }

  /**
   * Unique identifier of the transaction
   */
  private int mtn;

  /**
   * Amount in transaction
   */
  private double amount;

  /**
   * Sender information
   */
  private String senderFullName;
  private int senderAge;

  /**
   * Beneficiary information
   */
  private String beneficiaryFullName;
  private int beneficiaryAge;

  /**
   * Issue information
   */
  private int issueId;
  private boolean issueSolved;
  private String issueMessage;
}
