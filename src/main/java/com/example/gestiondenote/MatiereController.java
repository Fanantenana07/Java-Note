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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MatiereController implements Initializable {
    @FXML
    private TableView<Matiere> matiereTable;
    @FXML
    private TableColumn<Matiere, String> codeMatCol;
    @FXML
    private TableColumn<Matiere,String> libelleCol;
    @FXML
    private TableColumn<Matiere, Integer> coefCol;
    @FXML
    private TableColumn<Matiere, Void> editCol;
    @FXML
    private TableColumn<Matiere,Void> supprCol;

    private ObservableList<Matiere> matiereList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        codeMatCol.setCellValueFactory(new PropertyValueFactory<Matiere,String>("codeMat"));
        libelleCol.setCellValueFactory(new PropertyValueFactory<Matiere,String>("libelle"));
        coefCol.setCellValueFactory(new PropertyValueFactory<Matiere,Integer>("coef"));

        Callback<TableColumn<Matiere,Void>, TableCell<Matiere,Void>> cellfactory = new Callback<TableColumn<Matiere, Void>, TableCell<Matiere, Void>>() {
            @Override
            public TableCell<Matiere, Void> call(TableColumn<Matiere, Void> matiereVoidTableColumn) {
                final TableCell<Matiere, Void> cell = new TableCell<>(){
                    private final Button btn = new Button("Suprimer");
                    {
                        btn.setTextFill(Color.WHITE);
                        btn.setStyle("-fx-background-color : #dc3545;-fx-padding:5px 15px");
                        btn.setOnAction((ActionEvent event) -> {
                            Matiere matiere = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Supprimer");
                            alert.setHeaderText("Supprimer étudiant");
                            alert.setContentText("Etes-vous sûre de supprimer cet étudiant");
                            if (alert.showAndWait().get() == ButtonType.OK){
                                try {
                                    Connection con = DbModel.getConnection();
                                    PreparedStatement st = con.prepareStatement("DELETE FROM `matiere` WHERE `matiere`.`codeMat` = ?");
                                    st.setString(1,matiere.getCodeMat());
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

        Callback <TableColumn<Matiere,Void>, TableCell<Matiere,Void>> cf = new Callback<TableColumn<Matiere, Void>, TableCell<Matiere, Void>>() {
            @Override
            public TableCell<Matiere, Void> call(TableColumn<Matiere, Void> matiereVoidTableColumn) {
                final TableCell<Matiere, Void> cell = new TableCell<>(){
                    private final Button btn = new Button("Modifier");

                    {
                        btn.setTextFill(Color.WHITE);
                        btn.setStyle("-fx-background-color : #f0bc23;-fx-padding:5px 15px");
                        btn.setOnAction((ActionEvent event) -> {
                            Stage stage = new Stage();
                            Parent parent = null;
                            FXMLLoader loader = new FXMLLoader();
                            Matiere matiere = getTableView().getItems().get(getIndex());
                            try {
                                loader.setLocation(getClass().getResource("EditMatiere.fxml"));
                                loader.load();
                                parent = loader.getRoot();
                                EditMatiereController controller = loader.getController();
                                controller.setTable(matiereTable);
                                controller.setMatiere(matiere);
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

        matiereList = DbModel.getMatiere();
        matiereTable.setItems(matiereList);

    }

    private void refresh(){
        matiereList.clear();
        matiereList = DbModel.getMatiere();
        matiereTable.setItems(matiereList);
    }

    public void addMatiere (){
        Stage stage = new Stage();
        Parent parent = null;
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getResource("NewMatiere.fxml"));
            loader.load();
            parent = loader.getRoot();
            NewMatiereController controller = loader.getController();
            controller.setTable(matiereTable);
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
