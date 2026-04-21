package com.example.demo1;

public class RouteInfo {
    private String node;
    private String path;
    private Integer distance;

    public RouteInfo(String node, String path, Integer distance) {
        this.node = node;
        this.path = path;
        this.distance = distance;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}