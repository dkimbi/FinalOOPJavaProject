package main;

public class InvalidTransactionException extends RuntimeException {

    private String reason;
    private String operation;
    private Integer id;

    public InvalidTransactionException(String reason, Integer id, String operation) {
        super("\nReason: "+reason+"\nTransaction ID: "+id+"\nOperation: "+operation);
        this.id = id;
        this.reason = reason;
        this.operation = operation;
    }

    public String toString() {
        return "InvalidTransacionException:\nReason"+reason+
                "\nTransaction ID: "+id+"\nOperation: "+operation;
    }
}
