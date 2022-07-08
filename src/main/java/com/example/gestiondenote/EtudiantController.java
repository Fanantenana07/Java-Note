package com.example.gestiondenote;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;



public class EtudiantController implements Initializable {
    @FXML
    private TableView<Etudiant> etudiantTable;
    @FXML
    private TableColumn<Etudiant, String> matriculeCol;
    @FXML
    private TableColumn<Etudiant, String> nomCol;
    @FXML
    private TableColumn<Etudiant, String> dateNaisCol;
    @FXML
    private TableColumn<Etudiant, String> sexeCol;
    @FXML
    private TableColumn<Etudiant, String> anneeCol;
    @FXML
    private TableColumn<Etudiant, String> adresseCol;
    @FXML
    private TableColumn<Etudiant, Void> editCol;
    @FXML
    private TableColumn<Etudiant, Void> supprCol;
    @FXML
    private TextField search;



    private ObservableList<Etudiant> etudiantList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        matriculeCol.setCellValueFactory(new PropertyValueFactory<Etudiant,String>("matricule"));
        nomCol.setCellValueFactory(new PropertyValueFactory<Etudiant,String>("nom"));
        sexeCol.setCellValueFactory(new PropertyValueFactory<Etudiant,String>("sexe"));
        anneeCol.setCellValueFactory(new PropertyValueFactory<Etudiant,String>("annee"));
        dateNaisCol.setCellValueFactory(new PropertyValueFactory<Etudiant,String>("dateNais"));
        adresseCol.setCellValueFactory(new PropertyValueFactory<Etudiant,String>("adresse"));

        Callback<TableColumn<Etudiant,Void>, TableCell<Etudiant,Void>> cellfactory = new Callback<TableColumn<Etudiant, Void>, TableCell<Etudiant, Void>>() {
            @Override
            public TableCell<Etudiant, Void> call(TableColumn<Etudiant, Void> etudiantVoidTableColumn) {
                final TableCell<Etudiant, Void> cell = new TableCell<>(){
                    private final Button btn = new Button("Suprimer");
                    {
                        btn.setTextFill(Color.WHITE);
                        btn.setStyle("-fx-background-color : #dc3545;-fx-padding:5px 15px");
                        btn.setOnAction((ActionEvent event) -> {
                            Etudiant etudiant = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Supprimer");
                            alert.setHeaderText("Supprimer étudiant");
                            alert.setContentText("Etes-vous sûre de supprimer cet étudiant");
                            if (alert.showAndWait().get() == ButtonType.OK){
                                try {
                                    Connection con = DbModel.getConnection();
                                    PreparedStatement st = con.prepareStatement("DELETE FROM `etudiant` WHERE `etudiant`.`matricule` = ?");
                                    st.setString(1,etudiant.getMatricule());
                                    st.execute();
                                    refresh();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        supprCol.setCellFactory(cellfactory);

        Callback <TableColumn<Etudiant,Void>, TableCell<Etudiant,Void>> cf = new Callback<TableColumn<Etudiant, Void>, TableCell<Etudiant, Void>>() {
            @Override
            public TableCell<Etudiant, Void> call(TableColumn<Etudiant, Void> etudiantVoidTableColumn) {
                final TableCell<Etudiant, Void> cell = new TableCell<>(){
                    private final Button btn = new Button("Modifier");

                    {
                        btn.setTextFill(Color.WHITE);
                        btn.setStyle("-fx-background-color : #f0bc23;-fx-padding:5px 15px");
                        btn.setOnAction((ActionEvent event) -> {
                            Stage stage = new Stage();
                            Parent parent = null;
                            FXMLLoader loader = new FXMLLoader();
                            Etudiant etudiant = getTableView().getItems().get(getIndex());
                            try {
                                loader.setLocation(getClass().getResource("EditStudent.fxml"));
                                loader.load();
                                parent = loader.getRoot();
                                EditEtudiantController controller = loader.getController();
                                controller.setTable(etudiantTable);
                                controller.setEtudiant(etudiant);
                                Scene scene = new Scene(parent);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        editCol.setCellFactory(cf);

        etudiantList = DbModel.getEtudiants();
        etudiantTable.setItems(etudiantList);

    }

    private void refresh (){
        etudiantList.clear();
        etudiantList = DbModel.getEtudiants();
        etudiantTable.setItems(etudiantList);
    }

    public void search (){
        try {
            etudiantList.clear();

            String text = search.getText();
            ResultSet rs;
            if (text == "")
                etudiantList = DbModel.getEtudiants();
            else{
            Connection con = DbModel.getConnection();
            Statement st = con.createStatement();
                rs = st.executeQuery("SELECT * FROM etudiant WHERE nom LIKE '%" +
                        text + "%' OR matricule='" + text + "'");
                Etudiant etudiant;
                while (rs.next()){
                    etudiant = new Etudiant(
                            rs.getString("matricule"),
                            rs.getString("nom"),
                            rs.getString("dateNais"),
                            rs.getString("sexe"),
                            rs.getString("annee"),
                            rs.getString("adresse"));
                    etudiantList.add(etudiant);
                }
            }
            etudiantTable.setItems(etudiantList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addEtudiant (){
        Stage stage = new Stage();
        Parent parent = null;
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getResource("NewStudent.fxml"));
            loader.load();
            parent = loader.getRoot();
            NewEtudiantController controller = loader.getController();
            controller.setTable(etudiantTable);
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
