package com.example.gestiondenote;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditNoteController {
    @FXML
    private Label matricule;
    @FXML
    private Label codeMat;
    @FXML
    private ChoiceBox<String> annee;
    @FXML
    private Spinner<Integer> note;
    @FXML
    private AnchorPane pane;

    private TableView<Note> table;


    public void setTable (TableView<Note> t){
        table = t;
    }

    public void setField (Note n){
        matricule.setText(n.getMatricule());
        codeMat.setText(n.getCodeMat());
        String [] anneeValue = {"L1","L2","L3","M1","M2"};
        annee.getItems().addAll(anneeValue);
        annee.setValue(n.getAnnee());
        SpinnerValueFactory<Integer> vf = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,20);
        vf.setValue(n.getNote());
        note.setValueFactory(vf);
    }

    public void editNote (){
        try {
            Connection con = DbModel.getConnection();
            PreparedStatement st = con.prepareStatement("UPDATE `note` SET `note` = ?," +
                    " `annee` = ? WHERE `note`.`matricule` = ? AND `note`.`codeMat` = ?");
            st.setInt(1,note.getValue());
            st.setString(2,annee.getValue());
            st.setString(3,matricule.getText());
            st.setString(4,codeMat.getText());
            st.execute();
            ObservableList<Note> list = FXCollections.observableArrayList();
            list = DbModel.getNote();
            table.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
    }

    public void close (){
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

}
