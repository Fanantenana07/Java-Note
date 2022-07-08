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

public class NewNoteController implements Initializable {
    @FXML
    private AnchorPane pane;
    @FXML
    private ChoiceBox<String> matricule;
    @FXML
    private ChoiceBox<String> codeMat;
    @FXML
    private ChoiceBox<String> annee;
    @FXML
    private Spinner<Integer> note;

    private TableView<Note> table;

    public void addNote (){
        try {
            Connection con = DbModel.getConnection();
            PreparedStatement st;
            st = con.prepareStatement("SELECT * FROM `note` WHERE " +
                    "`note`.`matricule` = ? AND `note`.`codeMat` = ?");
            st.setString(1,matricule.getValue());
            st.setString(2,codeMat.getValue());
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("La note est déja présent dans la base");
                alert.show();
            }
            else{
                st = con.prepareStatement("INSERT INTO `note`" +
                        " (`matricule`, `codeMat`, `note`,`annee`)" +
                        " VALUES ( ?, ?, ?, ?)");
                st.setString(1,matricule.getValue());
                st.setString(2,codeMat.getValue());
                st.setInt(3,note.getValue());
                st.setString(4,annee.getValue());
                st.execute();
                ObservableList<Note> list = FXCollections.observableArrayList();
                list = DbModel.getNote();
                table.setItems(list);
                close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setTable(TableView<Note> t){
        table = t;
    }

    public void close (){
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String [] anneeValue = {"L1","L2","L3","M1","M2"};
        annee.getItems().addAll(anneeValue);
        annee.setValue("L1");
        try {
            Connection con = DbModel.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT matricule FROM etudiant");
            String txt;
            while (rs.next()){
                txt = rs.getString("matricule");
                matricule.getItems().add(txt);
                matricule.setValue(txt);
            }
            rs = st.executeQuery("SELECT codeMat FROM matiere");
            while (rs.next()){
                txt = rs.getString("codeMat");
                codeMat.getItems().add(txt);
                codeMat.setValue(txt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SpinnerValueFactory<Integer> vf = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,20);
        vf.setValue(0);
        note.setValueFactory(vf);

    }
}
