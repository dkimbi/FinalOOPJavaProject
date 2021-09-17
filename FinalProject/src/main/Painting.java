package main;

public class Painting extends Art{

    private Double height;
    private Double width;
    private String style;
    private String technique;
    private String category;
        
    public Painting(Double pHeight, Double pWidth, String paintStyle, String paintTechnique, String paintCategory, Long pId, Double pPrice, Integer pYear, String pTitle, String pDesc, String pAuthor) {
        super(pId, pPrice, pYear, pTitle, pDesc, pAuthor);
        if(pHeight == null || pHeight == 0){
            throw new InvalidArtException("Invalid height", pId, pHeight+"");
        }else{
            this.height = pHeight;
        }
        
        if(pWidth == null || pWidth == 0){
            throw new InvalidArtException("Invalid width", pId, pWidth+"");
        }else{
            this.width = pWidth;
        }
        
        if(paintStyle == null || paintStyle.trim().length() == 0){
            throw new InvalidArtException("Invalid style", pId, paintStyle);
        }else{
            this.style = paintStyle;
        }
        
        if(paintTechnique == null || paintTechnique.trim().length() == 0){
            throw new InvalidArtException("Invalid technique", pId, paintTechnique);
        }else{
            this.technique = paintTechnique;
        }
        
        if(paintCategory == null || paintCategory.trim().length() == 0){
            throw new InvalidArtException("Invalid category", pId, paintCategory);
        }else{
            this.category = paintCategory;
        }
    }
    
    public Painting(String line){
        super(line);
        String root, height, width, style, technique, category;
        root = line.substring(line.indexOf("<type>") + "<type>".length(), line.indexOf("</type>"));
        height = root.substring(root.indexOf("<height>") + "<height>".length(), root.indexOf("</height>"));
        width = root.substring(root.indexOf("<width>") + "<width>".length(), root.indexOf("</width>"));
        style = root.substring(root.indexOf("<style>") + "<style>".length(), root.indexOf("</style>"));
        technique = root.substring(root.indexOf("<technique>") + "<technique>".length(), root.indexOf("</technique>"));
        category = root.substring(root.indexOf("<category>") + "<category>".length(), root.indexOf("</category>"));
        this.height = Double.parseDouble(height);
        this.width = Double.parseDouble(width);
        this.style = style;
        this.technique = technique;
        this.category = category;
    }

    @Override
    public Double calculateShipping() {
        Double area = height * width;
        Double shipping = super.getBasicShippingCost();
        if(area < 100){
            shipping += 5.99;
        }else if(area >= 100 && area <= 300){
            shipping += 10.99;
        }else{
            shipping += 15.99;
        }
        return shipping;
    }
    
    

    public Double getHeight() {
        return this.height;
    }

    public Double getWidth() {
        return this.width;
    }

    public String getStyle() {
        return this.style;
    }

    public String getTechnique() {
        return this.technique;
    }

    public String getCategory() {
        return this.category;
    }
    
    @Override
    public String toString(){
        return super.toString()+"<type>"
                + "<name>painting</name>"
                + "<height>"+height+"</height>"
                + "<width>"+width+"</width>"
                + "<style>"+style+"</style>"
                + "<technique>"+technique+"</technique>"
                + "<category>"+category+"</category>"
                + "</type></art>";
    }
}
