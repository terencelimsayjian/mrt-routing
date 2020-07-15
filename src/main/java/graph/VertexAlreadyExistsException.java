package graph;

public class VertexAlreadyExistsException extends RuntimeException {
  public VertexAlreadyExistsException() {
    super("Vertex already exists");
  }
}
