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

public class NoteController implements Initializable {
    @FXML
    private TableView<Note> noteTable;
    @FXML
    private TableColumn<Note,String> matriculeCol;
    @FXML
    private TableColumn<Note, String> codeMatCol;
    @FXML
    private TableColumn<Note, Integer> noteCol;
    @FXML
    private TableColumn<Note, String> anneeCol;
    @FXML
    private TableColumn<Note, Void> editCol;
    @FXML
    private TableColumn<Note, Void> supprCol;

    private ObservableList<Note> noteList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        matriculeCol.setCellValueFactory(new PropertyValueFactory<Note,String>("matricule"));
        codeMatCol.setCellValueFactory(new PropertyValueFactory<Note,String>("codeMat"));
        noteCol.setCellValueFactory(new PropertyValueFactory<Note,Integer>("note"));
        anneeCol.setCellValueFactory(new PropertyValueFactory<Note,String>("annee"));

        Callback<TableColumn<Note,Void>, TableCell<Note,Void>> cellfactory = new Callback<TableColumn<Note, Void>, TableCell<Note, Void>>() {
            @Override
            public TableCell<Note, Void> call(TableColumn<Note, Void> noteVoidTableColumn) {
                final TableCell<Note, Void> cell = new TableCell<>(){
                    private final Button btn = new Button("Suprimer");
                    {
                        btn.setTextFill(Color.WHITE);
                        btn.setStyle("-fx-background-color : #dc3545;-fx-padding:5px 15px");
                        btn.setOnAction((ActionEvent event) -> {
                            Note note = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Supprimer");
                            alert.setHeaderText("Supprimer étudiant");
                            alert.setContentText("Etes-vous sûre de supprimer cet note");
                            if (alert.showAndWait().get() == ButtonType.OK){
                                try {
                                    Connection con = DbModel.getConnection();
                                    PreparedStatement st = con.prepareStatement("DELETE FROM `note` WHERE `note`.`matricule` = ? AND `note`.`codeMat` = ?");
                                    st.setString(1,note.getMatricule());
                                    st.setString(2,note.getCodeMat());
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

        Callback <TableColumn<Note,Void>, TableCell<Note,Void>> cf = new Callback<TableColumn<Note, Void>, TableCell<Note, Void>>() {
            @Override
            public TableCell<Note, Void> call(TableColumn<Note, Void> noteVoidTableColumn) {
                final TableCell<Note, Void> cell = new TableCell<>(){
                    private final Button btn = new Button("Modifier");

                    {
                        btn.setTextFill(Color.WHITE);
                        btn.setStyle("-fx-background-color : #f0bc23;-fx-padding:5px 15px");
                        btn.setOnAction((ActionEvent event) -> {
                            Stage stage = new Stage();
                            Parent parent = null;
                            FXMLLoader loader = new FXMLLoader();
                            Note note = getTableView().getItems().get(getIndex());
                            try {
                                loader.setLocation(getClass().getResource("EditNotes.fxml"));
                                loader.load();
                                parent = loader.getRoot();
                                EditNoteController controller = loader.getController();
                                controller.setTable(noteTable);
                                controller.setField(note);
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

        noteList = DbModel.getNote();
        noteTable.setItems(noteList);
    }

    private void refresh (){
        noteList.clear();
        noteList = DbModel.getNote();
        noteTable.setItems(noteList);
    }

    public void addNote(){
        Stage stage = new Stage();
        Parent parent = null;
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getResource("NewNotes.fxml"));
            loader.load();
            parent = loader.getRoot();
            NewNoteController controller = loader.getController();
            controller.setTable(noteTable);
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
