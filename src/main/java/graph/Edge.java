package graph;

public class Edge {
  private final Vertex trailingVertex;
  private final int weight;

  public Edge(Vertex trailingVertex, int weight) {
    this.trailingVertex = trailingVertex;
    this.weight = weight;
  }

  public Vertex getTrailingVertex() {
    return trailingVertex;
  }

  public int getWeight() {
    return weight;
  }
}
