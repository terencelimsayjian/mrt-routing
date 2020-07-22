package display;

import graph.Vertex;

public class RoutingInstruction {
  private RoutingInstruction(RoutingInstructionType type, Vertex startingVertex, Vertex endingVertex, int totalCost) {
    this.type = type;
    this.startingVertex = startingVertex;
    this.endingVertex = endingVertex;
    this.totalCost = totalCost;
  }

  public static RoutingInstruction buildRegular(Vertex startingVertex, Vertex endingVertex, int totalCost) {
    return new RoutingInstruction(RoutingInstructionType.REGULAR, startingVertex, endingVertex, totalCost);
  }

  public static RoutingInstruction buildInterchange(Vertex startingVertex, Vertex endingVertex, int totalCost) {
    return new RoutingInstruction(RoutingInstructionType.INTERCHANGE, startingVertex, endingVertex, totalCost);
  }

  private RoutingInstructionType type;

  private Vertex startingVertex;

  private Vertex endingVertex;
  private int totalCost;
  public static enum RoutingInstructionType {
    REGULAR,
    INTERCHANGE;

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RoutingInstruction that = (RoutingInstruction) o;

    if (totalCost != that.totalCost) return false;
    if (type != that.type) return false;
    if (!startingVertex.equals(that.startingVertex)) return false;
    return endingVertex.equals(that.endingVertex);
  }

  @Override
  public int hashCode() {
    int result = type.hashCode();
    result = 31 * result + startingVertex.hashCode();
    result = 31 * result + endingVertex.hashCode();
    result = 31 * result + totalCost;
    return result;
  }
}
