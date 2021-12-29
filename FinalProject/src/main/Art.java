package main;

public abstract class Art {

    protected Long id;
    protected Double price;
    protected Integer yearCreated;
    protected String title;
    protected String desc;
    protected String authorName;
    protected Double basicShippingCost = 10.99;

    public Art(Long aId, Double aPrice, Integer aYear, String aTitle, String aDesc, String aAuthor) {
        if (aId == null || aId == 0) {
            throw new InvalidArtException("Invalid id", aId, aId + "");
        } else {
            this.id = aId;
        }

        if (aPrice == null || aPrice == 0) {
            throw new InvalidArtException("Invalid price", aId, aPrice + "");
        } else {
            this.price = aPrice;
        }

        if (aYear == null || aYear == 0 || aYear.toString().length() != 4) { // was: aYear.toString().length() > 4 || aYear.toString().length() < 4
            throw new InvalidArtException("Invalid year", aId, aYear + "");
        } else {
            this.yearCreated = aYear;
        }

        if (aTitle == null || aTitle.trim().length() == 0) {
            throw new InvalidArtException("Invalid title", aId, aTitle);
        } else {
            this.title = aTitle;
        }

        if (aDesc == null || aDesc.trim().length() == 0 || aDesc.length() > 500) {  // Limit word count to 500
            throw new InvalidArtException("Invalid description", aId, aDesc);
        } else {
            this.desc = aDesc;
        }

        if (aAuthor == null || aAuthor.trim().length() == 0) {
            throw new InvalidArtException("Invalid author's name", aId, aAuthor);
        } else {
            this.authorName = aAuthor;
        }

    }

    public Art(String line) {
        String id, price, year, title, desc, author;
        id = line.substring(line.indexOf("<aid>") + "<aid>".length(), line.indexOf("</aid>"));
        price = line.substring(line.indexOf("<aprice>") + "<aprice>".length(), line.indexOf("</aprice>"));
        year = line.substring(line.indexOf("<year>") + "<year>".length(), line.indexOf("</year>"));
        title = line.substring(line.indexOf("<title>") + "<title>".length(), line.indexOf("</title>"));
        desc = line.substring(line.indexOf("<desc>") + "<desc>".length(), line.indexOf("</desc>"));
        author = line.substring(line.indexOf("<author>") + "<author>".length(), line.indexOf("</author>"));
        this.id = Long.parseLong(id);
        this.price = Double.parseDouble(price);
        this.yearCreated = Integer.parseInt(year);
        this.title = title;
        this.desc = desc;
        this.authorName = author;
    }

    public Double calculateShipping() {
        return basicShippingCost;
    }

    public Long getId() {
        return this.id;
    }

    public Double getPrice() {
        return this.price;
    }

    public Integer getYearCreated() {
        return this.yearCreated;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public Double getBasicShippingCost() {
        return basicShippingCost;
    }

    @Override
    public String toString() {
        return "<art><aid>" + id + "</aid>"
                + "<aprice>" + price + "</aprice>"
                + "<year>" + yearCreated + "</year>"
                + "<title>" + title + "</title>"
                + "<desc>" + desc + "</desc>"
                + "<author>" + authorName + "</author>";
    }
}
