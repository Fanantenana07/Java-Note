package com.example.gestiondenote;

public class Etudiant {
    private  String matricule;
    private String nom;
    private String dateNais;
    private String sexe;
    private String annee;
    private String adresse;

    public Etudiant(String matricule, String nom, String dateNais, String sexe, String annee, String adresse) {
        this.matricule = matricule;
        this.nom = nom;
        this.dateNais = dateNais;
        this.sexe = sexe;
        this.annee = annee;
        this.adresse = adresse;
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

    public String getDateNais() {
        return dateNais;
    }

    public void setDateNais(String dateNais) {
        this.dateNais = dateNais;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
