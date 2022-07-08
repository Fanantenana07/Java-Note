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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BulletinController implements Initializable {
    @FXML
    private ChoiceBox<String> matricule;
    @FXML
    private ChoiceBox<String> annee;
    @FXML
    private VBox bulletin;
    @FXML
    private TableView<Bulletin> noteTable;
    @FXML
    private TableColumn<Bulletin, String> libelleCol;
    @FXML
    private TableColumn<Bulletin, Integer> coefCol;
    @FXML
    private TableColumn<Bulletin, Integer> noteCol;
    @FXML
    private TableColumn<Bulletin, Integer> notePCol;
    @FXML
    private Label nomLabel;
    @FXML
    private Label matriculeLabel;
    @FXML
    private Label moyenneLabel;
    @FXML
    private Label observationLabel;


    private ObservableList<Bulletin> list = FXCollections.observableArrayList();

    public void afficher (){
        String matriculeV = matricule.getValue();
        String anneeV = annee.getValue();
        String libelle;
        int  noteSum = 0, coef,noteP,note;
        double coefSum = 0.0,moyenne;
        list.clear();

        try {
            Connection con = DbModel.getConnection();
            PreparedStatement st = con.prepareStatement("SELECT * FROM `note`,`matiere`,`etudiant` " +
                    "WHERE `note`.`annee` = ? AND `note`.`matricule` = ? " +
                    "AND `matiere`.`codeMat` = `note`.`codeMat` AND `note`.`matricule` = `etudiant`.`matricule`");
            st.setString(1,anneeV);
            st.setString(2,matriculeV);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                libelle = rs.getString("libelle");
                coef = rs.getInt("coef");
                note = rs.getInt("note");
                noteP = note * coef;
                coefSum += coef;
                noteSum += noteP;

                nomLabel.setText(rs.getString("nom"));
                matriculeLabel.setText(matriculeV);

                list.add(new Bulletin(libelle,coef,note,noteP));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        moyenne = noteSum / coefSum;
        moyenneLabel.setText(Double.toString(moyenne));
        if  (moyenne >= 10) observationLabel.setText(" Admis");
        else{
            if(moyenne >= 7.5) observationLabel.setText(" Redoublant");
            else observationLabel.setText(" Exclus");
        }

        bulletin.setVisible(true);
        noteTable.setItems(list);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        libelleCol.setCellValueFactory(new PropertyValueFactory<Bulletin,String>("libelle"));
        coefCol.setCellValueFactory(new PropertyValueFactory<Bulletin,Integer>("coef"));
        noteCol.setCellValueFactory(new PropertyValueFactory<Bulletin,Integer>("note"));
        notePCol.setCellValueFactory(new PropertyValueFactory<Bulletin,Integer>("noteP"));

        ResultSet rs = DbModel.getMatricule();
        String [] anneeV = {"L1","L2","L3", "M1", "M2"};
        annee.getItems().addAll(anneeV);
        annee.setValue("L1");
       try{
           String matriculeV;
            while (rs.next()){
                matriculeV = rs.getString("matricule");
                matricule.getItems().add(matriculeV);
                matricule.setValue(matriculeV);
            }
        }
       catch (Exception e){
           e.printStackTrace();
       }
    }
}
