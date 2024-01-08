//package com.example.graphsdemo;
//import javafx.scene.chart.XYChart;
//
//import java.io.PrintStream;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class Main {
//
//    public static ArrayList<Double> var1 = new ArrayList();
//    public static ArrayList<String> var2 = new ArrayList();
//
//    public static void main(String[] var0) {
//        Connection var1 = null;
//
//        try {
//            var1 = DriverManager.getConnection("jdbc:mysql://127.0.0.1/classicmodels", "root", "Poiuyt@09SQL");
//            System.out.println("Connected to the database!");
//            performDatabaseOperations(var1);
//        } catch (SQLException var11) {
//            System.err.println("Error connecting to the database: " + var11.getMessage());
//        } finally {
//            if (var1 != null) {
//                try {
//                    var1.close();
//
//                } catch (SQLException var10) {
//                    var10.printStackTrace();
//                }
//            }
//
//        }
//
//    }
//
//    public static void performDatabaseOperations(Connection var0) {
//
//        String var3 = "SELECT orderNumber, orderDate FROM orders ORDER BY orderNumber";
//
//        try {
//            PreparedStatement var4 = var0.prepareStatement(var3);
//
//            try {
//                ResultSet var5 = var4.executeQuery();
//
//                try {
//                    while(var5.next()) {
//                        int var6 = var5.getInt("orderNumber");
//                        String var7 = var5.getString("orderDate");
//                        var1.add((double) var6);
////                        var2.add(var7);
//                        String var10000 = var7.substring(0, 7);
//                        String var8 = var10000;
//                        if (!var2.contains(var8)) {
//                            var2.add(var10000);
//                        }
//                    }
//
//                    PrintStream var14 = System.out;
//                    String var10001 = String.valueOf(var2);
//                    var14.println(var10001 + ", " + String.valueOf(var1));
//                } catch (Throwable var11) {
//                    if (var5 != null) {
//                        try {
//                            var5.close();
//                        } catch (Throwable var10) {
//                            var11.addSuppressed(var10);
//                        }
//                    }
//
//                    throw var11;
//                }
//
//                if (var5 != null) {
//                    var5.close();
//                }
//            } catch (Throwable var12) {
//                if (var4 != null) {
//                    try {
//                        var4.close();
//                    } catch (Throwable var9) {
//                        var12.addSuppressed(var9);
//                    }
//                }
//
//                throw var12;
//            }
//
//            if (var4 != null) {
//                var4.close();
//            }
//        } catch (SQLException var13) {
//            System.err.println("Error executing query: " + var13.getMessage());
//        }
//        for(int c = 0; c < var1.size(); c++){
//
//            System.out.println(var1.get(c));
//    }
//        for(int c = 0; c < var2.size(); c++){
//            System.out.println(var2.get(c));
//    }}
//}
