package com.tomkato.issuetracker;

import java.io.*;
import java.sql.*;
import java.net.*;

public class GetConnectionUtilities {
    
    private static boolean isRemote = true;
    private static String dbUrl = "";
    private static String username = "";
    private static String password = "";
    
    
    public static Connection getConnection() throws URISyntaxException, SQLException, ClassNotFoundException {
        
        String envdburl = System.getenv("CLEARDB_DATABASE_URL");
        URI dbUri = new URI(envdburl);
        
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(dbUrl, username, password);
    }
}