package display;

import graph.MrtTrack;
import graph.ShortestPathVertex;
import graph.Vertex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RouteDisplayTest {

  @Test
  void name() {
    List<ShortestPathVertex> shortestPathVertexList = new ArrayList<>();
    ShortestPathVertex stationA = new ShortestPathVertex(new Vertex("A", "Station A", MrtTrack.NORTH_SOUTH_LINE, 0, 0), 0);
    ShortestPathVertex stationB = new ShortestPathVertex(new Vertex("B", "Station B", MrtTrack.NORTH_SOUTH_LINE, 0, 0), 0);
    ShortestPathVertex stationC1 = new ShortestPathVertex(new Vertex("C1", "Station C", MrtTrack.NORTH_SOUTH_LINE, 0, 0), 2);
    ShortestPathVertex stationC2 = new ShortestPathVertex(new Vertex("C2", "Station C", MrtTrack.EAST_WEST_LINE, 0, 0), 5);
    ShortestPathVertex stationD = new ShortestPathVertex(new Vertex("D", "Station D", MrtTrack.EAST_WEST_LINE, 0, 0), 2);

    shortestPathVertexList.add(stationA);
    shortestPathVertexList.add(stationB);
    shortestPathVertexList.add(stationC1);
    shortestPathVertexList.add(stationC2);
    shortestPathVertexList.add(stationD);

    List<List<ShortestPathVertex>> actual = new RouteDisplay().display(shortestPathVertexList);

    List<RoutingInstruction> expected = new ArrayList<>();
    expected.add(RoutingInstruction.buildRegular(stationA.getVertex(), stationC1.getVertex(), 2));
    expected.add(RoutingInstruction.buildInterchange(stationC1.getVertex(), stationC2.getVertex(), 5));
    expected.add(RoutingInstruction.buildRegular(stationC2.getVertex(), stationD.getVertex(), 2));

    assertEquals(expected, actual);
  }
}
