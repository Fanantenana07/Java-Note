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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewMatiereController implements Initializable {
    @FXML
    private AnchorPane pane;
    @FXML
    private TextField codeMat;
    @FXML
    private TextField libelle;
    @FXML
    private Spinner<Integer> coef;
    private TableView<Matiere> table;


    public void setTable(TableView<Matiere> t){
        table = t;
    }

    public void addMatiere (){
        try {
            if (libelle.getText() == "" || codeMat.getText() == ""){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Attention");
                alert.setContentText("Une champs du formulaire est vide. Veillez le remplir");
                alert.show();
            } else {
                Connection con = DbModel.getConnection();
                PreparedStatement st;
                st = con.prepareStatement("SELECT * FROM `matiere` WHERE `matiere`.`codeMat` = ?");
                st.setString(1, codeMat.getText());
                ResultSet rs = st.executeQuery();
                if (rs.next()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setContentText("Le Code Matiere que vous avez entrée correspond à un matière déja présent dans la base");
                    alert.show();
                }
                else
                {
                    st = con.prepareStatement("INSERT INTO `matiere`" +
                            " (`codeMat`, `libelle`, `coef`)" +
                            " VALUES ( ?, ?, ?)");
                    st.setString(1,codeMat.getText());
                    st.setString(2,libelle.getText());
                    st.setInt(3,coef.getValue());
                    st.execute();
                    ObservableList<Matiere> list = FXCollections.observableArrayList();
                    list = DbModel.getMatiere();
                    table.setItems(list);
                    close();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void close (){
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory <Integer> vf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10);
        vf.setValue(1);
        coef.setValueFactory(vf);
    }
}
