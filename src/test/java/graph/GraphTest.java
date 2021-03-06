package graph;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static graph.MrtTrack.NORTH_SOUTH_LINE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphTest {
  public static final Vertex VERTEX_A = new Vertex("A");
  public static final Vertex VERTEX_B = new Vertex("B");
  public static final Vertex VERTEX_C = new Vertex("C");
  public static final Vertex VERTEX_D = new Vertex("D");
  public static final Vertex VERTEX_E = new Vertex("E");
  public static final Vertex VERTEX_F = new Vertex("F");

  @Test
  void shouldThrowExceptionWhenTryingToAddVertexThatAlreadyExists() {
    Graph graph = new Graph();
    graph.addVertex(VERTEX_A);

    assertThrows(VertexAlreadyExistsException.class, () -> graph.addVertex(VERTEX_A));
  }

  @Test
  void shouldThrowNoSuchVertexExceptionWhenTryingToAddEdgeAndTheFirstVertexDoesntExist() {
    Graph graph = new Graph();
    graph.addVertex(VERTEX_A);

    assertThrows(NoSuchVertexException.class, () -> graph.addEdge(VERTEX_B.getId(), VERTEX_A.getId(), 2));
  }

  @Test
  void shouldThrowNoSuchVertexExceptionWhenTryingToAddEdgeAndTheSecondVertexDoesntExist() {
    Graph graph = new Graph();
    graph.addVertex(VERTEX_B);

    assertThrows(NoSuchVertexException.class, () -> graph.addEdge(VERTEX_B.getId(), VERTEX_A.getId(), 2));
  }

  @Test
  void shouldReturnCostBetweenTwoEdges() {
    Graph graph = new Graph();
    graph.addVertex(VERTEX_A);
    graph.addVertex(VERTEX_B);
    graph.addEdge(VERTEX_A.getId(), VERTEX_B.getId(), 2);

    assertEquals(2, graph.getCost(VERTEX_A.getId(), VERTEX_B.getId()));
  }

  @Test
  void shouldThrowNoSuchEdgeExceptionIfEdgeDoesNotExist() {
    Graph graph = new Graph();
    graph.addVertex(VERTEX_A);
    graph.addVertex(VERTEX_B);

    assertThrows(NoSuchEdgeException.class, () -> graph.getCost(VERTEX_A.getId(), VERTEX_B.getId()));
  }

  @Nested
  class SearchFunctions {
    private final Vertex vertexA = new Vertex("A", "station_a", NORTH_SOUTH_LINE, 0, 0);

    @Test
    void findByIdShouldReturnVertex() {
      Graph graph = new Graph();
      graph.addVertex(vertexA);

      Optional<Vertex> a = graph.findById("A");
      assertEquals(vertexA, a.get());
    }

    @Test
    void findByIdShouldReturnEmptyOptionalIfNotExist() {
      Graph graph = new Graph();
      graph.addVertex(vertexA);

      Optional<Vertex> a = graph.findById("B");
      assertFalse(a.isPresent());
    }

    @Test
    void findByNameShouldReturnMultipleVertices() {
      Graph graph = new Graph();
      Vertex vertexASameName = new Vertex("A", "station_a", NORTH_SOUTH_LINE, 0, 0);
      Vertex vertexBSameName = new Vertex("B", "station_a", NORTH_SOUTH_LINE, 0, 0);
      graph.addVertex(vertexASameName);
      graph.addVertex(vertexBSameName);

      List<Vertex> actual = graph.findByName("station_a");

      assertEquals(2, actual.size());
      assertTrue(actual.contains(vertexASameName));
      assertTrue(actual.contains(vertexBSameName));
    }

    @Test
    void findByNameShouldReturnEmptyListIfNoneExists() {
      Graph graph = new Graph();
      List<Vertex> actual = graph.findByName("station_a");

      assertEquals(0, actual.size());
    }
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

      graph.addEdge(VERTEX_A.getId(), VERTEX_B.getId(), 1);
      graph.addEdge(VERTEX_B.getId(), VERTEX_C.getId(), 1);

      List<ShortestPathVertex> actual = graph.findShortestPath("A", "C");

      List<ShortestPathVertex> expectedShortestPath = Arrays.asList(
          new ShortestPathVertex(VERTEX_A, 0),
          new ShortestPathVertex(VERTEX_B, 1),
          new ShortestPathVertex(VERTEX_C, 1)
      );

      int totalCost = actual.stream().reduce(0, (sum, vertex) -> sum + vertex.getCostToReachFromPreviousVertex(), Integer::sum);
      assertEquals(expectedShortestPath, actual);
      assertEquals(2, totalCost);
    }

    @Test
    void shouldFindShortestPathWithTwoAlternativeRoutes() throws Exception {
      Graph graph = new Graph();

      graph.addVertex(VERTEX_A);
      graph.addVertex(VERTEX_B);
      graph.addVertex(VERTEX_C);
      graph.addVertex(VERTEX_D);

      graph.addEdge(VERTEX_A.getId(), VERTEX_B.getId(), 5);
      graph.addEdge(VERTEX_A.getId(), VERTEX_C.getId(), 2);
      graph.addEdge(VERTEX_C.getId(), VERTEX_D.getId(), 8);
      graph.addEdge(VERTEX_B.getId(), VERTEX_D.getId(), 1);

      List<ShortestPathVertex> actual = graph.findShortestPath("A", "D");

      List<ShortestPathVertex> expectedShortestPath = Arrays.asList(
          new ShortestPathVertex(VERTEX_A, 0),
          new ShortestPathVertex(VERTEX_B, 5),
          new ShortestPathVertex(VERTEX_D, 1)
      );

      int totalCost = actual.stream().reduce(0, (sum, vertex) -> sum + vertex.getCostToReachFromPreviousVertex(), Integer::sum);
      assertEquals(expectedShortestPath, actual);
      assertEquals(6, totalCost);
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

      graph.addEdge(VERTEX_A.getId(), VERTEX_B.getId(), 6);
      graph.addEdge(VERTEX_A.getId(), VERTEX_C.getId(), 5);
      graph.addEdge(VERTEX_A.getId(), VERTEX_D.getId(), 4);
      graph.addEdge(VERTEX_C.getId(), VERTEX_E.getId(), 2);
      graph.addEdge(VERTEX_B.getId(), VERTEX_E.getId(), 9);
      graph.addEdge(VERTEX_D.getId(), VERTEX_E.getId(), 2);
      graph.addEdge(VERTEX_E.getId(), VERTEX_F.getId(), 2);

      List<ShortestPathVertex> actual = graph.findShortestPath("A", "F");

      List<ShortestPathVertex> expectedShortestPath = Arrays.asList(
          new ShortestPathVertex(VERTEX_A, 0),
          new ShortestPathVertex(VERTEX_D, 4),
          new ShortestPathVertex(VERTEX_E, 2),
          new ShortestPathVertex(VERTEX_F, 2)
      );

      int totalCost = actual.stream().reduce(0, (sum, vertex) -> sum + vertex.getCostToReachFromPreviousVertex(), Integer::sum);
      assertEquals(expectedShortestPath, actual);
      assertEquals(8, totalCost);
    }
  }

  @Nested
  class AStarSearch {
    private final Vertex VERTEX_1 = new Vertex("NS15", "BISHAN", NORTH_SOUTH_LINE, 1.350838988, 103.8481398);
    private final Vertex VERTEX_2 = new Vertex("NS16", "ANG MO KIO", NORTH_SOUTH_LINE, 1.369933175, 103.8495535);
    private final Vertex VERTEX_3 = new Vertex("NS17", "YIO CHU KANG", NORTH_SOUTH_LINE, 1.381756046, 103.8449439);

    @Test
    void shouldCalculateSimpleLinearPath() throws Exception {
      Graph graph = new Graph();

      graph.addVertex(VERTEX_1);
      graph.addVertex(VERTEX_2);
      graph.addVertex(VERTEX_3);

      graph.addEdge(VERTEX_1.getId(), VERTEX_2.getId(), 2);
      graph.addEdge(VERTEX_2.getId(), VERTEX_3.getId(), 4);

      List<ShortestPathVertex> actual = graph.findShortestPathAStar("NS15", "NS17");

      List<ShortestPathVertex> expectedShortestPath = Arrays.asList(
          new ShortestPathVertex(VERTEX_1, 0),
          new ShortestPathVertex(VERTEX_2, 2),
          new ShortestPathVertex(VERTEX_3, 4)
      );

      int totalCost = actual.stream().reduce(0, (sum, vertex) -> sum + vertex.getCostToReachFromPreviousVertex(), Integer::sum);
      assertEquals(expectedShortestPath, actual);
      assertEquals(6, totalCost);
    }
  }

}
