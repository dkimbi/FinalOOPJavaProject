package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Manager implements Cloneable {

    private Inventory inventory;
    private List<Transaction> listOfTransactions;
    private static final String FILE_NAME = "E:/transactions.xml";

    public Manager() {
        inventory = new Inventory();
        listOfTransactions = new ArrayList<>();
        readFromFile();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void addTransaction(Transaction newTransaction) throws CloneNotSupportedException {
        Boolean found = false;
        for (Transaction transaction : listOfTransactions) {
            if (Objects.equals(newTransaction.getId(), transaction.getId())) {
                found = true;
                break;
            }
        }
        if (found) {
            throw new InvalidOperationException("Duplicate transaction", newTransaction.getId(), "Adding new transaction");
        } else {
            if (newTransaction.getDate() == null && (newTransaction.getPrice() == 0.0)) {
                listOfTransactions.add((Transaction) newTransaction.clone());
                saveToFile();
            } else {
                throw new InvalidOperationException("Transaction already completed", newTransaction.getId(), "Adding new transaction");
            }
        }
    }

    public void completeTransaction(Integer transactionId) {
        Boolean found = false;
        Transaction transaction = null;
        for (Transaction tr : listOfTransactions) {
            if (Objects.equals(transactionId, tr.getId())) {
                transaction = tr;
                found = true;
                break;
            }
        }
        if (found) {
            Integer count = 0;
            for (Art art : transaction.getSelectedArtPieces()) {
                for (Art artPiece : inventory.getListOfArt()) {
                    if (Objects.equals(art.getId(), artPiece.getId())) {
                        count++;
                    }
                }
            }
            if (count == transaction.getSelectedArtPieces().size()) {
                transaction.completeTransaction();
                for (Art selectedArtPiece : transaction.getSelectedArtPieces()) {
                    inventory.removeArtPiece(selectedArtPiece.getId());
                }
                saveToFile();
            } else {
                throw new InvalidTransactionException("All Selected art pieces are not present in the inventory",
                        transactionId, "Completing transaction");
            }
        } else {
            throw new InvalidTransactionException("Transaction not found", transactionId, "Completing transaction");
        }
    }

    public void removeTransaction(Long transactionId) {
        Boolean found = false;
        for (Transaction transaction : listOfTransactions) {
            if (Objects.equals(transaction.getId(), transactionId)) {
                listOfTransactions.remove(transaction);
                saveToFile();
                found = true;
                break;
            }
        }

        if (!found) {
            throw new InvalidOperationException("Transaction does not exist", transactionId, "Removing transaction");
        }
    }

    public void removeArtPiece(Long artId) {
        inventory.removeArtPiece(artId);
    }

    public Transaction retrieveTransactionById(Integer transactionId) throws CloneNotSupportedException {
        Transaction transaction = null;
        for (Transaction tr : listOfTransactions) {
            if (Objects.equals(tr.getId(), transactionId)) {
                transaction = tr;
                break;
            }
        }
        return (Transaction) transaction.clone();
    }

    public Transaction retrieveTransactionByArtId(Long artId) throws CloneNotSupportedException {
        Transaction transaction = null;
        for (Transaction tr : listOfTransactions) {
            for (Art artPiece : tr.getSelectedArtPieces()) {
                if (Objects.equals(artPiece.getId(), artId)) {
                    transaction = tr;
                    break;
                }
            }
        }
        return (Transaction) transaction.clone();
    }

    public List<Transaction> retrieveTransactionByEmail(String email) throws CloneNotSupportedException {
        List<Transaction> list = new ArrayList<>();
        for (Transaction tr : listOfTransactions) {
            if (Objects.equals(tr.getCustomer().getEmail(), email)) {
                list.add(tr);
            }
        }
        return new ArrayList<>(list);
    }

    public List<Transaction> retrieveTransactionByDate(Date date) {
        List<Transaction> list = new ArrayList<>();
        for (Transaction tr : listOfTransactions) {
            if (Objects.equals(tr.getDate(), date)) {
                list.add(tr);
            }
        }
        return new ArrayList<>(list);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public List<Transaction> getListOfTransaction() {
        //returns clone of the list
        return new ArrayList<>(listOfTransactions);
    }

    public void changeCustomer(Long transactionId, Customer customer) {
        Boolean found = false;
        for (int i = 0; i < listOfTransactions.size(); i++) {
            Transaction tr = listOfTransactions.get(i);
            if (Objects.equals(tr.getId(), transactionId)) {
                if (tr.getDate() == null) {
                    tr.setCustomer(customer);
                    listOfTransactions.set(i, tr);
                    saveToFile();
                } else {
                    throw new InvalidOperationException("Can not change the customer, transaction already completed",
                            transactionId, "Adding new art piece in transaction");
                }
                found = true;
                break;
            }
        }

        if (!found) {
            throw new InvalidOperationException("Transaction does not exist", transactionId, "Changing customer of transaction");
        }
    }

    public void addArtToTransaction(Long transactionId, Art art) {
        Boolean found = false;
        for (Transaction tr : listOfTransactions) {
            if (Objects.equals(tr.getId(), transactionId)) {
                if (tr.getDate() == null) {
                    tr.addArtPiece(art);
                    saveToFile();
                } else {
                    throw new InvalidOperationException("Can not add art piece, transaction already completed",
                            transactionId, "Adding new art piece in transaction");
                }
                found = true;
                break;
            }
        }

        if (!found) {
            throw new InvalidOperationException("Transaction does not exist", transactionId,
                    "Adding new art piece in transaction");
        }
    }

    public void removeArtFromTransaction(Long transactionId, Long artId) {
        Boolean found = false;
        for (Transaction tr : listOfTransactions) {
            if (Objects.equals(tr.getId(), transactionId)) {
                if (tr.getDate() == null) {
                    tr.removeArtPiece(artId);
                    saveToFile();
                } else {
                    throw new InvalidOperationException("Can not remove art piece, transaction already completed",
                            transactionId, "Adding new art piece in transaction");
                }
                found = true;
                break;
            }
        }

        if (!found) {
            throw new InvalidOperationException("Transaction does not exist", transactionId,
                    "Removing art piece from transaction");
        }
    }

    public void addArt(Art artPiece) {
        inventory.addNewArtPiece(artPiece);
    }

    private void saveToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(FILE_NAME)));
            for (Transaction transaction : listOfTransactions) {
                bw.write(transaction.toString());
            }
            bw.close();
        } catch (IOException ex) {
            throw new InvalidInventoryException(ex.getMessage(), FILE_NAME);
        }
        System.out.println("Transactions saved successfully.");
    }

    private void readFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(FILE_NAME)));
            String transaction, line = "";
            while ((line = br.readLine()) != null) {
                if (line.length() > 0) {
                    transaction = line.substring(line.indexOf("<transaction>") + "<transaction>".length(), line.indexOf("</transaction>"));
                    this.listOfTransactions.add(new Transaction(transaction));
                }
            }
        } catch (FileNotFoundException ex) {
            throw new InvalidInventoryException("File not found exception", FILE_NAME);
        } catch (IOException ex) {
            throw new InvalidInventoryException("Error in reading the file", FILE_NAME);
        }
        System.out.println("Transactions loaded successfully.");
    }
}
