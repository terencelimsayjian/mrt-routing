package display;

import graph.MrtTrack;
import graph.ShortestPathVertex;
import graph.Vertex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouteDisplayTest {

  public static final ShortestPathVertex STATION_A = new ShortestPathVertex(new Vertex("A", "Station A", MrtTrack.NORTH_SOUTH_LINE, 0, 0), 0);
  public static final ShortestPathVertex STATION_B = new ShortestPathVertex(new Vertex("B", "Station B", MrtTrack.NORTH_SOUTH_LINE, 0, 0), 2);
  public static final ShortestPathVertex STATION_C_1 = new ShortestPathVertex(new Vertex("C1", "Station C", MrtTrack.NORTH_SOUTH_LINE, 0, 0), 2);
  public static final ShortestPathVertex STATION_C_2 = new ShortestPathVertex(new Vertex("C2", "Station C", MrtTrack.EAST_WEST_LINE, 0, 0), 5);
  public static final ShortestPathVertex STATION_D = new ShortestPathVertex(new Vertex("D", "Station D", MrtTrack.EAST_WEST_LINE, 0, 0), 2);

  @Test
  void shouldReturnRoutingInstructionForSimpleTrack() {
    List<ShortestPathVertex> shortestPathVertexList = new ArrayList<>();

    shortestPathVertexList.add(STATION_A);
    shortestPathVertexList.add(STATION_B);
    shortestPathVertexList.add(STATION_C_1);

    List<RoutingInstruction> actual = RouteDisplay.display(shortestPathVertexList);

    List<RoutingInstruction> expected = new ArrayList<>();
    expected.add(RoutingInstruction.buildRegular(STATION_A.getVertex(), STATION_C_1.getVertex(), 4));

    assertEquals(expected.size(), actual.size());
    assertEquals(expected.get(0), actual.get(0));
  }

  @Test
  void shouldReturnRoutingInstructionForTrackInterchangeTrack() {
    List<ShortestPathVertex> shortestPathVertexList = new ArrayList<>();

    shortestPathVertexList.add(STATION_A);
    shortestPathVertexList.add(STATION_B);
    shortestPathVertexList.add(STATION_C_1);
    shortestPathVertexList.add(STATION_C_2);
    shortestPathVertexList.add(STATION_D);

    List<RoutingInstruction> actual = RouteDisplay.display(shortestPathVertexList);

    List<RoutingInstruction> expected = new ArrayList<>();
    expected.add(RoutingInstruction.buildRegular(STATION_A.getVertex(), STATION_C_1.getVertex(), 4));
    expected.add(RoutingInstruction.buildInterchange(STATION_C_1.getVertex(), STATION_C_2.getVertex(), 5));
    expected.add(RoutingInstruction.buildRegular(STATION_C_2.getVertex(), STATION_D.getVertex(), 2));

    assertEquals(expected.size(), actual.size());
    assertEquals(expected.get(0), actual.get(0));
    assertEquals(expected.get(1), actual.get(1));
    assertEquals(expected.get(2), actual.get(2));
  }

  @Test
  void shouldRemoveDuplicateInterchangesAtStartOfPath() {
    List<ShortestPathVertex> shortestPathVertexList = new ArrayList<>();

    shortestPathVertexList.add(STATION_C_1);
    shortestPathVertexList.add(STATION_C_2);
    shortestPathVertexList.add(STATION_D);

    List<RoutingInstruction> actual = RouteDisplay.display(shortestPathVertexList);

    List<RoutingInstruction> expected = new ArrayList<>();
    expected.add(RoutingInstruction.buildRegular(STATION_C_2.getVertex(), STATION_D.getVertex(), 2));

    assertEquals(expected.size(), actual.size());
    assertEquals(expected.get(0), actual.get(0));
  }

  @Test
  void shouldRemoveMultipleDuplicateInterchangesAtStartOfPath() {
    List<ShortestPathVertex> shortestPathVertexList = new ArrayList<>();

    shortestPathVertexList.add(STATION_C_1);
    shortestPathVertexList.add(STATION_C_2);
    shortestPathVertexList.add(STATION_C_1);
    shortestPathVertexList.add(STATION_C_2);
    shortestPathVertexList.add(STATION_D);

    List<RoutingInstruction> actual = RouteDisplay.display(shortestPathVertexList);

    List<RoutingInstruction> expected = new ArrayList<>();
    expected.add(RoutingInstruction.buildRegular(STATION_C_2.getVertex(), STATION_D.getVertex(), 2));

    assertEquals(expected.size(), actual.size());
    assertEquals(expected.get(0), actual.get(0));
  }

  @Test
  void shouldRemoveDuplicateInterchangesAtEndOfPath() {
    List<ShortestPathVertex> shortestPathVertexList = new ArrayList<>();

    shortestPathVertexList.add(STATION_A);
    shortestPathVertexList.add(STATION_B);
    shortestPathVertexList.add(STATION_C_1);
    shortestPathVertexList.add(STATION_C_2);

    List<RoutingInstruction> actual = RouteDisplay.display(shortestPathVertexList);

    List<RoutingInstruction> expected = new ArrayList<>();
    expected.add(RoutingInstruction.buildRegular(STATION_A.getVertex(), STATION_C_1.getVertex(), 4));

    assertEquals(expected.size(), actual.size());
    assertEquals(expected.get(0), actual.get(0));
  }

  @Test
  void shouldRemoveMultipleDuplicateInterchangesAtEndOfPath() {
    List<ShortestPathVertex> shortestPathVertexList = new ArrayList<>();

    shortestPathVertexList.add(STATION_A);
    shortestPathVertexList.add(STATION_B);
    shortestPathVertexList.add(STATION_C_1);
    shortestPathVertexList.add(STATION_C_2);
    shortestPathVertexList.add(STATION_C_1);
    shortestPathVertexList.add(STATION_C_2);
    shortestPathVertexList.add(STATION_C_1);
    shortestPathVertexList.add(STATION_C_2);

    List<RoutingInstruction> actual = RouteDisplay.display(shortestPathVertexList);

    List<RoutingInstruction> expected = new ArrayList<>();
    expected.add(RoutingInstruction.buildRegular(STATION_A.getVertex(), STATION_C_1.getVertex(), 4));

    assertEquals(expected.size(), actual.size());
    assertEquals(expected.get(0), actual.get(0));
  }
}
