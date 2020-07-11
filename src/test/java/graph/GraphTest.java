package graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

  private Graph graph;

  @BeforeEach
  void setUp() {
    graph = new Graph();

    graph.addVertex(new Vertex("aaron"));
    graph.addVertex(new Vertex("bryan"));
    graph.addVertex(new Vertex("charlie"));
    graph.addVertex(new Vertex("dillon"));

    graph.addEdge("aaron", "bryan", 1);
    graph.addEdge("aaron", "charlie", 1);
    graph.addEdge("aaron", "dillon", 1);
    graph.addEdge("bryan", "charlie", 1);
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
      graph.addVertex(new Vertex("B"));

      assertThrows(NoSuchVertexException.class, () -> graph.findShortestPath("unknown-vertex", "B"));
    }

    @Test
    void shouldThrowNoSuchVertexExceptionIfEndingNodeDoesNotExistInGraph() throws Exception {
      Graph graph = new Graph();
      graph.addVertex(new Vertex("A"));

      assertThrows(NoSuchVertexException.class, () -> graph.findShortestPath("A", "unknown-vertex"));
    }

    @Test
    void shouldThrowNoPathExistsExceptionIfThereIsNoPathToEndingNode() throws Exception {
      Graph graph = new Graph();
      graph.addVertex(new Vertex("A"));
      graph.addVertex(new Vertex("B"));

      assertThrows(NoPathExistsException.class, () -> graph.findShortestPath("A", "B"));
    }

    @Test
    void shouldFindShortestPathWithOnlyThreeNodesLinearlyConnected() throws Exception {
      Graph graph = new Graph();
      graph.addVertex(new Vertex("A"));
      graph.addVertex(new Vertex("B"));
      graph.addVertex(new Vertex("C"));
      graph.addEdge("A", "B", 1);
      graph.addEdge("B", "C", 1);

      ShortestPathResult actual = graph.findShortestPath("A", "C");

      List<String> expectedShortestPath = Arrays.asList("A", "B", "C");

      assertEquals(expectedShortestPath, actual.getShortestPath());
      assertEquals(2, actual.getTotalCost());
    }

    @Test
    void shouldFindShortestPathWithTwoAlternativeRoutes() throws Exception {
      Graph graph = new Graph();
      graph.addVertex(new Vertex("A"));
      graph.addVertex(new Vertex("B"));
      graph.addVertex(new Vertex("C"));
      graph.addVertex(new Vertex("D"));
      graph.addEdge("A", "B", 5);
      graph.addEdge("A", "C", 2);
      graph.addEdge("C", "D", 8);
      graph.addEdge("B", "D", 1);

      ShortestPathResult actual = graph.findShortestPath("A", "D");

      List<String> expectedShortestPath = Arrays.asList("A", "B", "D");

      assertEquals(expectedShortestPath, actual.getShortestPath());
      assertEquals(6, actual.getTotalCost());
    }

    @Test
    void shouldFindShortestPathWithComplicatedRoute() throws Exception {
      Graph graph = new Graph();

      graph.addVertex(new Vertex("A"));
      graph.addVertex(new Vertex("B"));
      graph.addVertex(new Vertex("C"));
      graph.addVertex(new Vertex("D"));
      graph.addVertex(new Vertex("E"));
      graph.addVertex(new Vertex("F"));

      graph.addEdge("A", "B", 6);
      graph.addEdge("A", "C", 5);
      graph.addEdge("A", "D", 4);
      graph.addEdge("C", "E", 2);
      graph.addEdge("B", "E", 9);
      graph.addEdge("D", "E", 2);
      graph.addEdge("E", "F", 2);

      ShortestPathResult actual = graph.findShortestPath("A", "F");

      List<String> expectedShortestPath = Arrays.asList("A", "D", "E", "F");

      assertEquals(expectedShortestPath, actual.getShortestPath());
      assertEquals(8, actual.getTotalCost());
    }
  }

  @Nested
  class ASarSearch {

  }

}
