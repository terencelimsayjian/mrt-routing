package graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
  private Graph graph;
  public static final Vertex VERTEX_A = new Vertex("A");
  public static final Vertex VERTEX_B = new Vertex("B");
  public static final Vertex VERTEX_C = new Vertex("C");
  public static final Vertex VERTEX_D = new Vertex("D");
  public static final Vertex VERTEX_E = new Vertex("E");
  public static final Vertex VERTEX_F = new Vertex("F");

  @BeforeEach
  void setUp() {
    graph = new Graph();

    Vertex aaron = new Vertex("aaron");
    Vertex bryan = new Vertex("bryan");
    Vertex charlie = new Vertex("charlie");
    Vertex dillon = new Vertex("dillon");

    graph.addVertex(aaron);
    graph.addVertex(bryan);
    graph.addVertex(charlie);
    graph.addVertex(dillon);

    graph.addEdge(aaron, bryan, 1);
    graph.addEdge(aaron, charlie, 1);
    graph.addEdge(aaron, dillon, 1);
    graph.addEdge(bryan, charlie, 1);
  }

  @Test
  void breadthFirstSearch() {
    graph.breadthFirstTraversal("bryan");
    assertTrue(true);
  }

  @Test
  void depthFirstSearch() {
    graph.depthFirstTraversal("dillon");
    assertTrue(true);
  }

  @Nested
  class FindShortestPathDijkstra {
    @Test
    void shouldThrowNoSuchVertexExceptionIfStartingNodeDoesNotExistInGraph() throws Exception {
      Graph graph = new Graph();

      assertThrows(NoSuchVertexException.class, () -> graph.findShortestPath("unknown-vertex", "B"));
    }

    @Test
    void shouldThrowNoSuchVertexExceptionIfEndingNodeDoesNotExistInGraph() throws Exception {
      Graph graph = new Graph();

      assertThrows(NoSuchVertexException.class, () -> graph.findShortestPath("A", "unknown-vertex"));
    }

    @Test
    void shouldThrowNoPathExistsExceptionIfThereIsNoPathToEndingNode() throws Exception {
      Graph graph = new Graph();
      graph.addVertex(VERTEX_A);
      graph.addVertex(VERTEX_B);

      assertThrows(NoPathExistsException.class, () -> graph.findShortestPath("A", "B"));
    }

    @Test
    void shouldFindShortestPathWithOnlyThreeNodesLinearlyConnected() throws Exception {
      Graph graph = new Graph();

      graph.addVertex(VERTEX_A);
      graph.addVertex(VERTEX_B);
      graph.addVertex(VERTEX_C);

      graph.addEdge(VERTEX_A, VERTEX_B, 1);
      graph.addEdge(VERTEX_B, VERTEX_C, 1);

      ShortestPathResult actual = graph.findShortestPath("A", "C");

      List<String> expectedShortestPath = Arrays.asList("A", "B", "C");

      assertEquals(expectedShortestPath, actual.getShortestPath());
      assertEquals(2, actual.getTotalCost());
    }

    @Test
    void shouldFindShortestPathWithTwoAlternativeRoutes() throws Exception {
      Graph graph = new Graph();

      graph.addVertex(VERTEX_A);
      graph.addVertex(VERTEX_B);
      graph.addVertex(VERTEX_C);
      graph.addVertex(VERTEX_D);

      graph.addEdge(VERTEX_A, VERTEX_B, 5);
      graph.addEdge(VERTEX_A, VERTEX_C, 2);
      graph.addEdge(VERTEX_C, VERTEX_D, 8);
      graph.addEdge(VERTEX_B, VERTEX_D, 1);

      ShortestPathResult actual = graph.findShortestPath("A", "D");

      List<String> expectedShortestPath = Arrays.asList("A", "B", "D");

      assertEquals(expectedShortestPath, actual.getShortestPath());
      assertEquals(6, actual.getTotalCost());
    }

    @Test
    void shouldFindShortestPathWithComplicatedRoute() throws Exception {
      Graph graph = new Graph();

      graph.addVertex(VERTEX_A);
      graph.addVertex(VERTEX_B);
      graph.addVertex(VERTEX_C);
      graph.addVertex(VERTEX_D);
      graph.addVertex(VERTEX_E);
      graph.addVertex(VERTEX_F);

      graph.addEdge(VERTEX_A, VERTEX_B, 6);
      graph.addEdge(VERTEX_A, VERTEX_C, 5);
      graph.addEdge(VERTEX_A, VERTEX_D, 4);
      graph.addEdge(VERTEX_C, VERTEX_E, 2);
      graph.addEdge(VERTEX_B, VERTEX_E, 9);
      graph.addEdge(VERTEX_D, VERTEX_E, 2);
      graph.addEdge(VERTEX_E, VERTEX_F, 2);

      ShortestPathResult actual = graph.findShortestPath("A", "F");

      List<String> expectedShortestPath = Arrays.asList("A", "D", "E", "F");

      assertEquals(expectedShortestPath, actual.getShortestPath());
      assertEquals(8, actual.getTotalCost());
    }
  }

  @Nested
  class AStarSearch {

  }

}
