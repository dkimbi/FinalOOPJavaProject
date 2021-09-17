package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Inventory {
    
    public List<Art> listOfArt;
    private static final String FILE_NAME = "E:/inventory.xml";

    public Inventory() {
        listOfArt = new ArrayList<>();
        readFromFile();
    }

    public void addNewArtPiece(Art artPiece) {
        Boolean found = false;
        for (Art art : listOfArt) {
            if (Objects.equals(artPiece.getId(), art.getId())) {
                found = true;
                break;
            }
        }
        if (found) {
            throw new InvalidOperationException("Duplicate art piece in the inventory",
                    artPiece.getId(), "Adding new art piece in the inventory");
        } else {
            listOfArt.add(artPiece);
            saveToFile();
        }
    }

    public void removeArtPiece(Long artId) {
        Boolean found = false;
        for (Art art : listOfArt) {
            if (Objects.equals(artId, art.getId())) {
                listOfArt.remove(art);
                saveToFile();
                found = true;
                break;
            }
        }
        if (!found) {
            throw new InvalidOperationException("Art piece does not exist in the inventory",
                    artId, "Removing art piece from the inventory");
        }
        System.out.println("Art piece removed successfully.");
    }

    public List<Art> getListOfArt() {
        //returns the clone of the list
        return new ArrayList<>(listOfArt);
    }

    private void saveToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(FILE_NAME)));
            for (Art artPieces : listOfArt) {
                bw.write(artPieces.toString());
                bw.newLine();
            }
            bw.close();
        } catch (IOException ex) {
            throw new InvalidInventoryException(ex.getMessage(), FILE_NAME);
        }
        System.out.println("Art pieces saved successfully.");
    }

    private void readFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(FILE_NAME)));
            String art, type, name, line = "";
            while ((line = br.readLine()) != null) {
                art = line.substring(line.indexOf("<art>")+"<art>".length(), line.indexOf("</art>"));
                type = art.substring(art.indexOf("<type>") + "<type>".length(), art.indexOf("</type>"));
                name = type.substring(type.indexOf("<name>") + "<name>".length(), type.indexOf("</name>"));
                Art artPiece = null;
                switch(name){
                    case "drawing":
                        artPiece = new Drawing(art);
                        break;
                    case "painting":
                        artPiece = new Painting(art);
                        break;
                    case "print":
                        artPiece = new Print(art);
                        break;
                    case "sculpture":
                        artPiece = new Sculpture(art);
                        break;
                }
                this.listOfArt.add(artPiece);
            }
        } catch (FileNotFoundException ex) {
            throw new InvalidInventoryException("File not found exception", FILE_NAME);
        } catch (IOException ex) {
            throw new InvalidInventoryException("Error reading the file", FILE_NAME);
        }
        System.out.println("Art pieces loaded successfully.");
    }
}
