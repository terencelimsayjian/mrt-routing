package graph;

import java.util.List;

public class ShortestPathResult {
  private final Integer numberOfExploredNodes;
  private final List<ShortestPathVertex> shortestPathVertices;

  public ShortestPathResult(List<ShortestPathVertex> shortestPath, Integer numberOfExploredNodes) {
    this.numberOfExploredNodes = numberOfExploredNodes;
    this.shortestPathVertices = shortestPath;
  }

  public Integer getTotalCost() {
    return shortestPathVertices.stream().reduce(0, (sum, shortestPathVertex) -> sum + shortestPathVertex.getCostToReachFromPreviousVertex(), Integer::sum);
  }

  public Integer getNumberOfExploredNodes() {
    return numberOfExploredNodes;
  }

  public List<ShortestPathVertex> getShortestPathVertices() {
    return shortestPathVertices;
  }
}
