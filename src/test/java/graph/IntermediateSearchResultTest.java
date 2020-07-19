package graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntermediateSearchResultTest {

  @Test
  void shouldOrderNullsLastSmaller() {
    IntermediateSearchResult intermediateSearchResult1 = new IntermediateSearchResult(new Vertex("A"), 0);
    intermediateSearchResult1.setCostToReachThisVertex(3);
    IntermediateSearchResult intermediateSearchResult2 = new IntermediateSearchResult(new Vertex("B"), 0);

    int result = intermediateSearchResult1.compareTo(intermediateSearchResult2);

    assertTrue(result < 0);
  }

  @Test
  void shouldOrderNullsLastLarger() {
    IntermediateSearchResult intermediateSearchResult1 = new IntermediateSearchResult(new Vertex("A"), 0);
    IntermediateSearchResult intermediateSearchResult2 = new IntermediateSearchResult(new Vertex("B"), 0);
    intermediateSearchResult2.setCostToReachThisVertex(3);

    int result = intermediateSearchResult1.compareTo(intermediateSearchResult2);

    assertTrue(result > 0);
  }

  @Test
  void shouldOrderTwoNullsEqually() {
    IntermediateSearchResult intermediateSearchResult1 = new IntermediateSearchResult(new Vertex("A"), 0);
    IntermediateSearchResult intermediateSearchResult2 = new IntermediateSearchResult(new Vertex("B"), 0);
    int result = intermediateSearchResult1.compareTo(intermediateSearchResult2);

    assertTrue(result == 0);
  }

  @Test
  void shouldCompareSmallerCost() {
    IntermediateSearchResult intermediateSearchResult1 = new IntermediateSearchResult(new Vertex("A"));
    intermediateSearchResult1.setCostToReachThisVertex(2);

    IntermediateSearchResult intermediateSearchResult2 = new IntermediateSearchResult(new Vertex("B"));
    intermediateSearchResult2.setCostToReachThisVertex(3);

    int result = intermediateSearchResult1.compareTo(intermediateSearchResult2);

    assertTrue(result < 0);
  }

  @Test
  void shouldCompareLargerCost() {
    IntermediateSearchResult intermediateSearchResult1 = new IntermediateSearchResult(new Vertex("A"));
    intermediateSearchResult1.setCostToReachThisVertex(5);

    IntermediateSearchResult intermediateSearchResult2 = new IntermediateSearchResult(new Vertex("B"));
    intermediateSearchResult2.setCostToReachThisVertex(3);

    int result = intermediateSearchResult1.compareTo(intermediateSearchResult2);

    assertTrue(result > 0);
  }

  @Test
  void shouldCompareEqualCost() {
    IntermediateSearchResult intermediateSearchResult1 = new IntermediateSearchResult(new Vertex("A"));
    intermediateSearchResult1.setCostToReachThisVertex(3);

    IntermediateSearchResult intermediateSearchResult2 = new IntermediateSearchResult(new Vertex("B"));
    intermediateSearchResult2.setCostToReachThisVertex(3);

    int result = intermediateSearchResult1.compareTo(intermediateSearchResult2);

    assertTrue(result == 0);
  }

  @Test
  void shouldCompareSmallerHeuristicCost() {
    IntermediateSearchResult intermediateSearchResult1 = new IntermediateSearchResult(new Vertex("A"), 2);
    intermediateSearchResult1.setCostToReachThisVertex(3);

    IntermediateSearchResult intermediateSearchResult2 = new IntermediateSearchResult(new Vertex("B"), 3);
    intermediateSearchResult2.setCostToReachThisVertex(3);

    int result = intermediateSearchResult1.compareTo(intermediateSearchResult2);

    assertTrue(result < 0);
  }

  @Test
  void shouldCompareLargerHeuristicCost() {
    IntermediateSearchResult intermediateSearchResult1 = new IntermediateSearchResult(new Vertex("A"), 6);
    intermediateSearchResult1.setCostToReachThisVertex(3);

    IntermediateSearchResult intermediateSearchResult2 = new IntermediateSearchResult(new Vertex("B"), 3);
    intermediateSearchResult2.setCostToReachThisVertex(3);

    int result = intermediateSearchResult1.compareTo(intermediateSearchResult2);

    assertTrue(result > 0);
  }

  @Test
  void shouldCompareEqualHeuristicCost() {
    IntermediateSearchResult intermediateSearchResult1 = new IntermediateSearchResult(new Vertex("A"), 3);
    intermediateSearchResult1.setCostToReachThisVertex(3);

    IntermediateSearchResult intermediateSearchResult2 = new IntermediateSearchResult(new Vertex("B"), 3);
    intermediateSearchResult2.setCostToReachThisVertex(3);

    int result = intermediateSearchResult1.compareTo(intermediateSearchResult2);

    assertTrue(result == 0);
  }
}
