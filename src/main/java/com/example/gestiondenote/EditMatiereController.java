package com.example.gestiondenote;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditMatiereController {
    @FXML
    private AnchorPane pane;
    @FXML
    private Label codeMat;
    @FXML
    private TextField libelle;
    @FXML
    private Spinner<Integer> coef;
    private TableView<Matiere> table;


    public void setTable(TableView<Matiere> t){
        table = t;
    }

    public void editMatiere (){
        try {
            Connection con = DbModel.getConnection();
            PreparedStatement st = con.prepareStatement("UPDATE `matiere` SET `libelle` = ?," +
                    " `coef` = ? WHERE `matiere`.`codeMat` = ?");
            st.setString(1,libelle.getText());
            st.setInt(2,coef.getValue());
            st.setString(3,codeMat.getText());
            st.execute();
            ObservableList<Matiere> list = FXCollections.observableArrayList();
            list = DbModel.getMatiere();
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

    public void setMatiere (Matiere m){
        codeMat.setText(m.getCodeMat());
        libelle.setText(m.getLibelle());
        SpinnerValueFactory <Integer> vf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10);
        vf.setValue(m.getCoef());
        coef.setValueFactory(vf);
    }
}
