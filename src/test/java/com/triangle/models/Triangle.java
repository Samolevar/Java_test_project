package com.triangle.models;

public class Triangle {
    private float aSide;
    private float bSIde;
    private float cSide;

    public Triangle(float aSide, float bSIde, float cSide) {
        this.aSide = Math.abs(aSide);
        this.bSIde = Math.abs(bSIde);
        this.cSide = Math.abs(cSide);
    }

    public float GetPerimeter(){
        return aSide + bSIde + cSide;
    }

    public float GetArea(){
        double x = (aSide + bSIde + cSide) / 2.0;
        return (float) Math.sqrt(x*(x-aSide)*(x-bSIde)*(x-cSide));
    }

    public float getaSide() {
        return aSide;
    }

    public void setaSide(float aSide) {
        this.aSide = aSide;
    }

    public float getbSIde() {
        return bSIde;
    }

    public void setbSIde(float bSIde) {
        this.bSIde = bSIde;
    }

    public float getcSide() {
        return cSide;
    }

    public void setcSide(float cSide) {
        this.cSide = cSide;
    }
}
