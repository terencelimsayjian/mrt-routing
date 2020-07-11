package graph;

import java.util.Comparator;

class SearchResult implements Comparable<SearchResult> {

  private final Vertex vertex;
  private Vertex previousVertex;
  private Integer costToReachThisVertex;
  private Integer heuristicCost;

  SearchResult(Vertex vertex) {
    this.vertex = vertex;
    this.previousVertex = null;
    this.costToReachThisVertex = null;
    this.heuristicCost = 0;
  }

  SearchResult(Vertex vertex, int heuristicCost) {
    this.vertex = vertex;
    this.previousVertex = null;
    this.costToReachThisVertex = null;
    this.heuristicCost = heuristicCost;
  }

  Vertex getVertex() {
    return vertex;
  }

  Vertex getPreviousVertex() {
    return previousVertex;
  }

  Integer getCostToReachThisVertex() {
    return costToReachThisVertex;
  }

  void setPreviousVertex(Vertex previousVertex) {
    this.previousVertex = previousVertex;
  }

  void setCostToReachThisVertex(Integer costToReachThisVertex) {
    this.costToReachThisVertex = costToReachThisVertex;
  }

  boolean isUnknownCost() {
    return this.costToReachThisVertex == null;
  }

  @Override
  public String toString() {
    return "SearchResult{" +
           "vertex=" + vertex +
           ", previousVertex=" + previousVertex +
           ", costToReachThisVertex=" + costToReachThisVertex +
           ", heuristicCost=" + heuristicCost +
           '}';
  }

  @Override
  public int compareTo(SearchResult o) {
    if (this.isUnknownCost() && o.isUnknownCost()) {
      return 0;
    } else if (this.isUnknownCost()) {
      return 1;
    } else if (o.isUnknownCost()) {
      return -1;
    }

    Comparator<SearchResult> comparator = Comparator.nullsLast((a, b) -> (a.costToReachThisVertex + a.heuristicCost) - (b.costToReachThisVertex + b.heuristicCost));
    return comparator.compare(this, o);
  }
}
