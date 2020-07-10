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

    graph.addVertex("aaron");
    graph.addVertex("bryan");
    graph.addVertex("charlie");
    graph.addVertex("dillon");

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
      graph.addVertex("B");

      assertThrows(NoSuchVertexException.class, () -> graph.findShortestPath("unknown-vertex", "B"));
    }

    @Test
    void shouldThrowNoSuchVertexExceptionIfEndingNodeDoesNotExistInGraph() throws Exception {
      Graph graph = new Graph();
      graph.addVertex("A");

      assertThrows(NoSuchVertexException.class, () -> graph.findShortestPath("A", "unknown-vertex"));
    }

    @Test
    void shouldThrowNoPathExistsExceptionIfThereIsNoPathToEndingNode() throws Exception {
      Graph graph = new Graph();
      graph.addVertex("A");
      graph.addVertex("B");

      assertThrows(NoPathExistsException.class, () -> graph.findShortestPath("A", "B"));
    }

    @Test
    void shouldFindShortestPathWithOnlyThreeNodesLinearlyConnected() throws Exception {
      Graph graph = new Graph();
      graph.addVertex("A");
      graph.addVertex("B");
      graph.addVertex("C");
      graph.addEdge("A", "B", 1);
      graph.addEdge("B", "C", 1);

      List<Vertex> actual = graph.findShortestPath("A", "C");

      List<Vertex> expectedShortestPath = Arrays.asList(new Vertex("A"), new Vertex("B"), new Vertex("C"));

      assertEquals(expectedShortestPath, actual);
    }

    @Test
    void shouldFindShortestPathWithTwoAlternativeRoutes() throws Exception {
      Graph graph = new Graph();
      graph.addVertex("A");
      graph.addVertex("B");
      graph.addVertex("C");
      graph.addVertex("D");
      graph.addEdge("A", "B", 5);
      graph.addEdge("A", "C", 2);
      graph.addEdge("C", "D", 8);
      graph.addEdge("B", "D", 1);

      List<Vertex> actual = graph.findShortestPath("A", "D");

      List<Vertex> expectedShortestPath = Arrays.asList(new Vertex("A"), new Vertex("B"), new Vertex("D"));

      assertEquals(expectedShortestPath, actual);
    }

    @Test
    void shouldFindShortestPathWithComplicatedRoute() throws Exception {
      Graph graph = new Graph();

      graph.addVertex("A");
      graph.addVertex("B");
      graph.addVertex("C");
      graph.addVertex("D");
      graph.addVertex("E");
      graph.addVertex("F");

      graph.addEdge("A", "B", 6);
      graph.addEdge("A", "C", 5);
      graph.addEdge("A", "D", 4);
      graph.addEdge("C", "E", 2);
      graph.addEdge("B", "E", 9);
      graph.addEdge("D", "E", 2);
      graph.addEdge("E", "F", 2);

      List<Vertex> actual = graph.findShortestPath("A", "F");

      List<Vertex> expectedShortestPath = Arrays.asList(new Vertex("A"), new Vertex("D"), new Vertex("E"), new Vertex("F"));

      assertEquals(expectedShortestPath, actual);
    }

  }

}
