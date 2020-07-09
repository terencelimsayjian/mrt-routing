package graph;

public class SearchResult {

  private final Vertex vertex;
  private boolean isVisited;
  private Vertex previousVertex;
  private Integer costToReachThisVertex;

  public SearchResult(Vertex vertex) {
    this.vertex = vertex;
    this.isVisited = false;
    this.previousVertex = null;
    this.costToReachThisVertex = null;
  }

  public Vertex getVertex() {
    return vertex;
  }

  public boolean isVisited() {
    return isVisited;
  }

  public Vertex getPreviousVertex() {
    return previousVertex;
  }

  public Integer getCostToReachThisVertex() {
    return costToReachThisVertex;
  }

  public void setVisited(boolean visited) {
    isVisited = visited;
  }

  public void setPreviousVertex(Vertex previousVertex) {
    this.previousVertex = previousVertex;
  }

  public void setCostToReachThisVertex(Integer costToReachThisVertex) {
    this.costToReachThisVertex = costToReachThisVertex;
  }

  public boolean isUnknownCost() {
    return this.costToReachThisVertex == null;
  }

  @Override
  public String toString() {
    return "SearchResult{" +
           "vertex=" + vertex +
           ", isVisited=" + isVisited +
           ", previousVertex=" + previousVertex +
           ", costToReachThisVertex=" + costToReachThisVertex +
           '}';
  }
}
