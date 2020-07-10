package graph;

import java.util.List;

public class ShortestPathResult {

  private final List<String> shortestPath;
  private final Integer totalCost;
  private final Integer numberOfExploredNodes;

  public ShortestPathResult(List<String> shortestPath, Integer totalCost, Integer numberOfExploredNodes) {
    this.shortestPath = shortestPath;
    this.totalCost = totalCost;
    this.numberOfExploredNodes = numberOfExploredNodes;
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
}
