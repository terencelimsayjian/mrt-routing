package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class Graph {
  private HashMap<Vertex, List<Edge>> adjacencyList;

  public Graph() {
    adjacencyList = new HashMap<>();
  }

  public void addVertex(String vertexName) {
    Vertex vertex = new Vertex(vertexName);
    adjacencyList.put(vertex, new ArrayList<>());
  }

  public void addEdge(String v1, String v2, int weight) {
    List<Edge> v1Edges = adjacencyList.get(new Vertex(v1));
    List<Edge> v2Edges = adjacencyList.get(new Vertex(v2));

    if (v1Edges == null || v2Edges == null) {
      throw new NoSuchVertexException();
    }

    v1Edges.add(new Edge(new Vertex(v2), weight));
    v2Edges.add(new Edge(new Vertex(v1), weight));
  }

  public void breadthFirstTraversal(String startingNode) {
    Set<Vertex> visited = new HashSet<>();
    Queue<Vertex> nodesToExploreAdjacentNodes = new LinkedList<>();

    nodesToExploreAdjacentNodes.add(new Vertex(startingNode));

    while (!nodesToExploreAdjacentNodes.isEmpty()) {
      Vertex nodeToExplore = nodesToExploreAdjacentNodes.remove();
      if (!visited.contains(nodeToExplore)) {
        visited.add(nodeToExplore);

        List<Edge> edges = adjacencyList.get(nodeToExplore);

        for (Edge edge : edges) {
          nodesToExploreAdjacentNodes.add(edge.getTrailingVertex());
        }
      }
    }

    System.out.println(visited);
  }

  public void depthFirstTraversal(String startingNode) {
    Set<Vertex> visited = new HashSet<>();
    Stack<Vertex> nodesToExplore = new Stack<>();

    nodesToExplore.add(new Vertex(startingNode));

    while (!nodesToExplore.isEmpty()) {
      Vertex nodeToExplore = nodesToExplore.pop();
      if (!visited.contains(nodeToExplore)) {
        visited.add(nodeToExplore);

        List<Edge> edges = adjacencyList.get(nodeToExplore);

        for (Edge edge : edges) {
          nodesToExplore.add(edge.getTrailingVertex());
        }
      }
    }

    System.out.println(visited);
  }

  public void djikstraSearch(String starting, String ending) {
    Vertex startingVertex = new Vertex(starting);
    Vertex endingVertex = new Vertex(ending);

    if (!adjacencyList.containsKey(startingVertex) || adjacencyList.containsKey(endingVertex)) {
      throw new NoSuchVertexException();
    }

    Set<Vertex> unvisitedVertexes = new HashSet<>(adjacencyList.keySet());
    List<DijkstraSearchResult> dijkstraSearchResults = new HashSet<>(adjacencyList.keySet()).stream().map(vertex -> {
      DijkstraSearchResult dijkstraSearchResult = new DijkstraSearchResult(vertex);

      if (vertex.equals(startingVertex)) {
        dijkstraSearchResult.setDistance(0);
      }

      return dijkstraSearchResult;
    }).collect(Collectors.toList());

    Vertex currentVertex = startingVertex;

    while (!currentVertex.equals(endingVertex)) {
      unvisitedVertexes.remove(currentVertex);

      Vertex finalCurrentVertex = currentVertex;
      DijkstraSearchResult currentSearchResult = dijkstraSearchResults.stream().filter(s -> s.getVertex().equals(finalCurrentVertex)).findFirst().get();

      List<Edge> connectingEdges = adjacencyList.get(currentVertex);

      for (Edge edge : connectingEdges) {
        if (!unvisitedVertexes.contains(edge)) {

          DijkstraSearchResult edgeSearchResult = dijkstraSearchResults.stream().filter(s -> s.getVertex().equals(edge.getTrailingVertex())).findFirst().get();
          int newDistanceFromCurrentVertexToNewEdge = currentSearchResult.getWeight() + edge.getWeight();

          if (edgeSearchResult.isDistanceUnintialized() || newDistanceFromCurrentVertexToNewEdge < edgeSearchResult.getWeight()) {
            edgeSearchResult.setDistance(newDistanceFromCurrentVertexToNewEdge);
            edgeSearchResult.setShortestPrecedingVertex(currentVertex);
          }
        }
      }

      // TODO: Account for case where there is no route to end destination

      Optional<DijkstraSearchResult> min = dijkstraSearchResults.stream()
          .filter(s -> !s.isDistanceUnintialized())
          .filter(s -> unvisitedVertexes.contains(s.getVertex()))
          .min(Comparator.comparingInt(DijkstraSearchResult::getWeight));

      currentVertex = min.get().getVertex();
    }

    Vertex v = endingVertex;
    List<Vertex> route = new ArrayList<>();
    route.add(endingVertex);

    while (!v.equals(startingVertex)) {
      Vertex finalV = v;
      DijkstraSearchResult dijkstraSearchResult = dijkstraSearchResults.stream().filter(s -> s.getVertex().equals(finalV)).findFirst().get();
      route.add(dijkstraSearchResult.getShortestPrecedingVertex());
      v = dijkstraSearchResult.getShortestPrecedingVertex();
    }

    // While currentVertexIsNotEndingVertex
      // Remove currentVertex from unvisited vertex
      // Get all adjacent vertexes to the current vertex
      // Get currentSearchResult from DijkstraSearchResult

      // For each of the adjacent vertexes
        // Stop if adjacentVertice is visited
        // If not yet visited:
        // Find corresponding result in DijkstraSearchResult
        // if result isInfinite || resultWeight + currentSearchResult < result.getWeight()
          // set  resultWeight + currentSearchResult
          // Update precedingVertex to currentVertex




  }


}
