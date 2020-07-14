import csv.CsvReader;
import csv.TrainData;
import graph.Graph;
import graph.Vertex;
import traindataparser.Edge;
import traindataparser.TrainDataParser;

import java.io.FileNotFoundException;
import java.util.List;

public class Application {
  public static void main(String[] args) throws FileNotFoundException {


    List<TrainData> trainDataFromCSV = new CsvReader().getTrainDataFromCSV();

    TrainDataParser trainDataParser = new TrainDataParser();

    List<Vertex> vertices = trainDataParser.buildVertices(trainDataFromCSV);
    List<Edge> edges = trainDataParser.buildEdges(trainDataFromCSV);

    Graph graph = new Graph();

    vertices.forEach(v -> graph.addVertex(v));
//    edges.forEach(e -> graph.addEdge());

    // Get train data from CSV
    // Build vertices
    // Build edges
    // Change API to add edges as ID?


  }
}
