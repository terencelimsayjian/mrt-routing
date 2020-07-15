import graph.Graph;
import graph.NoPathExistsException;
import graph.ShortestPathResult;
import graph.Vertex;
import traindata.CsvTrainDataSource;
import traindata.Edge;
import traindata.TrainDataParser;

import java.util.List;

public class Application {
  public static void main(String[] args) {


    TrainDataParser trainDataParser = new TrainDataParser(new CsvTrainDataSource());

    List<Vertex> vertices = trainDataParser.buildVertices();
    List<Edge> edges = trainDataParser.buildEdges();

    Graph graph = new Graph();
    vertices.forEach(graph::addVertex);
    edges.forEach(e -> graph.addEdge(e.getStationIdA(), e.getStationIdB(), e.getTimeInMinutes()));

    try {
      ShortestPathResult shortestPathAStar = graph.findShortestPathAStar("NS19", "NE8");

      List<String> shortestPath = shortestPathAStar.getShortestPath();
      Integer numberOfExploredNodes = shortestPathAStar.getNumberOfExploredNodes();
      Integer totalCost = shortestPathAStar.getTotalCost();

      System.out.println("Shortest Path: " + shortestPath);
      System.out.println("Number of explored nodes: " + numberOfExploredNodes);
      System.out.println("Total cost: " + totalCost);
    } catch (NoPathExistsException e) {
      e.printStackTrace();
    }




  }
}
