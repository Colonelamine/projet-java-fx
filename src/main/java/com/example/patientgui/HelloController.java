package com.example.patientgui;

import com.example.patientgui.entities.Patient;
import com.example.patientgui.services.PatientService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class HelloController implements Initializable {

    PatientService patientService = new PatientService();
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField age;
    @FXML
    private TextField telephone;
    @FXML
    private TextField type;

    @FXML
    private Label Text;

    @FXML
    private Button saveButton;


    @FXML
    protected void onHelloButtonClick() {
        Text.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void importText(){
        try {
            patientService.extractTXT("./ressources/inputData.txt");
            fillTableView(patientService.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void exportText(){
        try {
            patientService.writeTXT("./ressources/inputData.txt");
            fillTableView(patientService.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onAddButtonClick() {
        Patient patient = new Patient();
        patient.setnom(nom.getText());
        patient.setprenom(prenom.getText());
        patient.settel(telephone.getText());
        patient.setage(Integer.parseInt(age.getText()));
        patient.setType(type.getText());
        patientService.save(patient);
        Text.setText("                                    Patient Ajoutee");
        fillTableView(patientService.findAll());

    }

    @FXML
    protected void ondeleteButtonClick() {

        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Patient patient = patientService.findAll().stream().filter(patient1 -> patient1.getnom().equals(tableView.getSelectionModel().getSelectedItem().getNom())).collect(Collectors.toList()).get(0);
            patientService.remove(patient.getId());
            fillTableView(patientService.findAll());
            Text.setText("                                    Patient deleted successfully");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTableColums();
        System.out.println(patientService.findAll());
        fillTableView(patientService.findAll());
    }

    @FXML
    private TableView<TableRow> tableView;

    private void initializeTableColums() {

        TableColumn Nom = new TableColumn("Nom");
        TableColumn Prenom = new TableColumn("Prenom");
        TableColumn Age = new TableColumn("age");
        TableColumn Telephone = new TableColumn("Telephone");
        TableColumn Type = new TableColumn("Type");

        tableView.getColumns().addAll(Nom, Prenom, Age, Telephone, Type);
        Nom.setCellValueFactory((new PropertyValueFactory<TableRow, String>("Nom")));
        Prenom.setCellValueFactory(new PropertyValueFactory<TableRow, String>("Prenom"));
        Age.setCellValueFactory(new PropertyValueFactory<TableRow, Integer>("Age"));
        Telephone.setCellValueFactory(new PropertyValueFactory<TableRow, String>("Telephone"));
        Type.setCellValueFactory(new PropertyValueFactory<TableRow, Float>("Type"));

    }

    private void fillTableView(List<Patient> list) {
        ObservableList<TableRow> data = FXCollections.observableArrayList();
        list.forEach(patient -> data.add(new TableRow(
                patient.getnom(),
                patient.getprenom(),
                patient.getage(),
                patient.gettel(),
                patient.getType()
        )));
        tableView.setItems(data);
    }

    @FXML
    protected void onresetButtonClick() {
        nom.setText("");
        prenom.setText("");
        age.setText("");
        telephone.setText("");
        type.setText("");
    }



    @FXML
    protected void onUpdateButtonClick() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            TableRow tableRow = tableView.getSelectionModel().getSelectedItem();
            nom.setText(tableRow.getNom());
            prenom.setText(tableRow.getPrenom());
            age.setText(tableRow.getAge() + "");
            telephone.setText(tableRow.getTelephone() + "");
            type.setText(tableRow.getType());
            saveButton.setOnAction(actionEvent -> {
                Patient patient = new Patient();
                patient.setnom(nom.getText());
                patient.setprenom(prenom.getText());
                patient.setage(Integer.parseInt(age.getText()));

                patient.settel(telephone.getText());
                patient.setType(type.getText());
                patient.setId(patientService.findAll().stream().filter(patient1 -> patient1.getnom().equals(tableView.getSelectionModel().getSelectedItem().getNom())).collect(Collectors.toList()).get(0).getId());
                patientService.update(patient);
                fillTableView(patientService.findAll());
                Text.setText("                                    Patient update successfully");


            });


        }
    }



    }
