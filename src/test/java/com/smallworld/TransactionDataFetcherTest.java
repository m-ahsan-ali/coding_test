package com.smallworld;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.data.Transaction;
import junit.framework.TestCase;

import java.io.File;
import java.util.*;

public class TransactionDataFetcherTest extends TestCase {

  @Override
  protected void setUp() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    File file = new File("./transactions.json");
    JsonNode jsonArray = objectMapper.readValue(file, JsonNode.class);
    String jsonArrayAsString = objectMapper.writeValueAsString(jsonArray);
    transactions = objectMapper.readValue(jsonArrayAsString, new TypeReference<List<Transaction>>() {});
  }

  public void testGetTotalTransactionAmount() {
    TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher();
    double expected = transactionDataFetcher.getTotalTransactionAmount(transactions);
    assertEquals(expected, totalAmount);
  }

  public void testGetTotalTransactionAmountSentBy() {
    TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher();
    double expected = transactionDataFetcher.getTotalTransactionAmountSentBy(transactions, totalAmountSenderName);
    assertEquals(expected, totalAmountSentBy);
  }

  public void testGetMaxTransactionAmount() {
    TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher();
    double expected = transactionDataFetcher.getMaxTransactionAmount(transactions);
    assertEquals(expected, maxTransactionAmount);
  }

  public void testCountUniqueClients() {
    TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher();
    long expected = transactionDataFetcher.countUniqueClients(transactions);
    assertEquals(expected, uniqueClients);
  }

  public void testHasOpenComplianceIssues() {
    TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher();
    boolean expected = transactionDataFetcher.hasOpenComplianceIssues(transactions, totalAmountSenderName);
    assertEquals(expected, false);
    expected = transactionDataFetcher.hasOpenComplianceIssues(transactions, clientWithOpenComplianceIssue);
    assertEquals(expected, true);
  }

  public void testGetTransactionsByBeneficiaryName() {
    Set<String> beneficiaryNames = new HashSet<>();
    for(Transaction transaction : transactions) {
      beneficiaryNames.add(transaction.getBeneficiaryFullName());
    }
    TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher();
    Map<String, Set<Transaction>> clientsTransactions = transactionDataFetcher.getTransactionsByBeneficiaryName(transactions);
    for(String name : beneficiaryNames) {
      Set<Transaction> clientTransaction = clientsTransactions.get(name);
      assertEquals(clientTransaction.size(), 1);
    }
  }

  public void testGetUnsolvedIssueIds() {
    TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher();
    Set<Integer> identifiers = transactionDataFetcher.getUnsolvedIssueIds(transactions);
    assertEquals(identifiers.size(), 4);
  }

  public void testGetAllSolvedIssueMessages() {
    TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher();
    List<String> messages = transactionDataFetcher.getAllSolvedIssueMessages(transactions);
    assertEquals(messages.size(), 3);
  }

  public void testGetTop3TransactionsByAmount() {
    TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher();
    List<Transaction> top3Transactions = transactionDataFetcher.getTop3TransactionsByAmount(transactions);
    assertEquals(top3Transactions.get(0).getAmount(), 985.0);
    assertEquals(top3Transactions.get(1).getAmount(), 666.0);
    assertEquals(top3Transactions.get(2).getAmount(), 430.2);
  }

  public void testGetTopSender() {
    TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher();
    String topSenderName = transactionDataFetcher.getTopSender(transactions);
    assertEquals(topSenderName, topSender);
  }

  private List<Transaction> transactions;
  private double totalAmount = 2889.17;
  private String totalAmountSenderName = "Billy Kimber";
  private double totalAmountSentBy = 459.09;
  private double maxTransactionAmount = 985.0;
  private long uniqueClients = 7;
  private String clientWithOpenComplianceIssue = "Ben Younger";
  private String topSender = "Arthur Shelby";
}
