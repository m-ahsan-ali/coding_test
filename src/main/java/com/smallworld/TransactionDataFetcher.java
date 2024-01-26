package com.smallworld;

import com.smallworld.data.Transaction;

import java.util.*;

public class TransactionDataFetcher {

    /**
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount(List<Transaction> transactions) {
        Set<Integer> mtnSet = new HashSet<>();
        double sum = 0.0;
        try {
            for(Transaction transaction : transactions) {
                if(mtnSet.add(transaction.getMtn())) {
                    sum += transaction.getAmount();
                }
            }
            return sum;
        } catch (Exception ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(List<Transaction> transactions, String senderFullName) {
        Set<Integer> mtnSet = new HashSet<>();
        double sum = 0.0;
        try {
            for(Transaction transaction : transactions) {
                if(transaction.getSenderFullName().equals(senderFullName) && mtnSet.add(transaction.getMtn())) {
                    sum += transaction.getAmount();
                }
            }
            return sum;
        } catch (Exception ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount(List<Transaction> transactions) {
        double maxAmount = 0.0;
        Set<Integer> mtnSet = new HashSet<>();
        try {
            for(Transaction transaction : transactions) {
                if(mtnSet.add(transaction.getMtn())) {
                    double transactionAmount = transaction.getAmount();
                    if(transactionAmount > maxAmount) {
                        maxAmount = transactionAmount;
                    }
                }
            }
            return maxAmount;
        } catch (Exception ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients(List<Transaction> transactions) {
        Set<String> uniqueClients = new HashSet<>();
        try {
            for(Transaction transaction : transactions) {
                if(transaction.getIssueId() > 0) {
                    uniqueClients.add(transaction.getSenderFullName());
                    uniqueClients.add(transaction.getBeneficiaryFullName());
                }
            }
            return uniqueClients.size();
        } catch (Exception ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(List<Transaction> transactions, String clientFullName) {
        try {
            for(Transaction transaction : transactions) {
                if((transaction.getSenderFullName().equals(clientFullName) || transaction.getBeneficiaryFullName().equals(clientFullName)) && transaction.getIssueId() > 0 && !transaction.isIssueSolved()) {
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, Set<Transaction>> getTransactionsByBeneficiaryName(List<Transaction> transactions) {
        Set<Integer> mtnSet = new HashSet<>();
        Map<String, Set<Transaction>> clientsTransactions = new HashMap<>();
        try {
            for(Transaction transaction : transactions) {
                if(mtnSet.add(transaction.getMtn())) {
                    String beneficiaryFullName = transaction.getBeneficiaryFullName();
                    Set<Transaction> clientTransaction = clientsTransactions.get(beneficiaryFullName);
                    if(clientTransaction == null) {
                        clientTransaction = new HashSet<>();
                    }
                    clientTransaction.add(transaction);
                    clientsTransactions.put(beneficiaryFullName, clientTransaction);
                }
            }
            return clientsTransactions;
        } catch (Exception ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds(List<Transaction> transactions) {
        Set<Integer> identifiers = new HashSet<>();
        try {
            for(Transaction transaction : transactions) {
                if(!transaction.isIssueSolved()) {
                    identifiers.add(transaction.getMtn());
                }
            }
            return identifiers;
        } catch (Exception ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages(List<Transaction> transactions) {
        List<String> messages = new ArrayList<>();
        try {
            for(Transaction transaction : transactions) {
                if(transaction.getIssueId() > 0 && transaction.isIssueSolved()) {
                    messages.add(transaction.getIssueMessage());
                }
            }
            return messages;
        } catch (Exception ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    /**
     * Returns the 3 transactions with the highest amount sorted by amount descending
     */
    public List<Transaction> getTop3TransactionsByAmount(List<Transaction> transactions) {
        Set<Integer> identifiers = new HashSet<>();
        List<Transaction> top3Transactions = new ArrayList<>(Arrays.asList(transactions.get(0), transactions.get(0), transactions.get(0)));
        try {
            for(Transaction transaction : transactions) {
                if(identifiers.add(transaction.getMtn())) {
                    double tranAmount = transaction.getAmount();
                    for(int i=0; i<top3Transactions.size(); i++) {
                        if(tranAmount > top3Transactions.get(i).getAmount()) {
                            if(i>0 && !(tranAmount < top3Transactions.get(i-1).getAmount())) {
                                continue;
                            }
                            top3Transactions.set(i, transaction);
                        }
                    }
                }
            }
            return top3Transactions;
        } catch (Exception ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    /**
     * Returns the senderFullName of the sender with the most total sent amount
     */
    public String getTopSender(List<Transaction> transactions) {
        Set<Integer> identifiers = new HashSet<>();
        Map<String, Double> topSenders = new HashMap<>();
        for(Transaction transaction : transactions) {
            if(identifiers.add(transaction.getMtn())){
                String name = transaction.getSenderFullName();
                double amount = transaction.getAmount();
                if(topSenders.containsKey(name)) {
                    amount += topSenders.get(name);
                }
                topSenders.put(name, amount);
            }
        }
        Map.Entry<String, Double> maxEntry = null;
        for (Map.Entry<String, Double> entry : topSenders.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }

}
