package com.example.gestiondenote;

public class Classement {
    private String matricule, nom;
    private double moyenne;

    public Classement(String matricule, String nom, double moyenne) {
        this.matricule = matricule;
        this.nom = nom;
        this.moyenne = moyenne;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }
}
