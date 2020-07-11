package graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VertexTest {
  @Test
  void verticesWithSameNameAreEqual() {
    Vertex v1 = new Vertex("a1");
    Vertex v2 = new Vertex("a1");

    assertEquals(v1, v2);
  }

  @Test
  void verticesWithDifferentNameAreUnequal() {
    Vertex v1 = new Vertex("a1");
    Vertex v2 = new Vertex("a2");

    assertNotEquals(v1, v2);
  }
}
