package com.example.gestiondenote;

public class Note {
    private String matricule,codeMat,annee;
    private int note;

    public Note(String matricule, String codeMat, String annee, int note) {
        this.matricule = matricule;
        this.codeMat = codeMat;
        this.annee = annee;
        this.note = note;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getCodeMat() {
        return codeMat;
    }

    public void setCodeMat(String codeMat) {
        this.codeMat = codeMat;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }
}
