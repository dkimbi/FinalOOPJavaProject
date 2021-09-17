package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Transaction implements Cloneable{

    private Integer id;
    private Double price;
    private Date date;
    private Customer customer;
    private List<Art> selectedArtPieces;

    public Transaction(Integer tId, Customer tCustomer, List tArtSelected) {
        if (tId == null || tId == 0 || tId.toString().length() < 9) {
            throw new InvalidOperationException("Invalid transaction id", id, "Creating the transaction object");
        } else {
            this.id = tId;
        }

        if (tCustomer == null) {
            throw new InvalidOperationException("Invalid customer in the transaction", id, "Creating the transaction object");
        } else {
            this.customer = tCustomer;
        }

        if (tArtSelected == null || tArtSelected.isEmpty()) {
            throw new InvalidOperationException("Invalid selected art pieces in the transaction",
                    id, "Creating the transaction object");
        } else {
            this.selectedArtPieces = tArtSelected;
        }
        this.price = 0.0;
    }
    
    public Transaction(String line){
        String id, price, date, customer, artPieces;
        id = line.substring(line.indexOf("<tid>")+"<tid>".length(), line.indexOf("</tid>"));
        price = line.substring(line.indexOf("<price>")+"<price>".length(), line.indexOf("</price>"));
        date = line.substring(line.indexOf("<date>")+"<date>".length(), line.indexOf("</date>"));
        customer = line.substring(line.indexOf("<customer>")+"<customer>".length(), line.indexOf("</customer>"));
        this.id = Integer.parseInt(id);
        if(price.length() == 0 || price == null){
            this.price = null;
        }else{
            this.price = Double.parseDouble(price);
        }
        if(date.length() == 0 || date == null){
            this.date = null;
        }else{
            try {
                this.date = new SimpleDateFormat("dd/mm/yyyy").parse(date);
            } catch (ParseException ex) {
                Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.customer = new Customer(line);
        this.selectedArtPieces = new ArrayList<>();
        artPieces = line.substring(line.indexOf("<artPieces>") + "<artPieces>".length(), line.indexOf("</artPieces>"));
        String[] arts = artPieces.split("<art>|</art>");
        String type, name;
        for (String art : arts) {
            if (art.length() > 0) {
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
                this.selectedArtPieces.add(artPiece);
            }
        }
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    public void completeTransaction() {
        if (date == null) {
            calculateTotal();
            date = new Date();
        } else {
            throw new InvalidTransactionException("Transaction already complete", id, "Completing transaction");
        }
    }

    public void calculateTotal() {
        this.price = 0.0;
        for (Art artPiece : selectedArtPieces) {
            this.price += artPiece.getPrice() + artPiece.getBasicShippingCost();
        }
    }

    public void addArtPiece(Art artPiece) {
        for (Art art : selectedArtPieces) {
            if (Objects.equals(art.getId(), artPiece.getId())) {
                throw new InvalidOperationException("Duplicate art piece in the transaction", 
                        artPiece.getId(), "Adding new art piece in the transaction");
            }
        }
        selectedArtPieces.add(artPiece);
    }

    public void removeArtPiece(Long artId) {
        Boolean found = false;
        for (Art art : selectedArtPieces) {
            if (Objects.equals(art.getId(), artId)) {
                selectedArtPieces.remove(art);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new InvalidOperationException("Art piece does not exist in the transaction",
                    artId, "Removing art piece from the transaction");
        }
    }

    public Integer getId() {
        return this.id;
    }

    public Double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public void setCustomer(Customer customer) {
        if (customer == null) {
            throw new InvalidOperationException("Invalid customer in the transaction",
                    id, "Setting customer in the transaction");
        } else {
            this.customer = customer;
        }
    }

    public Customer getCustomer() throws CloneNotSupportedException {
        return (Customer) this.customer.clone();
    }

    public List<Art> getSelectedArtPieces() {
        //returns clone of the list
        return new ArrayList<>(selectedArtPieces);
    }

    public String printTransaction() {
        String strPrint = "";
        String strDate = "";
        if (date != null) {
            strDate = new SimpleDateFormat("dd/mm/yyyy").format(date);
        }

        strPrint += "-------------Transaction Details-------------"
                + "\nID: " + id
                + "\nPrice: " + getPrice()
                + "\nDate: " + strDate
                + "\n-------------Customer Details-------------"
                + "\nCustomer ID: " + customer.getId()
                + "\nFirst Name: " + customer.getFirstName()
                + "\nLast Name: " + customer.getLastName()
                + "\nPhone Number: " + customer.getPhoneNumber()
                + "\nEmail: " + customer.getEmail()
                + "Address: " + customer.getMailingAddress().getStreet()
                + ", " + customer.getMailingAddress().getCity()
                + ", " + customer.getMailingAddress().getState()
                + "\nZip Code: " + customer.getMailingAddress().getZipCode()
                + "\n-------------Selected Art Pieces-------------";
        for (Art artPiece : selectedArtPieces) {
            strPrint += "\nID: " + artPiece.getId()
                    + "\nPrice: " + artPiece.getPrice()
                    + "\nYear Created: " + artPiece.getYearCreated()
                    + "\nTitle: " + artPiece.getTitle()
                    + "\nDescription: " + artPiece.getDesc()
                    + "\nAuthor: " + artPiece.getAuthorName();

            if (artPiece instanceof Drawing) {
                Drawing drawing = (Drawing) artPiece;
                strPrint += "\nType: Drawing"
                        + "\nTechnique: " + drawing.getTechnique()
                        + "\nStyle: " + drawing.getStyle()
                        + "\nCategory: " + drawing.getCategory();
            } else if (artPiece instanceof Painting) {
                Painting painting = (Painting) artPiece;
                strPrint += "\nType: Painting"
                        + "\nHeight: " + painting.getHeight()
                        + "\nWidth: " + painting.getWidth()
                        + "\nStyle: " + painting.getStyle()
                        + "\nTechnique: " + painting.getTechnique()
                        + "\nCategory: " + painting.getCategory();
            } else if (artPiece instanceof Print) {
                Print print = (Print) artPiece;
                strPrint += "\nType: Print"
                        + "\nOpen Edition Type: " + print.getOpenEditionType()
                        + "\nCategory: " + print.getCategory();
            } else {
                Sculpture sculpture = (Sculpture) artPiece;
                strPrint += "\nType: Scuplture"
                        + "\nMaterial: " + sculpture.getMaterial()
                        + "\nWeight: " + sculpture.getWeight();
            }
        }
        return strPrint;
    }

    @Override
    public String toString() {
        String str = "";
        String strDate = "";
        if (date != null) {
            strDate = new SimpleDateFormat("dd/mm/yyyy").format(date);
        }
        if(getPrice() == null){
            this.price = 0.0;
        }
        str += "<transaction><tid>" + id + "</tid>"
                + "<price>" + getPrice() + "</price>"
                + "<date>" + strDate + "</date>"
                + customer.toString()
                + "<artPieces>";
        for (Art artPiece : selectedArtPieces) {
            if(artPiece instanceof Drawing){
                Drawing d = (Drawing) artPiece;
                str += d.toString();
            }else if(artPiece instanceof Painting){
                Painting paint = (Painting) artPiece;
                str += paint.toString();
            }else if(artPiece instanceof Print){
                Print print = (Print) artPiece;
                str += print.toString();
            }else{
                Sculpture s = (Sculpture) artPiece;
                str += s.toString();
            }
        }
        return str + "</artPieces></transaction>\n";
    }
}
