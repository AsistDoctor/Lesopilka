package models;

public class Workpiece {

    private final int length;
    private final TreeType treeType;
    private final int diameterMm;

    public Workpiece(TreeType treeType, int diameterMm, int length) {
        this.treeType = treeType;
        this.diameterMm = diameterMm;
        this.length = length;
    }

    public TreeType getTreeType() {
        return treeType;
    }

    public int getDiameterMm() {
        return diameterMm;
    }

    public int getLength() {
        return length;
    }
}