package com.example.graphsdemo;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class ChartsDemoController implements Initializable {

    @FXML
    private BorderPane borderPane;

    private static final ArrayList<Double> orderNumbers = new ArrayList<>();
    private static final ArrayList<String> orderMonths = new ArrayList<>();
    private static final ArrayList<Double> prices = new ArrayList<>();
    private static final ArrayList<Double> orderValues = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/classicmodels", "root", "sqlpassword")) {
            System.out.println("Connected to the database!");
            performDatabaseOperations(connection);
            handleShowBarChart(null);
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    private void performDatabaseOperations(Connection connection) {
        String orderQuery = "SELECT orderNumber, orderDate FROM orders ORDER BY orderNumber";
        String priceQuery = "SELECT priceEach FROM orderdetails ORDER BY orderNumber";

        try (PreparedStatement orderStatement = connection.prepareStatement(orderQuery);
             ResultSet orderResultSet = orderStatement.executeQuery()) {

            while (orderResultSet.next()) {
                int orderNumber = orderResultSet.getInt("orderNumber");
                String orderDate = orderResultSet.getString("orderDate");
                orderNumbers.add((double) orderNumber);

                String month = orderDate.substring(0, 7);
                if (!orderMonths.contains(month)) {
                    orderMonths.add(month);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error executing order query: " + e.getMessage());
        }

        try (PreparedStatement priceStatement = connection.prepareStatement(priceQuery);
             ResultSet priceResultSet = priceStatement.executeQuery()) {

            while (priceResultSet.next()) {
                double priceEach = priceResultSet.getDouble("priceEach");
                prices.add(priceEach);

                orderValues.add(priceEach);
            }

        } catch (SQLException e) {
            System.err.println("Error executing price query: " + e.getMessage());
        }
    }

    @FXML
    void handleClose(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    public void handleShowBarChart(ActionEvent event) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Order Month");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Order Quantity");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        XYChart.Series<String, Number> data = new XYChart.Series<>();
        data.setName("Orders");

        for (int c = 0; c < orderMonths.size(); c++) {
            data.getData().add(new XYChart.Data<>(orderMonths.get(c), orderValues.get(c)));
        }

        barChart.getData().add(data);
        borderPane.setCenter(barChart);

        System.out.println("Data added to BarChart: " + data.getData());
    }

    @FXML
    private void handleShowPieChart(ActionEvent event) {
        borderPane.setCenter(setupPieChart());
    }

    private PieChart setupPieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (int c = 0; c < orderMonths.size(); c++) {
            pieChartData.add(new PieChart.Data(orderMonths.get(c), orderValues.get(c)));
        }

        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Orders by Month");

        ContextMenu contextMenu = new ContextMenu();
        MenuItem miSwitchToBarChart = new MenuItem("Switch To Bar Chart");
        contextMenu.getItems().add(miSwitchToBarChart);

        pieChart.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                            contextMenu.show(pieChart, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                        }
                    }
                });

        miSwitchToBarChart.setOnAction((ActionEvent actionEvent) -> {
            handleShowBarChart(null);
        });

        return pieChart;
    }

    @FXML
    void handleUpdateData(ActionEvent event) {
    }
}
