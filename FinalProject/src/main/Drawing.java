package main;

public class Drawing extends Art{

    private String technique;
    private String style;
    private String category;

    public Drawing(String dTechnique, String dStyle, String dCategory, Long dId, Double dPrice, Integer dYear, String dTitle, String dDesc, String dAuthor) {
        super(dId, dPrice, dYear, dTitle, dDesc, dAuthor);
        if(dTechnique == null || dTechnique.trim().length() == 0){
            throw new InvalidArtException("Invalid technique", dId, dTechnique);
        }else{
            this.technique = dTechnique;
        }
        
        if(dStyle == null || dStyle.trim().length() == 0){
            throw new InvalidArtException("Invalid style", dId, dStyle);
        }else{
            this.style = dStyle;
        }
        
        if(dCategory == null || dCategory.trim().length() == 0){
            throw new InvalidArtException("Invalid category", dId, dCategory);
        }else{
            this.category = dCategory;
        }
    }
    
    public Drawing(String line){
        super(line);
        String root, technique, style, category;
        root = line.substring(line.indexOf("<type>") + "<type>".length(), line.indexOf("</type>"));
        technique = root.substring(root.indexOf("<technique>") + "<technique>".length(), root.indexOf("</technique>"));
        style = root.substring(root.indexOf("<style>") + "<style>".length(), root.indexOf("</style>"));
        category = root.substring(root.indexOf("<category>") + "<category>".length(), root.indexOf("</category>"));
        this.technique = technique;
        this.style = style;
        this.category = category;
    }

    public String getTechnique() {
        return this.technique;
    }

    public String getStyle() {
        return this.style;
    }

    public String getCategory() {
        return this.category;
    }
    
    @Override
    public String toString(){
        return super.toString()+"<type>"
                + "<name>drawing</name>"
                + "<technique>"+technique+"</technique>"
                + "<style>"+style+"</style>"
                + "<category>"+category+"</category>"
                + "</type></art>";
    }
}
