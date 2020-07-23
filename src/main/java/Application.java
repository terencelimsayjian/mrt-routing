import display.RouteDisplay;
import display.RoutingInstruction;
import graph.Graph;
import graph.NoPathExistsException;
import graph.ShortestPathVertex;
import graph.Vertex;
import traindata.CsvTrainDataSource;
import traindata.Edge;
import traindata.TrainDataParser;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Application {
  public static void main(String[] args) {
    Graph graph = initialiseMrtMap();
    Scanner scanner = new Scanner(System.in);

    System.out.println("Welcome to Singapore's MRT!");

    String continueAsking = "y";
    while (!continueAsking.equals("N")) {

      Vertex startingStation = getStationIdFromInput(graph, scanner, "starting");
      Vertex endingStation = getStationIdFromInput(graph, scanner, "ending");

      try {
        System.out.println("Finding path from " + startingStation.getDisplayName() + " to " + endingStation.getDisplayName());

        List<ShortestPathVertex> shortestPathVertices = graph.findShortestPathAStar(startingStation.getId(), endingStation.getId());
        List<RoutingInstruction> display = RouteDisplay.display(shortestPathVertices);

        Integer totalCost = display.stream().reduce(0, (sum, v) -> sum + v.getTotalCost(), Integer::sum);
        System.out.println("Journey will take " + totalCost + " minutes");

        display.forEach(ri -> {
          System.out.println(ri.getType() + ": " + ri.getStartingVertex().getDisplayName() + " to " + ri.getEndingVertex().getDisplayName() + " for " + ri.getTotalCost() + " minutes.");
        });
      } catch (NoPathExistsException e) {
        System.out.println("Something went wrong; No path found");
      }

      System.out.println("Would you like to try again? [y/N]");
      continueAsking = scanner.nextLine();
    }
  }

  private static Vertex getStationIdFromInput(Graph graph, Scanner scanner, String stationLabel) {
    while (true) {
      System.out.println("Enter " + stationLabel + " Station ID or Name: ");
      String stationNameOrId = scanner.nextLine().toUpperCase();

      Optional<Vertex> vertexById = graph.findById(stationNameOrId);
      if (vertexById.isPresent()) {
        return vertexById.get();
      }

      List<Vertex> verticesByName = graph.findByName(stationNameOrId);
      if (!verticesByName.isEmpty()) {
        return verticesByName.get(0);
      }

      System.out.println("That station does not exist, please try again.");
    }
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
