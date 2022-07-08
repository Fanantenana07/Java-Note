package com.example.gestiondenote;

public class Bulletin {
    private String libelle;
    private int coef, note, noteP;

    public Bulletin(String libelle, int coef, int note, int noteP) {
        this.libelle = libelle;
        this.coef = coef;
        this.note = note;
        this.noteP = noteP;
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

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getNoteP() {
        return noteP;
    }

    public void setNoteP(int noteP) {
        this.noteP = noteP;
    }
}
