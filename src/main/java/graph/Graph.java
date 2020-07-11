package graph;

import geometry.Coordinate;
import geometry.Geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Graph {
  private HashMap<Vertex, List<Edge>> adjacencyList;

  public Graph() {
    adjacencyList = new HashMap<>();
  }

  public void addVertex(Vertex vertex) {
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

  public Set<Vertex> breadthFirstTraversal(String startingNode) {
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

    return visited;
  }

  public Set<Vertex> depthFirstTraversal(String startingNode) {
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

    return visited;
  }

  public boolean hasVertex(String name) {
    return adjacencyList.get(new Vertex(name)) != null;
  }

  public ShortestPathResult findShortestPathAStar(String startingNode, String endingNode) throws NoPathExistsException {
    if (!hasVertex(startingNode) || !hasVertex(endingNode)) {
      throw new NoSuchVertexException();
    }

    Vertex startingVertex = new Vertex(startingNode);
    Vertex endingVertex = new Vertex(endingNode);

    List<SearchResult> searchResults = initialiseSearchResultsCartesianHeuristic(startingVertex, endingVertex);
    return calculateShortestPath(startingVertex, endingVertex, searchResults);
  }

  private List<SearchResult> initialiseSearchResultsCartesianHeuristic(Vertex startingVertex, Vertex endingVertex) {
    Set<Vertex> allVertices = breadthFirstTraversal(startingVertex.getName());

    List<SearchResult> searchResults = new LinkedList<>();

    for (Vertex vertex : allVertices) {
      double MRT_SPEED_KM_H = 80;
      double distanceInKm = Geometry.calculateHaversineDistanceKm(new Coordinate(vertex.getLat(), vertex.getLng()), new Coordinate(endingVertex.getLat(), endingVertex.getLng()));
      double timeTakenToReachInMinutes = distanceInKm / MRT_SPEED_KM_H;
      int heuristicCost = (int) Math.round(timeTakenToReachInMinutes);

      SearchResult searchResult = new SearchResult(vertex, heuristicCost);


      if (vertex.equals(startingVertex)) {
        searchResult.setCostToReachThisVertex(0);
      }

      searchResults.add(searchResult);
    }
    return searchResults;
  }

  public ShortestPathResult findShortestPath(String startingNode, String endingNode) throws NoPathExistsException {
    // This algorithm uses the Dijkstra algorithm to find shortest path
    if (!hasVertex(startingNode) || !hasVertex(endingNode)) {
      throw new NoSuchVertexException();
    }

    Vertex startingVertex = new Vertex(startingNode);
    Vertex endingVertex = new Vertex(endingNode);

    List<SearchResult> searchResults = initialiseSearchResults(startingVertex);
    return calculateShortestPath(startingVertex, endingVertex, searchResults);
  }

  private ShortestPathResult calculateShortestPath(Vertex startingVertex, Vertex endingVertex, List<SearchResult> searchResults) throws NoPathExistsException {
    List<SearchResult> visitedSearchResults = new ArrayList<>();

    while (searchResults.size() > 0 && !searchResults.get(0).isUnknownCost()) {
      SearchResult selectedSearchResult = searchResults.remove(0);
      visitedSearchResults.add(selectedSearchResult);

      Vertex selectedVertex = selectedSearchResult.getVertex();
      if (selectedVertex.equals(endingVertex)) {
        List<String> shortestPath = getShortestPath(visitedSearchResults, startingVertex, endingVertex);
        return new ShortestPathResult(shortestPath, selectedSearchResult.getCostToReachThisVertex(), (int) visitedSearchResults.size());
      }

      List<Edge> edges = adjacencyList.get(selectedVertex);

      for (Edge edge : edges) {
        Optional<SearchResult> unvisitedSearchResult = searchResults.stream().filter(sr -> sr.getVertex().equals(edge.getTrailingVertex())).findFirst();

        if (unvisitedSearchResult.isEmpty()) {
          continue;
        }

        SearchResult searchResultToAssess = unvisitedSearchResult.get();
        Integer costToReachCurrentVertex = edge.getWeight() + selectedSearchResult.getCostToReachThisVertex();

        if (searchResultToAssess.isUnknownCost() || costToReachCurrentVertex < searchResultToAssess.getCostToReachThisVertex()) {
          searchResultToAssess.setPreviousVertex(selectedVertex);
          searchResultToAssess.setCostToReachThisVertex(costToReachCurrentVertex);
        }
      }

      Collections.sort(searchResults);
    }

    throw new NoPathExistsException();
  }

  private List<SearchResult> initialiseSearchResults(Vertex startingVertex) {
    Set<Vertex> allVertices = breadthFirstTraversal(startingVertex.getName());
    List<SearchResult> searchResults = new LinkedList<>();

    for (Vertex vertex : allVertices) {
      SearchResult searchResult = new SearchResult(vertex);

      if (vertex.equals(startingVertex)) {
        searchResult.setCostToReachThisVertex(0);
      }

      searchResults.add(searchResult);
    }
    return searchResults;
  }

  private List<String> getShortestPath(List<SearchResult> visitedSearchResults, Vertex startingVertex, Vertex endingVertex) {
    List<String> shortestPath = new ArrayList<>();
    shortestPath.add(endingVertex.getName());

    Vertex foundVertex = endingVertex;
    while (!foundVertex.equals(startingVertex)) {
      Vertex finalFoundVertex = foundVertex;
      SearchResult searchResult = visitedSearchResults.stream().filter(sr -> sr.getVertex().equals(finalFoundVertex)).findFirst().get();

      Vertex previousVertex = searchResult.getPreviousVertex();
      shortestPath.add(previousVertex.getName());
      foundVertex = previousVertex;
    }

    Collections.reverse(shortestPath);
    return shortestPath;
  }

}
