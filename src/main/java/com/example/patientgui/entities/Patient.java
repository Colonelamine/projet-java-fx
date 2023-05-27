package com.example.patientgui.entities;

public class Patient {
    private int id;
    private String nom;
    private String prenom;
    private int age;
    private String tel;
    private String type;


    public Patient() {
    }

    public Patient(String nom, String prenom, int age, String tel, String type) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.tel = tel;
        this.type=type;

    }
        public Patient(int id, String nom, String prenom, int age, String tel, String type) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.tel = tel;
        this.type = type;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getnom() {
        return nom;
    }

    public String setnom(String nom) {this.nom = nom;
        return nom;
    }

    public String getprenom() {
        return prenom;
    }
    public String setprenom(String prenom) {
        this.prenom = prenom;
        return prenom;
    }


    public void setage(int age) {
        this.age= age;
    }

    public int getage() {
        return age;
    }

    public void settel(String tel) {
        this.tel = tel;
    }

    public String gettel() {
        return tel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return id+"/ "+nom+"/ "+prenom+"/ "+age+"/ "+tel+"/ "+type;
    }
}