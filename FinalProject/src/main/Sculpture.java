package main;

public class Sculpture extends Art{

    private String material;
    private Integer weight;

    public Sculpture(String sMaterial, Integer sWeight, Long sId, Double sPrice, Integer sYear, String sTitle, String sDesc, String sAuthor) {
        super(sId, sPrice, sYear, sTitle, sDesc, sAuthor);
        if(sMaterial == null || sMaterial.trim().length() == 0){
            throw new InvalidArtException("Invalid material", sId, sMaterial);
        }else{
            this.material = sMaterial;
        }
        
        if(sWeight == null || sWeight == 0){
            throw new InvalidArtException("Invalid weight", sId, sWeight+"");
        }else{
            this.weight = sWeight;
        }
    }
    
    public Sculpture(String line){
        super(line);
        String root, material, weight;
        root = line.substring(line.indexOf("<type>")+"<type>".length(), line.indexOf("</type>"));
        material = root.substring(root.indexOf("<material>")+"<material>".length(), root.indexOf("</material>"));
        weight = root.substring(root.indexOf("<weight>")+"<weight>".length(), root.indexOf("</weight>"));
        this.material = material;
        this.weight = Integer.parseInt(weight);
    }

    @Override
    public Double calculateShipping() {
        Double shipping = super.getBasicShippingCost();
        for (int i = 0; i < weight; i++) {
            shipping += 0.20;
        }
        return shipping;
    }

    public String getMaterial() {
        return this.material;
    }

    public Integer getWeight() {
        return this.weight;
    }
    
    @Override
    public String toString(){
        return super.toString()+"<type>"
                + "<name>sculpture</name>"
                + "<material>"+material+"</material>"
                + "<weight>"+weight+"</weight>"
                + "</type></art>";
    }
}
