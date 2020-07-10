package graph;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

  public Edge getEdge(String v1, String v2) {
    if (!hasVertex(v1) || !hasVertex(v2)) {
      throw new NoSuchVertexException();
    }

    List<Edge> edges = adjacencyList.get(new Vertex(v1));

    Optional<Edge> optionalEdge = edges.stream().filter(e -> e.getTrailingVertex().equals(new Vertex(v2))).findFirst();

    if (optionalEdge.isEmpty()) {
      throw new NoSuchVertexException();
    }

    return optionalEdge.get();
  }

  public boolean hasVertex(String name) {
    return adjacencyList.get(new Vertex(name)) != null;
  }

  public ShortestPathResult findShortestPath(String startingNode, String endingNode) throws NoPathExistsException {
    // This algorithm uses the Dijkstra algorithm to find shortest path
    if (!hasVertex(startingNode) || !hasVertex(endingNode)) {
      throw new NoSuchVertexException();
    }

    Vertex startingVertex = new Vertex(startingNode);
    Vertex endingVertex = new Vertex(endingNode);

    List<SearchResult> visitedSearchResults = new ArrayList<>();
    List<SearchResult> searchResults = initialiseSearchResults(startingVertex);

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

      Collections.sort(searchResults, Comparator.comparing(SearchResult::getCostToReachThisVertex, Comparator.nullsLast(Comparator.naturalOrder())));
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
