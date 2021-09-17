package main;

public class InvalidInventoryException extends RuntimeException {

    private String reason;
    private String fileName;

    public InvalidInventoryException(String reason, String fileName) {
        super("\nReason: "+reason+", \nFile: "+fileName);
        this.reason = reason;
        this.fileName = fileName;
    }

    public String toString() {
        return "InvalidInventoryException: \nReason: "+reason+" \nFile: "+fileName;
    }
}
