package com.tomkato.issuetracker;

import java.io.*;
import java.sql.*;
import java.net.*;

public class GetConnectionUtilities {
    
    private static boolean isRemote = true;
    private static String dbUrl = "";
    private static String username = "";
    private static String password = "";
    
    
    public static Connection getConnection() throws URISyntaxException, SQLException {
        
        if (System.getenv("CLEARDB_DATABASE_URL") == null) {isRemote = false;}
        
        if (isRemote) {
            String envdburl = System.getenv("CLEARDB_DATABASE_URL");
            URI dbUri = new URI(envdburl);
            
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
        } else {
            // TODO: convert to environment variables
            dbUrl = "jdbc:mysql://localhost:3306/sandbox";
            username = "root";
            password = "poweroverwhelming";
        }
        
        return DriverManager.getConnection(dbUrl, username, password);
    }
}