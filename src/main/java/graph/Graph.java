package graph;

import geometry.Coordinate;
import geometry.Geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Graph {
  private HashMap<Vertex, List<Edge>> adjacencyList;
  private Set<Vertex> vertices;
  private Map<String, Vertex> searchIndexById;
  private Map<String, List<Vertex>> searchIndexByName;

  public Graph() {
    adjacencyList = new HashMap<>();
    vertices = new HashSet<>();
    searchIndexById = new HashMap<>();
    searchIndexByName = new HashMap<>();
  }

  public void addVertex(Vertex vertex) {
    if (vertices.contains(vertex)) {
      throw new VertexAlreadyExistsException();
    }

    vertices.add(vertex);
    adjacencyList.put(vertex, new ArrayList<>());
    searchIndexById.put(vertex.getId(), vertex);

    if (searchIndexByName.get(vertex.getDisplayName()) != null) {
      searchIndexByName.get(vertex.getDisplayName()).add(vertex);
    } else {
      searchIndexByName.put(vertex.getDisplayName(), new ArrayList(Arrays.asList(vertex)));
    }
  }

  public void addEdge(String v1Id, String v2Id, int weight) {
    Optional<Vertex> optionalFirstVertice = findById(v1Id);
    Optional<Vertex> optionalSecondVertice = findById(v2Id);
    if (!optionalFirstVertice.isPresent() || !optionalSecondVertice.isPresent()) {
      throw new NoSuchVertexException();
    }

    Vertex vv1 = optionalFirstVertice.get();
    Vertex vv2 = optionalSecondVertice.get();

    List<Edge> v1Edges = adjacencyList.get(vv1);
    List<Edge> v2Edges = adjacencyList.get(vv2);

    if (v1Edges == null || v2Edges == null) {
      throw new NoSuchVertexException();
    }

    v1Edges.add(new Edge(vv2, weight));
    v2Edges.add(new Edge(vv1, weight));
  }

  public boolean hasVertex(String id) {
    return adjacencyList.get(new Vertex(id)) != null;
  }

  public Optional<Vertex> findById(String id) {
    if (searchIndexById.get(id) != null) {
      return Optional.of(searchIndexById.get(id));
    }

    return Optional.empty();
  }

  public List<Vertex> findByName(String name) {
    if (searchIndexByName.get(name) != null) {
      return new ArrayList<>(searchIndexByName.get(name));
    }

    return new ArrayList<>();
  }

  public int getCost(String startingId, String endingId) {
    Optional<Vertex> v1 = findById(startingId);
    Optional<Vertex> v2 = findById(endingId);

    if (!v1.isPresent() || !v2.isPresent()) {
      throw new NoSuchVertexException();
    }

    List<Edge> edges = adjacencyList.get(v1.get());

    Optional<Edge> foundEdge = edges.stream().filter(e -> e.getTrailingVertex().equals(v2.get())).findFirst();

    if (!foundEdge.isPresent()) {
      throw new NoSuchEdgeException();
    }

    return foundEdge.get().getWeight();
  }

  public List<Vertex> getAllVertices() {
    List<Vertex> verticeList = Arrays.asList(vertices.toArray(new Vertex[vertices.size()]));
    return new ArrayList<>(verticeList);
  }

  private Set<Vertex> breadthFirstTraversal(String startingId) {
    Set<Vertex> visited = new HashSet<>();
    Queue<Vertex> nodesToExploreAdjacentNodes = new LinkedList<>();

    Vertex startingVertex = getVerticeFromGraph(startingId);

    nodesToExploreAdjacentNodes.add(startingVertex);

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

  private Set<Vertex> depthFirstTraversal(String startingId) {
    Set<Vertex> visited = new HashSet<>();
    Stack<Vertex> nodesToExplore = new Stack<>();

    Vertex vertex = getVerticeFromGraph(startingId);

    nodesToExplore.add(vertex);

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

  private Vertex getVerticeFromGraph(String verticeId) {
    Optional<Vertex> startingVertex = adjacencyList.keySet().stream().filter(v -> v.equals(new Vertex(verticeId))).findFirst();

    if (!startingVertex.isPresent()) {
      throw new NoSuchVertexException();
    }

    return startingVertex.get();
  }

  public ShortestPathResult findShortestPathAStar(String startingId, String endingId) throws NoPathExistsException {
    if (!hasVertex(startingId) || !hasVertex(endingId)) {
      throw new NoSuchVertexException();
    }

    Vertex startingVertex = getVerticeFromGraph(startingId);
    Vertex endingVertex = getVerticeFromGraph(endingId);

    List<SearchResult> searchResults = initialiseSearchResultsCartesianHeuristic(startingVertex, endingVertex);
    return calculateShortestPath(startingVertex, endingVertex, searchResults);
  }

  private List<SearchResult> initialiseSearchResultsCartesianHeuristic(Vertex startingVertex, Vertex endingVertex) {
    List<SearchResult> searchResults = new LinkedList<>();

    for (Vertex vertex : vertices) {
      double MRT_SPEED_KM_H = 80;
      double distanceInKm = Geometry.calculateHaversineDistanceKm(new Coordinate(vertex.getLat(), vertex.getLng()), new Coordinate(endingVertex.getLat(), endingVertex.getLng()));
      double timeTakenToReachInHours = distanceInKm / MRT_SPEED_KM_H;
      int heuristicCost = (int) Math.round(timeTakenToReachInHours * 60);

      SearchResult searchResult = new SearchResult(vertex, heuristicCost);

      if (vertex.equals(startingVertex)) {
        searchResult.setCostToReachThisVertex(0);
      }

      searchResults.add(searchResult);
    }

    Collections.sort(searchResults);
    return searchResults;
  }

  public ShortestPathResult findShortestPath(String startingId, String endingId) throws NoPathExistsException {
    // This algorithm uses the Dijkstra algorithm to find shortest path
    if (!hasVertex(startingId) || !hasVertex(endingId)) {
      throw new NoSuchVertexException();
    }

    Vertex startingVertex = new Vertex(startingId);
    Vertex endingVertex = new Vertex(endingId);

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
        List<ShortestPathVertex> shortestPathVertices = getShortestPathVertices(visitedSearchResults, startingVertex, endingVertex);
        return new ShortestPathResult(shortestPathVertices, selectedSearchResult.getCostToReachThisVertex(), visitedSearchResults.size());
      }

      List<Edge> edges = adjacencyList.get(selectedVertex);

      for (Edge edge : edges) {
        Optional<SearchResult> unvisitedSearchResult = searchResults.stream().filter(sr -> sr.getVertex().equals(edge.getTrailingVertex())).findFirst();

        if (!unvisitedSearchResult.isPresent()) {
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
    List<SearchResult> searchResults = new LinkedList<>();

    for (Vertex vertex : vertices) {
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
    shortestPath.add(endingVertex.getId());

    Vertex foundVertex = endingVertex;
    while (!foundVertex.equals(startingVertex)) {
      Vertex finalFoundVertex = foundVertex;
      SearchResult searchResult = visitedSearchResults.stream().filter(sr -> sr.getVertex().equals(finalFoundVertex)).findFirst().get();

      Vertex previousVertex = searchResult.getPreviousVertex();
      shortestPath.add(previousVertex.getId());
      foundVertex = previousVertex;
    }

    Collections.reverse(shortestPath);
    return shortestPath;
  }

  private List<ShortestPathVertex> getShortestPathVertices(List<SearchResult> visitedSearchResults, Vertex startingVertex, Vertex endingVertex) {
    List<Vertex> shortestPath = new ArrayList<>();
    shortestPath.add(endingVertex);

    Vertex foundVertex = endingVertex;
    while (!foundVertex.equals(startingVertex)) {
      Vertex finalFoundVertex = foundVertex;
      SearchResult searchResult = visitedSearchResults.stream().filter(sr -> sr.getVertex().equals(finalFoundVertex)).findFirst().get();

      Vertex previousVertex = searchResult.getPreviousVertex();
      shortestPath.add(previousVertex);
      foundVertex = previousVertex;
    }

    Collections.reverse(shortestPath);

    List<ShortestPathVertex> shortestPathVertices = new ArrayList<>();

    shortestPathVertices.add(new ShortestPathVertex(shortestPath.get(0), 0));
    for (int i = 1; i < shortestPath.size(); i++) {
      Vertex currentVertex = shortestPath.get(i);
      Vertex previousVertex = shortestPath.get(i - 1);

      Edge edge = adjacencyList.get(previousVertex).stream().filter(e -> e.getTrailingVertex().equals(currentVertex)).findFirst().get();
      shortestPathVertices.add(new ShortestPathVertex(edge.getTrailingVertex(), edge.getWeight()));
    }

    return shortestPathVertices;
  }

}
