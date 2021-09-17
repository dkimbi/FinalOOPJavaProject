package main;

public class InvalidOperationException extends RuntimeException {

    private String reason;
    private String operation;
    private Long artId;
    private Integer transactionId;
    
    public InvalidOperationException(String reason, Long artId, String operation) {
        super("\nReason: "+reason+"\nArt Piece ID: "+artId+"\nOperation: "+operation);
        this.reason = reason;
        this.artId = artId;
        this.operation = operation;
    }
    
    public InvalidOperationException(String reason, Integer transactionId, String operation) {
        super("\nReason: "+reason+"\nTransaction ID: "+transactionId+"\nOperation: "+operation);
        this.reason = reason;
        this.transactionId = transactionId;
        this.operation = operation;
    }

    public String toString() {
        if(artId != null)
            return "InvalidOperationException:\nReason: "
                +reason+"\nArt Piece ID: "+artId+"\nOperation: "+operation;
        else
            return "InvalidOperationException:\nReason: "
                +reason+"\nTransaction ID: "+transactionId+"\nOperation: "+operation;
    }
}
