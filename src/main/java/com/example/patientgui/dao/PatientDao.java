package com.example.patientgui.dao;


import com.example.patientgui.entities.Patient;

import java.util.List;

public interface PatientDao {
    void insert(Patient patient);
    void update(Patient patient);
    void deleteById(Integer id);
    Patient findById(Integer id);
    List<Patient> findAll();
    Patient findByName(String name) ;

}
