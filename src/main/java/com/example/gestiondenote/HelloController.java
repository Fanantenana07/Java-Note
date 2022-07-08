package com.example.gestiondenote;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Pane etudiantPane;
    @FXML
    private Pane matierePane;
    @FXML
    private Pane dashboardPane;
    @FXML
    private Pane notesPane;
    @FXML
    private Pane bulletinPane;
    @FXML
    private Pane classementPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private Label titleLabel;
    @FXML
    private Label subTitleLabel;
    @FXML
    private Button etudiantBtn;
    @FXML
    private Button matiereBtn;


    public void etudiant(){
        titleLabel.setText("Etudiants");
        subTitleLabel.setText("Dashboard/Etudiants");
        setPane(etudiantPane,"Etudiant.fxml");
    }
    public void matiere(){
        titleLabel.setText("Matieres");
        subTitleLabel.setText("Dashboard/Matieres");
        setPane(matierePane,"Matiere.fxml");
    }
    public void note(){
        titleLabel.setText("Notes");
        subTitleLabel.setText("Dashboard/Notes");
        setPane(notesPane,"Notes.fxml");
    }
    public void bulletin(){
        titleLabel.setText("Bulletin");
        subTitleLabel.setText("Dashboard/Bulletin");
        setPane(bulletinPane,"Bulletin.fxml");
    }
    public void classement(){
        titleLabel.setText("Classement");
        subTitleLabel.setText("Dashboard/Classement");
        setPane(classementPane,"Classement.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            setPane(etudiantPane,"Etudiant.fxml");
    }
    public void setPane (Pane p, String fxml){
        try {
            etudiantPane.getChildren().clear();
            matierePane.getChildren().clear();
            notesPane.getChildren().clear();
            bulletinPane.getChildren().clear();
            classementPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxml));
            loader.load();
            Node node = (Node) loader.getRoot();

            p.getChildren().add(node);
            p.toFront();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}