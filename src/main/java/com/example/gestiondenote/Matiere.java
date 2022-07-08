package com.example.gestiondenote;

public class Matiere {

    private String codeMat;
    private String libelle;
    private int coef;

    public Matiere(String codeMat, String libelle, int coef) {
        this.codeMat = codeMat;
        this.libelle = libelle;
        this.coef = coef;
    }

    public String getCodeMat() {
        return codeMat;
    }

    public void setCodeMat(String codeMat) {
        this.codeMat = codeMat;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getCoef() {
        return coef;
    }

    public void setCoef(int coef) {
        this.coef = coef;
    }
}
