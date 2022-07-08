package com.example.gestiondenote;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditEtudiantController implements Initializable {

    @FXML
    private ChoiceBox<String> sexe;
    @FXML
    private ChoiceBox<String> annee;
    @FXML
    private TextField nom,adresse;
    @FXML
    private Label matricule;
    @FXML
    private DatePicker dateNais;
    @FXML
    private AnchorPane pane;


    private TableView<Etudiant> tableView;
    private String [] sexeValues = {"Homme", "Femme"};
    private String [] anneeValues = {"L1","L2","L3","M1","M2"};
    public void setTable (TableView<Etudiant> t){
        tableView = t;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sexe.getItems().addAll(sexeValues);
        annee.getItems().addAll(anneeValues);
    }
    public void close (){
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }
    public void setEtudiant(Etudiant etudiant){
        matricule.setText(etudiant.getMatricule());
        nom.setText(etudiant.getNom());
        adresse.setText(etudiant.getAdresse());
        sexe.setValue(etudiant.getSexe());
        annee.setValue(etudiant.getAnnee());
        dateNais.setValue(LocalDate.parse(etudiant.getDateNais()));
    }
    public void editEtudiant (){
        try {
            Connection con = DbModel.getConnection();
            PreparedStatement st = con.prepareStatement("UPDATE `etudiant` SET " +
                    "`matricule` = ?, `nom` = ?, `dateNais` = ?," +
                    " `sexe` = ?, `annee` = ?,`adresse` = ? WHERE `etudiant`.`matricule` = ?");
            st.setString(1,matricule.getText());
            st.setString(2,nom.getText());
            st.setString(3,dateNais.getValue().toString());
            st.setString(4,sexe.getValue());
            st.setString(5,annee.getValue());
            st.setString(6,adresse.getText());
            st.setString(7,matricule.getText());
            st.execute();
            ObservableList<Etudiant> list = FXCollections.observableArrayList();
            list = DbModel.getEtudiants();
            tableView.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
    }
}
