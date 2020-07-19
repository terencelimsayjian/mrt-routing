package graph;

public class ShortestPathVertex {

  private final Vertex vertex;
  private final int costToReachFromPreviousVertex;

  public ShortestPathVertex(Vertex vertex, int costToReachFromPreviousVertex) {
    this.vertex = vertex;
    this.costToReachFromPreviousVertex = costToReachFromPreviousVertex;
  }

  @Override
  public String toString() {
    return "ShortestPathVertex{" +
           "vertex=" + vertex +
           ", costToReachFromPreviousVertex=" + costToReachFromPreviousVertex +
           '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ShortestPathVertex that = (ShortestPathVertex) o;

    if (costToReachFromPreviousVertex != that.costToReachFromPreviousVertex) return false;
    return vertex != null ? vertex.equals(that.vertex) : that.vertex == null;
  }

  @Override
  public int hashCode() {
    int result = vertex != null ? vertex.hashCode() : 0;
    result = 31 * result + costToReachFromPreviousVertex;
    return result;
  }

  public Vertex getVertex() {
    return vertex;
  }

  public int getCostToReachFromPreviousVertex() {
    return costToReachFromPreviousVertex;
  }
}
