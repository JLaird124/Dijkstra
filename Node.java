package com.example.demo1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Node {
    private String name;
    private LinkedList<Node> shortestPath = new LinkedList<>();
    private Integer distance = Integer.MAX_VALUE;
    private Map<Node, Integer> adjacentNodes = new HashMap<>();
    private double x, y;

    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    public Node(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;

    }

    // Getters and setters
    public String getName() { return name; }
    public Map<Node, Integer> getAdjacentNodes() { return adjacentNodes; }
    public Integer getDistance() { return distance; }
    public void setDistance(Integer distance) { this.distance = distance; }
    public LinkedList<Node> getShortestPath() { return shortestPath; }
    public void setShortestPath(LinkedList<Node> shortestPath) { this.shortestPath = shortestPath; }
    public double getX() { return x; }
    public double getY() { return y; }
}
