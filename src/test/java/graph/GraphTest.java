package graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

  @Test
  void djikstraSearch() {
    Graph mrtGraph = getMrtMap();

    mrtGraph.djikstraSearch("A", "F");
  }

  private Graph getMrtMap() {
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
    graph.addEdge("C", "E", 1);
    graph.addEdge("B", "E", 9);
    graph.addEdge("D", "E", 2);
    graph.addEdge("E", "F", 2);

    return graph;
  }
}
