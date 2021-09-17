package main;

public class Print extends Art{

    private String openEditionType;
    private String category;

    public Print(String pEdition, String pCategory, Long pId, Double pPrice, Integer pYear, String pTitle, String pDesc, String pAuthor) {
        super(pId, pPrice, pYear, pTitle, pDesc, pAuthor);
        if(pEdition == null || pEdition.trim().length() == 0){
            throw new InvalidArtException("Invalid opening edition type", pId, pEdition);
        }else{
            this.openEditionType = pEdition;
        }
        
        if(pCategory == null || pCategory.trim().length() == 0){
            throw new InvalidArtException("Invalid category", pId, pCategory);
        }else{
            this.category = pCategory;
        }
    }
    
    public Print(String line){
        super(line);
        String root, edition, category;
        root = line.substring(line.indexOf("<type>") + "<type>".length(), line.indexOf("</type>"));
        edition = root.substring(root.indexOf("<openEditionType>") + "<openEditionType>".length(), root.indexOf("</openEditionType>"));
        category = root.substring(root.indexOf("<category>") + "<category>".length(), root.indexOf("</category>"));
        this.openEditionType = edition;
        this.category = category;
    }

    public String getOpenEditionType() {
        return this.openEditionType;
    }

    public String getCategory() {
        return this.category;
    }
    
    @Override
    public String toString(){
        return super.toString()+"<type>"
                + "<name>print</name>"
                + "<openEditionType>"+openEditionType+"</openEditionType>"
                + "<category>"+category+"</category>"
                + "</type></art>";
    }
}
