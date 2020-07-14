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
    graph.addVertex(VERTEX_A);
    graph.addVertex(VERTEX_B);
    graph.addVertex(VERTEX_C);
    graph.addVertex(VERTEX_D);

    graph.addEdge(VERTEX_A, VERTEX_B, 1);
    graph.addEdge(VERTEX_A, VERTEX_C, 1);
    graph.addEdge(VERTEX_A, VERTEX_D, 1);
    graph.addEdge(VERTEX_B, VERTEX_C, 1);
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
    private final Vertex VERTEX_1 = new Vertex("NS17", "BISHAN", 1.350838988, 103.8481398);
    private final Vertex VERTEX_2 = new Vertex("NS16", "ANG MO KIO", 1.369933175, 103.8495535);
    private final Vertex VERTEX_3 = new Vertex("NS15", "YIO CHU KANG", 1.381756046, 103.8449439);

    @Test
    void shouldCalculateSimpleLinearPath() throws Exception {
      Graph graph = new Graph();

      graph.addVertex(VERTEX_1);
      graph.addVertex(VERTEX_2);
      graph.addVertex(VERTEX_3);

      graph.addEdge(VERTEX_1, VERTEX_2, 2);
      graph.addEdge(VERTEX_2, VERTEX_3, 4);

      ShortestPathResult actual = graph.findShortestPathAStar("NS17", "NS15");

      List<String> expectedShortestPath = Arrays.asList("NS17", "NS16", "NS15");

      assertEquals(expectedShortestPath, actual.getShortestPath());
      assertEquals(6, actual.getTotalCost());
    }
  }

}
