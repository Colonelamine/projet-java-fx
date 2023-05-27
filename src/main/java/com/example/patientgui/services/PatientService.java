package com.example.patientgui.services;


import com.example.patientgui.dao.PatientDao;
import com.example.patientgui.dao.impl.PatientDaoImpl;
import com.example.patientgui.entities.Patient;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class PatientService {
    private PatientDao patientDao = new PatientDaoImpl();
    public List<Patient> findAll() {
        return patientDao.findAll();
    }
    public void save(Patient patient) {
        patientDao.insert(patient);
    }
    public void update(Patient patient) {
        patientDao.update(patient);
    }
    public void findByID(int id) { patientDao.findById(id); }
    public List<Patient> extractTXT(String path)throws Exception{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        List<Patient> liste = new ArrayList<>();
        String readLine = bufferedReader.readLine();
        while(readLine != null){
            String [] patientData = readLine.split("/");
            Patient l = new Patient();
            l.setId(Integer.parseInt(patientData[0].trim()));
            l.setnom(patientData[1].trim());
            l.setprenom(patientData[2].trim());
            l.setage(Integer.parseInt(patientData[0].trim()));
            l.settel(patientData[0].trim());
            l.setType((patientData[5].trim()));
            liste.add(l);
            Patient d = patientDao.findById(l.getId());
            if(d != null){
                System.out.println("exists");
                patientDao.update(l);
            }
            else{
                System.out.println("nn hh");
                patientDao.insert(l);
            }
            readLine = bufferedReader.readLine();
        }
        return liste;
    }
    public void writeTXT(String path) throws Exception {
        List<Patient> patients = patientDao.findAll();
        try (PrintWriter writer = new PrintWriter(new File(path))) {
            for (Patient patient : patients) {
                writer.println(patient.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void remove(Integer id) {
        patientDao.deleteById(id);
    }
//    public void extractXLSX(String path) throws Exception {
//        FileInputStream fileInputStream = new FileInputStream(new File(path));
//        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
//        XSSFSheet sheet = workbook.getSheetAt(0);
//        Iterator<Row> rowIterator = sheet.iterator();
//        List<Patient> patients = new ArrayList<>();
//        rowIterator.next();
//        while (rowIterator.hasNext()) {
//            Row row = rowIterator.next();
//            Iterator<Cell> cellIterator = row.cellIterator();
//            Patient patient = new Patient();
//            patient.setId((int)cellIterator.next().getNumericCellValue());
//            try{
//                patient.setnom(cellIterator.next().getStringCellValue());
//            }
//            catch (Exception exception){
//                break;
//            }
//            patient.setprenom((String)cellIterator.next().getNumericCellValue());
//            patient.setOrigin(cellIterator.next().getStringCellValue());
//            patient.setDesc(cellIterator.next().getStringCellValue());
//            patient.setType(Patient.Type.valueOf(cellIterator.next().getStringCellValue().toUpperCase()));
//            Cell vegetarianCell = cellIterator.next();
//            if (vegetarianCell.getCellType() == CellType.BOOLEAN) {
//                patient.setVegetarian(vegetarianCell.getBooleanCellValue());
//            } else {
//                String vegetarianString = vegetarianCell.getStringCellValue().toLowerCase();
//                boolean isVegetarian = vegetarianString.equals("true") || vegetarianString.equals("yes");
//                patient.setVegetarian(isVegetarian);
//            }
//            patients.add(patient);
//            Patient d = patientDao.findById(patient.getId());
//            if(d != null){
//                System.out.println("exists");
//                patientDao.update(patient);
//            }
//            else{
//                System.out.println("nn hh");
//                patientDao.insert(patient);
//            }
//        }
//    }

    public void writeXLSX(String path) throws Exception {
        List<Patient> patients = patientDao.findAll();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Patients Info");

        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Prenom");
        headerRow.createCell(3).setCellValue("Age");
        headerRow.createCell(4).setCellValue("Tel");
        headerRow.createCell(5).setCellValue("Type");


        int rowNum = 1;
        for (Patient patient : patients) {
            XSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(patient.getId());
            row.createCell(1).setCellValue(patient.getnom());
            row.createCell(2).setCellValue(patient.getprenom());
            row.createCell(3).setCellValue(patient.getage());
            row.createCell(4).setCellValue(patient.gettel());
            row.createCell(5).setCellValue(patient.getType());

        }

        FileOutputStream outputStream = new FileOutputStream(new File(path));
        workbook.write(outputStream);
        outputStream.close();
    }
    public void  extractJSON(String path) throws Exception {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(path));

        JSONArray outputJsonArray = new JSONArray();
        for (Object obj : jsonArray) {
            Patient patient = new Patient();
            JSONObject jsonObject = (JSONObject) obj;
            patient.setId(((Long) jsonObject.get("id")).intValue());
            patient.setnom((String) jsonObject.get("name"));
            patient.setprenom((String) jsonObject.get("prenom"));
            patient.setage((int) jsonObject.get("age"));
            patient.settel((String) jsonObject.get("tel"));
            patient.setType((String) jsonObject.get("type"));

            Patient d = patientDao.findById(patient.getId());
            if(d != null){
                System.out.println("exists");
                patientDao.update(patient);
            }
            else{
                System.out.println("nn hh");
                patientDao.insert(patient);
            }
        }
    }

    public void writeJSON(String path) throws Exception {
        List<Patient> patients = patientDao.findAll();
        JSONArray outputJsonArray = new JSONArray();
        for (Patient patient : patients) {
            JSONObject outputJsonObject = new JSONObject();
            outputJsonObject.put("id", patient.getId());
            outputJsonObject.put("nom", patient.getnom());
            outputJsonObject.put("prenom", patient.getprenom());
            outputJsonObject.put("age", patient.getage());
            outputJsonObject.put("tel", patient.gettel());
            outputJsonObject.put("type", patient.getType());

            outputJsonArray.add(outputJsonObject);
        }
        try (FileWriter file = new FileWriter(path)) {
            file.write(outputJsonArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static XSSFRow row;
}
