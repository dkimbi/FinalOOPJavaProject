package main;

public class MailingAddress {

    private String street;
    private String city;
    private String state;
    private String zipCode;

    public MailingAddress(String mStreet, String mCity, String mState, String mZip) {
        if (mStreet == null || mStreet.trim().length() == 0) {
            throw new InvalidOperationException("Invalid street address", 0, "Creating mailing address object");
        } else {
            this.street = mStreet;
        }

        if (mCity == null || mCity.trim().length() == 0) {
            throw new InvalidOperationException("Invalid city address", 0, "Creating mailing address object");
        } else {
            this.city = mCity;
        }

        if (mState == null || mState.trim().length() == 0) {
            throw new InvalidOperationException("Invalid state address", 0, "Creating mailing address object");
        } else {
            this.state = mState;
        }

        if (mZip == null || mZip.trim().length() == 0) {
            throw new InvalidOperationException("Invalid zip code of address", 0, "Creating mailing address object");
        } else {
            this.zipCode = mZip;
        }
    }
    
    public MailingAddress(String line){
        String street, city, state, zipCode;
        street = line.substring(line.indexOf("<street>")+"<street>".length(), line.indexOf("</street>"));
        city = line.substring(line.indexOf("<city>")+"<city>".length(), line.indexOf("</city>"));
        state = line.substring(line.indexOf("<state>")+"<state>".length(), line.indexOf("</state>"));
        zipCode = line.substring(line.indexOf("<zipCode>")+"<zipCode>".length(), line.indexOf("</zipCode>"));
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public void setStreet(String street) {
        if (street == null || street.trim().length() == 0) {
            throw new InvalidOperationException("Invalid street address", 0, "Setting the street of address");
        } else {
            this.street = street;
        }
    }

    public String getStreet() {
        return this.street;
    }

    public void setCity(String city) {
        if (city == null || city.trim().length() == 0) {
            throw new InvalidOperationException("Invalid city address", 0,"Setting the city of address");
        } else {
            this.city = city;
        }
    }

    public String getCity() {
        return this.city;
    }

    public void setState(String state) {
        if (state == null || state.trim().length() == 0) {
            throw new InvalidOperationException("Invalid state address", 0, "Setting state of address");
        } else {
            this.state = state;
        }
    }

    public String getState() {
        return this.state;
    }

    public void setZipCode(String zipCode) {
        if (zipCode == null || zipCode.trim().length() == 0) {
            throw new InvalidOperationException("Invalid zip code of address", 0, "Setting zip code of address");
        } else {
            this.zipCode = zipCode;
        }
    }

    public String getZipCode() {
        return this.zipCode;
    }

    @Override
    public String toString() {
        return "<mailingAddress>"
                + "<street>" + street + "</street>"
                + "<city>" + city + "</city>"
                + "<state>" + state + "</state>"
                + "<zipCode>" + zipCode + "</zipCode>"
                + "</mailingAddress>";
    }
}
