package display;

import graph.ShortestPathVertex;

import java.util.ArrayList;
import java.util.List;

public class RouteDisplay {

  public List<List<ShortestPathVertex>> display(List<ShortestPathVertex> shortestPathVertices) {
    List<List<ShortestPathVertex>> listOfRegularRoutes = new ArrayList<>();
    List<Integer> indexesOfStartingInterchanges = new ArrayList<>();

    for (int i = 1; i < shortestPathVertices.size(); i++) {
      ShortestPathVertex v1 = shortestPathVertices.get(i-1);
      ShortestPathVertex v2 = shortestPathVertices.get(i);

      if (!v1.getVertex().getMrtTrack().equals(v2.getVertex().getMrtTrack())) {
        indexesOfStartingInterchanges.add(i - 1);
      }
    }

    Integer startingIndex = 0;
    for (Integer index: indexesOfStartingInterchanges) {
      listOfRegularRoutes.add(shortestPathVertices.subList(0, index + 1));
      startingIndex = index;
    }

    listOfRegularRoutes.add(shortestPathVertices.subList(startingIndex + 1, shortestPathVertices.size()));

    // TODO: generate the RoutingInstructions, with each in between being an interchange

    return listOfRegularRoutes;
  }
}


