import graph.Graph;
import graph.NoPathExistsException;
import graph.ShortestPathResult;
import graph.ShortestPathVertex;
import graph.Vertex;
import traindata.CsvTrainDataSource;
import traindata.Edge;
import traindata.TrainDataParser;

import java.util.List;
import java.util.Scanner;

public class Application {
  public static void main(String[] args) {
    Graph graph = initialiseMrtMap();

    Scanner scanner = new Scanner(System.in);

    System.out.println("Welcome to Singapore's MRT!");

    String continueAsking = "y";
    while (!continueAsking.equals("N")) {

      String startingStationId = getStationIdFromInput(graph, scanner, "starting");
      String endingStationId = getStationIdFromInput(graph, scanner, "ending");

      try {
        System.out.println("Finding path from " + startingStationId + " to " + endingStationId);
        ShortestPathResult shortestPathAStar = graph.findShortestPathAStar(startingStationId, endingStationId);

        List<ShortestPathVertex> shortestPathVertices = shortestPathAStar.getShortestPathVertices();
        Integer totalCost = shortestPathAStar.getTotalCost();

        System.out.println("Shortest Path Vertex: " + shortestPathVertices);
        System.out.println("Total cost: " + totalCost);
      } catch (NoPathExistsException e) {
        System.out.println("Something went wrong; No path found");
      }

      System.out.println("Would you like to try again? [y/N]");
      continueAsking = scanner.next();
    }

    // TODO:
    // 1. Print out results:
    //    a. Make get shortest path return vertices with cost (DONE)
    //    b. Either decide to return an interface that you can build up the route by itself, or have to query the graph again (DONE)
    //    c. Display UI as follows:
    // Instructions
    // Continue along line CC1 -> CC9 x total minutes
    // Change at interchange CC9 -> DL8 x minutes
    // Continue along line DL8 -> DL19 x total minutes

    // 2. Allow for name input
    // Only allow name or ID at one time, but not both

  }

  private static String getStationIdFromInput(Graph graph, Scanner scanner, String stationLabel) {
    boolean isError = true;
    String startingStationId = null;

    while (isError) {
      System.out.println("Enter " + stationLabel + " Station ID or Name: ");
      startingStationId = scanner.next();

      if (graph.hasVertex(startingStationId)) {
        isError = false;
      } else {
        System.out.println("That station does not exist, please try again.");
      }
    }
    return startingStationId;
  }

  private static Graph initialiseMrtMap() {
    TrainDataParser trainDataParser = new TrainDataParser(new CsvTrainDataSource());

    List<Vertex> vertices = trainDataParser.buildVertices();
    List<Edge> edges = trainDataParser.buildEdges();

    Graph graph = new Graph();
    vertices.forEach(graph::addVertex);
    edges.forEach(e -> graph.addEdge(e.getStationIdA(), e.getStationIdB(), e.getTimeInMinutes()));
    return graph;
  }
}
