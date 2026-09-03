package models;

public class Workpiece {


    private int length; //Произвольная длинна в метрах
    private TreeType treeType;
    private Diameter diameter;

    public Workpiece(
               TreeType treeType,
               Diameter diameter,
               int length
              ){
        this.treeType = treeType;
        this.diameter = diameter;
        this.length = length;
    }

    public TreeType getTreeType() {
        return treeType;
    }
    public Diameter getDiameter() {
        return  diameter;
    }
    public int getLength() {
        return length;
    }
}
