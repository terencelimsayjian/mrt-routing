package graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchResultTest {

  @Test
  void shouldOrderNullsLastSmaller() {
    SearchResult searchResult1 = new SearchResult(new Vertex("A"), 0);
    searchResult1.setCostToReachThisVertex(3);
    SearchResult searchResult2 = new SearchResult(new Vertex("B"), 0);

    int result = searchResult1.compareTo(searchResult2);

    assertTrue(result < 0);
  }

  @Test
  void shouldOrderNullsLastLarger() {
    SearchResult searchResult1 = new SearchResult(new Vertex("A"), 0);
    SearchResult searchResult2 = new SearchResult(new Vertex("B"), 0);
    searchResult2.setCostToReachThisVertex(3);

    int result = searchResult1.compareTo(searchResult2);

    assertTrue(result > 0);
  }

  @Test
  void shouldOrderTwoNullsEqually() {
    SearchResult searchResult1 = new SearchResult(new Vertex("A"), 0);
    SearchResult searchResult2 = new SearchResult(new Vertex("B"), 0);
    int result = searchResult1.compareTo(searchResult2);

    assertTrue(result == 0);
  }

  @Test
  void shouldCompareSmallerCost() {
    SearchResult searchResult1 = new SearchResult(new Vertex("A"));
    searchResult1.setCostToReachThisVertex(2);

    SearchResult searchResult2 = new SearchResult(new Vertex("B"));
    searchResult2.setCostToReachThisVertex(3);

    int result = searchResult1.compareTo(searchResult2);

    assertTrue(result < 0);
  }

  @Test
  void shouldCompareLargerCost() {
    SearchResult searchResult1 = new SearchResult(new Vertex("A"));
    searchResult1.setCostToReachThisVertex(5);

    SearchResult searchResult2 = new SearchResult(new Vertex("B"));
    searchResult2.setCostToReachThisVertex(3);

    int result = searchResult1.compareTo(searchResult2);

    assertTrue(result > 0);
  }

  @Test
  void shouldCompareEqualCost() {
    SearchResult searchResult1 = new SearchResult(new Vertex("A"));
    searchResult1.setCostToReachThisVertex(3);

    SearchResult searchResult2 = new SearchResult(new Vertex("B"));
    searchResult2.setCostToReachThisVertex(3);

    int result = searchResult1.compareTo(searchResult2);

    assertTrue(result == 0);
  }

  @Test
  void shouldCompareSmallerHeuristicCost() {
    SearchResult searchResult1 = new SearchResult(new Vertex("A"), 2);
    searchResult1.setCostToReachThisVertex(3);

    SearchResult searchResult2 = new SearchResult(new Vertex("B"), 3);
    searchResult2.setCostToReachThisVertex(3);

    int result = searchResult1.compareTo(searchResult2);

    assertTrue(result < 0);
  }

  @Test
  void shouldCompareLargerHeuristicCost() {
    SearchResult searchResult1 = new SearchResult(new Vertex("A"), 6);
    searchResult1.setCostToReachThisVertex(3);

    SearchResult searchResult2 = new SearchResult(new Vertex("B"), 3);
    searchResult2.setCostToReachThisVertex(3);

    int result = searchResult1.compareTo(searchResult2);

    assertTrue(result > 0);
  }

  @Test
  void shouldCompareEqualHeuristicCost() {
    SearchResult searchResult1 = new SearchResult(new Vertex("A"), 3);
    searchResult1.setCostToReachThisVertex(3);

    SearchResult searchResult2 = new SearchResult(new Vertex("B"), 3);
    searchResult2.setCostToReachThisVertex(3);

    int result = searchResult1.compareTo(searchResult2);

    assertTrue(result == 0);
  }
}
