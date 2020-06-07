package graph;

public class DijkstraSearchResult {
  private final Vertex vertex;
  private Vertex shortestPrecedingVertex;
  private Integer weight;

  public DijkstraSearchResult(Vertex vertex) {
    this.vertex = vertex;
    this.weight = null;
  }

  public boolean isDistanceUnintialized() {
    return weight == null;
  }

  public Vertex getVertex() {
    return vertex;
  }

  public Vertex getShortestPrecedingVertex() {
    return shortestPrecedingVertex;
  }

  public void setShortestPrecedingVertex(Vertex shortestPrecedingVertex) {
    this.shortestPrecedingVertex = shortestPrecedingVertex;
  }

  public Integer getWeight() {
    return weight;
  }

  public void setDistance(Integer distance) {
    this.weight = distance;
  }

  @Override
  public String toString() {
    return "DijkstraSearchResult{" +
           "vertex=" + vertex +
           ", shortestPrecedingVertex=" + shortestPrecedingVertex +
           ", weight=" + weight +
           '}';
  }
}
