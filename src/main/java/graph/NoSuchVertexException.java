package graph;

public class NoSuchVertexException extends RuntimeException {

  public NoSuchVertexException() {
    super("Vertex does not exist");
  }
}
