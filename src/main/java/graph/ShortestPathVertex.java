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
}
