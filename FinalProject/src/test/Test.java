package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Art;
import main.Customer;
import main.InvalidInventoryException;
import main.InvalidTransactionException;
import main.Inventory;
import main.MailingAddress;
import main.Manager;
import main.Print;
import main.Transaction;

public class Test {

    public static void testInventoryException() {
        try {
            Manager manager = new Manager();
        } catch (InvalidInventoryException ie) {
            System.err.println(ie.toString());
        }
    }

    public static void testInventoryLoading() {
        Manager manager = new Manager();
        printInventory(manager);
    }

    public static void testAddArtToInventory() {
        Manager manager = new Manager();
        Art art = new Print("1st Edition", "Drama",
                8278121289L, 100.00, 2017, "The First Print", "This a print art piece", "Adam");
        manager.addArt(art);
        printInventory(manager);
    }

    public static void testRemoveArtValidFromInventory() {
        Manager manager = new Manager();
        System.out.println("Printing inventory before removing an art piece");
        printInventory(manager);
        System.out.println("Removing an art piece by valid input");
        manager.removeArtPiece(8278121289L);
        System.out.println("Printing inventory after removing an art piece");
        printInventory(manager);
    }

    public static void testRemoveArtInvalidFromInventory() {
        Manager manager = new Manager();
        System.out.println("Removing an art piece by invalid input");
        manager.removeArtPiece(1111111111L);
    }

    public static void testAddTransaction() throws CloneNotSupportedException {
        Manager manager = new Manager();

        Inventory inventory = manager.getInventory();
        List<Art> artPieces = new ArrayList<>();
        artPieces.add(inventory.getListOfArt().get(0));
        MailingAddress mailingAddress = new MailingAddress("12th", "MyCity", "MyState", "33787");
        Customer customer = new Customer(987654321, "Albert", "albert", "112233445566", "wxy@abc.com", mailingAddress);
        Transaction transaction = new Transaction(234567891, customer, artPieces);
        manager.addTransaction(transaction);

        List<Transaction> transactions = manager.retrieveTransactionByEmail(customer.getEmail());
        System.out.println(transactions.get(0).printTransaction());
        Double artPrice = 0.0;
        for (Art artPiece : artPieces) {
            artPrice += artPiece.getPrice();
        }
        System.out.println("Art pieces price: " + artPrice);
        System.out.println("Transaction price: " + transaction.getPrice());
    }

    public static void testCompleteTransaction() throws CloneNotSupportedException {
        Manager manager = new Manager();
        Inventory inventory = manager.getInventory();
        Art art = inventory.getListOfArt().get(0);
        List<Art> selectedArtPieces = new ArrayList<>();
        selectedArtPieces.add(art);
        MailingAddress mailingAddress = new MailingAddress("12th", "ABC", "DEFG", "45678");
        Customer customer = new Customer(478392883, "Jonathan", "Smith", "112233445566", "def@wxy.com", mailingAddress);
        Transaction transaction = new Transaction(278177234, customer, selectedArtPieces);
        manager.addTransaction(transaction);
        manager.completeTransaction(transaction.getId());
        List<Transaction> transactions = manager.retrieveTransactionByEmail(customer.getEmail());
        System.out.println(transactions.get(0).printTransaction());
    }

    public static void testIncompleteTransaction() throws CloneNotSupportedException {
        Manager manager = new Manager();
        Inventory inventory = manager.getInventory();
        Art art = inventory.getListOfArt().get(0);
        List<Art> selectedArtPieces = new ArrayList<>();
        selectedArtPieces.add(art);
        MailingAddress mailingAddress = new MailingAddress("8th", "GHI", "LMNO", "89761");
        Customer customer = new Customer(289174876, "Joseph", "Shawn", "112233445566",
                "wer@qsd.com", mailingAddress);
        Transaction transaction = new Transaction(918734123, customer, selectedArtPieces);
        manager.addTransaction(transaction);
        manager.removeArtPiece(art.getId());
        try {
            manager.completeTransaction(transaction.getId());
        } catch (InvalidTransactionException e) {
            System.err.println(e.toString());
        }
    }

    public static void testRetrieveTransactionFilters() throws CloneNotSupportedException {
        Manager manager = new Manager();
        List<Transaction> transactionsByEmail = manager.retrieveTransactionByEmail("xyz@abc.com");
        for (Transaction transaction : transactionsByEmail) {
            System.out.println(transaction.printTransaction() + "\n");
        }
        try {
            Transaction transactionById = manager.retrieveTransactionById(123456789);
            System.out.println(transactionById.printTransaction() + "\n");
            Transaction transactionByArtId = manager.retrieveTransactionByArtId(1010101010L);
            System.out.println(transactionByArtId.printTransaction() + "\n");
            List<Transaction> transactionsByDate = manager.retrieveTransactionByDate(new Date());
            for (Transaction transaction : transactionsByDate) {
                System.out.println(transaction.printTransaction() + "\n");
            }
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void testTransactionData() throws CloneNotSupportedException{
        Manager manager = new Manager();
        Transaction transaction = manager.retrieveTransactionById(123456789);
        transaction.completeTransaction();
        Transaction transaction1 = manager.retrieveTransactionById(123456789);
        System.out.println("----------Completed transaction----------");
        System.out.println("Transaction ID: "+transaction.getId()+"\nCompletion Date: "+transaction.getDate()+
                "\nPrice: "+transaction.getPrice());
        System.out.println("----------Original Transaction----------");
        System.out.println("Transaction ID: "+transaction1.getId()+"\nCompletion Date: "+transaction1.getDate()+
                "\nPrice: "+transaction1.getPrice());
    }

    public static void printInventory(Manager manager) {
        Inventory inventory = manager.getInventory();
        List<Art> artPieces = inventory.getListOfArt();
        System.out.println("----------------Art pieces in the inventory---------------");
        for (Art artPiece : artPieces) {
            System.out.println("\nID: " + artPiece.getId()
                    + "\nPrice: " + artPiece.getPrice()
                    + "\nYear Created: " + artPiece.getYearCreated()
                    + "\nTitle: " + artPiece.getTitle()
                    + "\nDescription: " + artPiece.getDesc()
                    + "\nAuthor Name: " + artPiece.getAuthorName());
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        testInventoryException();
        testInventoryLoading();
        testAddArtToInventory();
        testRemoveArtValidFromInventory();
        testRemoveArtInvalidFromInventory();
        testAddTransaction();
        testCompleteTransaction();
        testIncompleteTransaction();
        testRetrieveTransactionFilters();
        testTransactionData();
    }
}
