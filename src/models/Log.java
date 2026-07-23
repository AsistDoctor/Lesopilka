package models;

public class Log {
    

    private int barrelLength; //Произвольная длинна в метрах
    private TreeType treeType;
    private Diameter diameter;

    public Log(
               TreeType treeType,
               Diameter diameter,
               int barrelLength
              ){
        this.treeType = treeType;
        this.diameter = diameter;
        this.barrelLength = barrelLength;
    }

}
