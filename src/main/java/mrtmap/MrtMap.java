package mrtmap;

import graph.Graph;
import graph.Vertex;
import traindata.CsvTrainDataSource;
import traindata.Edge;
import traindata.TrainDataParser;

import java.util.List;

public class MrtMap {

  Graph graph;

  public MrtMap() {

    TrainDataParser trainDataParser = new TrainDataParser(new CsvTrainDataSource());

    List<Vertex> vertices = trainDataParser.buildVertices();
    List<Edge> edges = trainDataParser.buildEdges();

    Graph graph = new Graph();
    vertices.forEach(graph::addVertex);
    edges.forEach(e -> graph.addEdge(e.getStationIdA(), e.getStationIdB(), e.getTimeInMinutes()));


  }
}
