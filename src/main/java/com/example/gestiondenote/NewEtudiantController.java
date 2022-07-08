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
import java.util.ResourceBundle;

public class NewEtudiantController implements Initializable {

    @FXML
    private ChoiceBox<String> sexe;
    @FXML
    private ChoiceBox<String> annee;
    @FXML
    private TextField matricule,nom,adresse;
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
        sexe.setValue("Homme");
        annee.getItems().addAll(anneeValues);
        annee.setValue("L1");
    }
    public void close (){
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }
    public void addEtudiant (){
        String matriculeV = matricule.getText();
        String nomV = nom.getText();
        String adresseV = adresse.getText();
        if ((matriculeV == "") ||
                (nomV == "") ||
                (adresseV =="") ||
                (dateNais.getValue() == null)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setContentText("Une champs du formulaire est vide. Veillez le remplir");
            alert.show();
        } else {
            try {
                String dateN = dateNais.getValue().toString();
                Connection con = DbModel.getConnection();
                PreparedStatement st;
                st = con.prepareStatement("SELECT * FROM `etudiant` WHERE `etudiant`.`matricule`= ? ");
                st.setString(1,matricule.getText());
                ResultSet rs = st.executeQuery();
                if(rs.next()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setContentText("La matricule que vous avez entrée corresponde déja à un étudiant dans la base");
                    alert.show();
                }
                else{
                    st = con.prepareStatement("INSERT INTO `etudiant`" +
                            " (`matricule`, `nom`, `dateNais`, `sexe`, `annee`, `adresse`)" +
                            " VALUES ( ?, ?, ?, ?, ?, ?)");
                    st.setString(1,matriculeV);
                    st.setString(2,nomV);
                    st.setString(3,dateN);
                    st.setString(4,sexe.getValue());
                    st.setString(5,annee.getValue());
                    st.setString(6,adresseV);
                    st.execute();
                    ObservableList<Etudiant> list = FXCollections.observableArrayList();
                    list = DbModel.getEtudiants();
                    tableView.setItems(list);
                    close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
