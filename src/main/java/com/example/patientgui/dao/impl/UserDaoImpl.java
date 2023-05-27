package com.example.patientgui.dao.impl;


import com.example.patientgui.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl{
    private Connection conn= DB.getConnection();
    public User getUserByUserName(String username) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("Id"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                return user;
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Cant find user by id");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }
}
