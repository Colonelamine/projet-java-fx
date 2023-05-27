package com.example.patientgui.dao.impl;



import com.example.patientgui.dao.PatientDao;
import com.example.patientgui.entities.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDaoImpl implements PatientDao {
    private Connection connection= DB.getConnection();
    public void insert(Patient patient) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Patient (nom, prenom, age, tel, type) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, patient.getnom());
            preparedStatement.setString(2, patient.getprenom());
            preparedStatement.setInt(3, patient.getage());
            preparedStatement.setString(4, patient.gettel());
            preparedStatement.setString(5, patient.getType().toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update(Patient patient) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE Patient SET nom = ?, prenom = ?, age = ?, tel = ?, type = ? WHERE id = ?")) {
            preparedStatement.setString(1, patient.getnom());
            preparedStatement.setString(2, patient.getprenom());
            preparedStatement.setInt(3, patient.getage());
            preparedStatement.setString(4, patient.gettel());
            preparedStatement.setString(5, patient.getType().toString());
            preparedStatement.setInt(6, patient.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Patient WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Patient findById(Integer id) {
        Patient patient = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Patient WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                patient = new Patient();
                patient.setId(resultSet.getInt("id"));
                patient.setnom(resultSet.getString("nom"));
                patient.setprenom(resultSet.getString("prenom"));
                patient.setage(resultSet.getInt("age"));
                patient.settel(resultSet.getString("tel"));
                patient.setType(resultSet.getString("type"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM patient");
            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setId(resultSet.getInt("id"));
                patient.setnom(resultSet.getString("nom"));
                patient.setprenom(resultSet.getString("prenom"));
                patient.setage(resultSet.getInt("age"));
                patient.settel(resultSet.getString("tel"));
                patient.setType(resultSet.getString("type"));

                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    @Override
    public Patient findByName(String name) {

        return null;
    }


}
