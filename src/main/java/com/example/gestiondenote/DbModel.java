package com.example.gestiondenote;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DbModel {
    public static Connection getConnection (){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projet","root","");
            return con;
        } catch (SQLException e) {
            e.printStackTrace();
            return  null;
        }
    }

    public static ObservableList<Etudiant> getEtudiants(){
        ObservableList <Etudiant> list = FXCollections.observableArrayList();
        Etudiant et;

        try {
            Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM `etudiant`");
            while (rs.next()){
                et = new Etudiant(rs.getString("matricule"),
                                rs.getString("nom"),
                                rs.getString("dateNais"),
                                rs.getString("sexe"),
                                rs.getString("annee"),
                                rs.getString("adresse"));
                list.add(et);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static ObservableList<Matiere> getMatiere(){
        ObservableList <Matiere> list = FXCollections.observableArrayList();
        Matiere mat;

        try {
            Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM `matiere`");
            while (rs.next()){
                mat = new Matiere(rs.getString("codeMat"),
                                rs.getString("libelle"),
                                rs.getInt("coef"));
                list.add(mat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static ObservableList<Note> getNote(){
        ObservableList<Note> list = FXCollections.observableArrayList();
        Note  note;
        try {
            Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM `note`");
            while (rs.next()){
                note = new Note(rs.getString("matricule"),
                                rs.getString("codeMat"),
                                rs.getString("annee"),
                                rs.getInt("note"));
                list.add(note);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static ResultSet getMatricule(){
        try {
            Connection con = DbModel.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT matricule FROM etudiant");
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static double getMoyenne (String matricule, String annee){

        double moyenne =0.0, coefSum = 0.0;
        int note, noteSum =0, coef, noteP;
        try {
            Connection con = getConnection();
            PreparedStatement st = con.prepareStatement("SELECT * FROM `note`,`matiere` WHERE " +
                    "`note`.`codeMat` = `matiere`.`codeMat` AND `note`.`annee` = ? AND `note`.`matricule` = ? ");
            st.setString(1,annee);
            st.setString(2,matricule);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                note = rs.getInt("note");
                coef = rs.getInt("coef");
                coefSum += coef;
                noteP = note * coef;
                noteSum += noteP;
            }
            if (coefSum != 0){
                moyenne = noteSum / coefSum;
                return moyenne;
            }
            return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

}
