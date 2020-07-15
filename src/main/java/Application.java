import traindata.CsvTrainDataSource;
import traindata.TrainData;

import java.io.FileNotFoundException;
import java.util.List;

public class Application {
  public static void main(String[] args) throws FileNotFoundException {


    List<TrainData> trainDataFromCSV = new CsvTrainDataSource().getTrainData();

//    TrainDataParser trainDataParser = new TrainDataParser();

//    List<Vertex> vertices = trainDataParser.buildVertices(trainDataFromCSV);
//    List<Edge> edges = trainDataParser.buildEdges(trainDataFromCSV);
//
//    Graph graph = new Graph();
//
//    vertices.forEach(v -> graph.addVertex(v));
//    edges.forEach(e -> graph.addEdge());

    // Get train data from CSV
    // Build vertices
    // Build edges
    // Change API to add edges as ID?


  }
}
