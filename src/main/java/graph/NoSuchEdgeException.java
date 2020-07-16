package graph;

public class NoSuchEdgeException extends RuntimeException {
  public NoSuchEdgeException() {
    super("Edge does not exist");
  }
}
