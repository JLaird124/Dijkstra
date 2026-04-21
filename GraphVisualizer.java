package com.example.demo1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GraphVisualizer extends Application {

    @Override
    public void start(Stage primaryStage) {
        Graph graph = new Graph();

        // Create nodes with specific positions
        Node nodeA = new Node("A", 50, 200);
        Node nodeB = new Node("B", 150, 100);
        Node nodeC = new Node("C", 150, 250);
        Node nodeD = new Node("D", 300, 100);
        Node nodeE = new Node("E", 300, 250);
        Node nodeF = new Node("F", 450, 200);

        // Add nodes to graph
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);

        // Create edges based on the new connections
        nodeA.addDestination(nodeB, 20);
        nodeA.addDestination(nodeC, 30);

        nodeB.addDestination(nodeA, 20);
        nodeB.addDestination(nodeD, 32);
        nodeB.addDestination(nodeC, 40);

        nodeC.addDestination(nodeA, 30);
        nodeC.addDestination(nodeD, 40);
        nodeC.addDestination(nodeE, 52);

        nodeD.addDestination(nodeB, 32);
        nodeD.addDestination(nodeC, 40);
        nodeD.addDestination(nodeE, 25);

        nodeE.addDestination(nodeC, 52);
        nodeE.addDestination(nodeD, 25);
        nodeE.addDestination(nodeF, 30);

        nodeF.addDestination(nodeD, 40);
        nodeF.addDestination(nodeE, 30);

        // Calculate shortest paths from nodeA
        Graph.calculateShortestPathFromSource(graph, nodeA);

        Pane pane = new Pane();

        // Drawing nodes
        Set<Node> nodes = graph.getNodes();
        Map<String, Circle> nodeCircles = new HashMap<>();
        for (Node node : nodes) {
            Circle circle = new Circle(node.getX(), node.getY(), 20, Color.LIGHTBLUE);
            nodeCircles.put(node.getName(), circle);
            pane.getChildren().add(circle);
            Label label = new Label(node.getName());
            label.setLayoutX(node.getX() - 5);
            label.setLayoutY(node.getY() - 10);
            pane.getChildren().add(label);
        }

        // Drawing edges with distance labels
        for (Node node : nodes) {
            for (Map.Entry<Node, Integer> adjacencyPair : node.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                int distance = adjacencyPair.getValue();
                double startX = node.getX();
                double startY = node.getY();
                double endX = adjacentNode.getX();
                double endY = adjacentNode.getY();
                Line line = new Line(startX, startY, endX, endY);
                pane.getChildren().add(line);

                // Adding distance label with offset to avoid overlap
                double midX = (startX + endX) / 2;
                double midY = (startY + endY) / 2;
                double offsetX = 10;
                double offsetY = -10;
                if (startX == endX) { // Vertical line
                    offsetX = 15;
                    offsetY = 0;
                } else if (startY == endY) { // Horizontal line
                    offsetX = 0;
                    offsetY = 15;
                }
                Label distanceLabel = new Label(String.valueOf(distance));
                distanceLabel.setLayoutX(midX + offsetX);
                distanceLabel.setLayoutY(midY + offsetY);
                pane.getChildren().add(distanceLabel);
            }
        }

        // Highlight shortest paths in red
        for (Node node : nodes) {
            if (!node.getShortestPath().isEmpty()) {
                Node previousNode = node.getShortestPath().getFirst();
                for (Node pathNode : node.getShortestPath()) {
                    Line pathLine = new Line(previousNode.getX(), previousNode.getY(), pathNode.getX(), pathNode.getY());
                    pathLine.setStroke(Color.RED);
                    pathLine.setStrokeWidth(2);
                    pane.getChildren().add(pathLine);
                    previousNode = pathNode;
                }
                Line pathLine = new Line(previousNode.getX(), previousNode.getY(), node.getX(), node.getY());
                pathLine.setStroke(Color.RED);
                pathLine.setStrokeWidth(2);
                pane.getChildren().add(pathLine);
            }
        }

        // Create TableView for routing information
        TableView<RouteInfo> tableView = new TableView<>();
        tableView.setPrefWidth(600);
        tableView.setPrefHeight(200);

        TableColumn<RouteInfo, String> nodeColumn = new TableColumn<>("Node");
        nodeColumn.setCellValueFactory(new PropertyValueFactory<>("node"));

        TableColumn<RouteInfo, String> pathColumn = new TableColumn<>("Shortest Path");
        pathColumn.setCellValueFactory(new PropertyValueFactory<>("path"));

        TableColumn<RouteInfo, Integer> distanceColumn = new TableColumn<>("Distance");
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));

        tableView.getColumns().add(nodeColumn);
        tableView.getColumns().add(pathColumn);
        tableView.getColumns().add(distanceColumn);

        // Populate the table with routing information
        ObservableList<RouteInfo> data = FXCollections.observableArrayList();
        for (Node node : nodes) {
            StringBuilder path = new StringBuilder();
            for (Node pathNode : node.getShortestPath()) {
                path.append(pathNode.getName()).append(" -> ");
            }
            path.append(node.getName());
            data.add(new RouteInfo(node.getName(), path.toString(), node.getDistance()));
        }
        tableView.setItems(data);

        VBox vbox = new VBox(pane, tableView);

        Scene scene = new Scene(vbox, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Graph Visualizer");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
