package graph;

import java.util.List;

public class ShortestPathResult {

  private final List<String> shortestPath;
  private final Integer totalCost;
  private final Integer numberOfExploredNodes;
  private final List<ShortestPathVertex> shortestPathVertices;

  public ShortestPathResult(List<String> shortestPath, Integer totalCost, Integer numberOfExploredNodes, List<ShortestPathVertex> shortestPathVertices) {
    this.shortestPath = shortestPath;
    this.totalCost = totalCost;
    this.numberOfExploredNodes = numberOfExploredNodes;
    this.shortestPathVertices = shortestPathVertices;
  }

  public List<String> getShortestPath() {
    return shortestPath;
  }

  public Integer getTotalCost() {
    return totalCost;
  }

  public Integer getNumberOfExploredNodes() {
    return numberOfExploredNodes;
  }

  public List<ShortestPathVertex> getShortestPathVertices() {
    return shortestPathVertices;
  }
}
