package graph;

class Edge {
  private final Vertex trailingVertex;
  private final int weight;

  Edge(Vertex trailingVertex, int weight) {
    this.trailingVertex = trailingVertex;
    this.weight = weight;
  }

  Vertex getTrailingVertex() {
    return trailingVertex;
  }

  int getWeight() {
    return weight;
  }
}
