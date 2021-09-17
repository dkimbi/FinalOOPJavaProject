package main;

public class InvalidArtException extends RuntimeException {

    private String reason;
    private String data;
    private Long id;

    public InvalidArtException(String reason, Long aid, String data) {
        super("\nReason:"+reason+"\nArt Piece ID: "+aid+"\nData: "+data);
        this.id = aid;
        this.reason = reason;
        this.data = data;
    }

    public String toString() {
        return "InvalidArtException:\nReason: "+reason+
                "\nArt Piece ID: "+id+"\nData: "+data;
    }
}
