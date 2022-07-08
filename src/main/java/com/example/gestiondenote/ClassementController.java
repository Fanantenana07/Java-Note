package com.example.gestiondenote;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ClassementController implements Initializable {
    @FXML
    private TableView<Classement> classementTable;
    @FXML
    private TableColumn<Classement, String> matriculeCol;
    @FXML
    private TableColumn<Classement, String> nomCol;
    @FXML
    private TableColumn<Classement, Double> moyenneCol;
    @FXML
    private VBox classement;
    @FXML
    private Label moyenneClasse;
    @FXML
    private ChoiceBox<String> annee;

    private ObservableList<Classement> classementList = FXCollections.observableArrayList();


    public void classement (){
        String an = annee.getValue();
        double moyenne, moyenneSum=0.0;
        int count = 0;
        String nom, matricule;
        Connection con = DbModel.getConnection();
        classementList.clear();
        classement.setVisible(true);
        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM `etudiant` WHERE `etudiant`.`annee` = ?");
            st.setString(1,an);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                nom = rs.getString("nom");
                matricule = rs.getString("matricule");
                moyenne = DbModel.getMoyenne(matricule, an);
                classementList.add(new Classement(matricule,nom,moyenne));
                count++;
                moyenneSum += moyenne;
            }
            classementTable.setItems(classementList);
            moyenneClasse.setText(Double.toString(moyenneSum/count));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        matriculeCol.setCellValueFactory(new PropertyValueFactory<Classement, String>("matricule"));
        nomCol.setCellValueFactory(new PropertyValueFactory<Classement, String>("nom"));
        moyenneCol.setCellValueFactory(new PropertyValueFactory<Classement, Double>("moyenne"));
        String [] an = {"L1","L2","L3","M1","M2"};
        annee.getItems().addAll(an);
        annee.setValue("L1");
    }
}
