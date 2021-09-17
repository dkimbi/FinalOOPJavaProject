package main;

public class Customer implements Cloneable{

    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private MailingAddress mailingAddress;

    public Customer(Integer id, String cfName, String clName, String cPhone, String cEmail, MailingAddress cMailingAddress) {
        if (id == null || id == 0 || id.toString().length() < 9) {
            throw new InvalidOperationException("Invalid customer id", id, "Creating customer object");
        } else {
            this.id = id;
        }
        if (cfName == null || cfName.trim().length() == 0) {
            throw new InvalidOperationException("Invalid customer first name", id, "Creating customer object");
        } else {
            this.firstName = cfName;
        }
        if (clName == null || clName.trim().length() == 0) {
            throw new InvalidOperationException("Invalid customer last name", id, "Creating customer object");
        } else {
            this.lastName = clName;
        }
        if (cPhone == null || cPhone.trim().length() == 0) {
            throw new InvalidOperationException("Invalid customer phone number", id, "Creating customer object");
        } else {
            this.phoneNumber = cPhone;
        }
        if (cEmail == null || cEmail.trim().length() == 0) {
            throw new InvalidOperationException("Invalid customer email", id, "Creating customer object");
        } else {
            this.email = cEmail;
        }
        if (cMailingAddress == null) {
            throw new InvalidOperationException("Invalid customer mailing address", id, "Creating customer object");
        } else {
            this.mailingAddress = cMailingAddress;
        }
    }
    
    public Customer(String line){
        String id, firstName, lastName, phoneNumber, email, mailingAddress;
        id = line.substring(line.indexOf("<cid>")+"<cid>".length(), line.indexOf("</cid>"));
        firstName = line.substring(line.indexOf("<firstName>")+"<firstName>".length(), line.indexOf("</firstName>"));
        lastName = line.substring(line.indexOf("<lastName>")+"<lastName>".length(), line.indexOf("</lastName>"));
        phoneNumber = line.substring(line.indexOf("<phoneNumber>")+"<phoneNumber>".length(), line.indexOf("</phoneNumber>"));
        email = line.substring(line.indexOf("<email>")+"<email>".length(), line.indexOf("</email>"));
        mailingAddress = line.substring(line.indexOf("<mailingAddress>")+"<mailingAddress>".length(), line.indexOf("</mailingAddress>"));
        this.id = Integer.parseInt(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.mailingAddress = new MailingAddress(mailingAddress);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    public void updateCustomerInfo(String firstName, String lastName, String phoneNumber, String email) {
        if (firstName == null || firstName.trim().length() == 0) {
            throw new InvalidOperationException("Invalid customer first name", id, "Updating customer info");
        } else {
            this.firstName = firstName;
        }
        if (lastName == null || lastName.trim().length() == 0) {
            throw new InvalidOperationException("Invalid customer last name", id, "Updating customer info");
        } else {
            this.lastName = lastName;
        }
        if (phoneNumber == null || phoneNumber.trim().length() == 0) {
            throw new InvalidOperationException("Invalid customer phone number", id, "Updating customer info");
        } else {
            this.phoneNumber = phoneNumber;
        }
        if (email == null || email.trim().length() == 0) {
            throw new InvalidOperationException("Invalid customer email", id, "Updating customer info");
        } else {
            this.email = email;
        }
    }

    public void setId(Integer id) {
        if (id == null || id == 0 || id.toString().length() < 9) {
            throw new InvalidOperationException("Invalid customer id", id, "Setting customer id");
        } else {
            this.id = id;
        }
    }

    public Integer getId() {
        return this.id;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().length() == 0) {
            throw new InvalidOperationException("Invalid customer first name", id, "Setting customer first name");
        } else {
            this.firstName = firstName;
        }
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().length() == 0) {
            throw new InvalidOperationException("Invalid customer last name", id, "Setting customer last name");
        } else {
            this.lastName = lastName;
        }
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().length() == 0) {
            throw new InvalidOperationException("Invalid customer phone number", id, "Setting customer phone number");
        } else {
            this.phoneNumber = phoneNumber;
        }
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().length() == 0) {
            throw new InvalidOperationException("Invalid customer email", id, "Setting customer email");
        } else {
            this.email = email;
        }
    }

    public String getEmail() {
        return this.email;
    }

    public void setMailingAddress(MailingAddress mailingAddress) {
        if (mailingAddress == null) {
            throw new InvalidOperationException("Invalid customer mailing address", id, "Setting customer mailing address");
        } else {
            this.mailingAddress = mailingAddress;
        }
    }

    public MailingAddress getMailingAddress() {
        return this.mailingAddress;
    }

    @Override
    public String toString() {
        return "<customer>"
                + "<cid>" + id + "</cid>"
                + "<firstName>" + firstName + "</firstName>"
                + "<lastName>" + lastName + "</lastName>"
                + "<phoneNumber>" + phoneNumber + "</phoneNumber>"
                + "<email>" + email + "</email>"
                + mailingAddress.toString()
                + "</customer>";
    }
}
