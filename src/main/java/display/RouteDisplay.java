package display;

import graph.ShortestPathVertex;

import java.util.ArrayList;
import java.util.List;

public class RouteDisplay {

  public static List<RoutingInstruction> display(List<ShortestPathVertex> shortestPath) {
    List<ShortestPathVertex> shortestPathWithoutDuplicates = removeDuplicateStationsFromStartAndEnd(shortestPath);
    List<List<ShortestPathVertex>> listOfDirectRoutes = buildListOfDirectRoutes(shortestPathWithoutDuplicates);

    List<RoutingInstruction> routingInstructions = new ArrayList<>();

    for (int i = 0; i < listOfDirectRoutes.size(); i++) {
      List<ShortestPathVertex> currentPath = listOfDirectRoutes.get(i);
      if (i != 0) {
        List<ShortestPathVertex> precedingPath = listOfDirectRoutes.get(i - 1);


        routingInstructions.add(RoutingInstruction.buildInterchange(
            precedingPath.get(precedingPath.size() - 1).getVertex(),
            currentPath.get(0).getVertex(),
            currentPath.get(0).getCostToReachFromPreviousVertex())
        );
      }

      // Sum of cost to reach the second vertex onwards
      int costToTraversePath = currentPath.stream()
          .skip(1)
          .reduce(0, (sum, vertex) -> sum + vertex.getCostToReachFromPreviousVertex(), Integer::sum);

      routingInstructions.add(RoutingInstruction.buildRegular(
          currentPath.get(0).getVertex(),
          currentPath.get(currentPath.size() - 1).getVertex(),
          costToTraversePath)
      );
    }


    return routingInstructions;
  }

  private static List<List<ShortestPathVertex>> buildListOfDirectRoutes(List<ShortestPathVertex> shortestPathWithoutDuplicates) {
    List<List<ShortestPathVertex>> listOfDirectRoutes = new ArrayList<>();
    List<Integer> indexesOfStartingInterchanges = new ArrayList<>();

    for (int i = 1; i < shortestPathWithoutDuplicates.size(); i++) {
      ShortestPathVertex v1 = shortestPathWithoutDuplicates.get(i - 1);
      ShortestPathVertex v2 = shortestPathWithoutDuplicates.get(i);

      if (!v1.getVertex().getMrtTrack().equals(v2.getVertex().getMrtTrack())) {
        indexesOfStartingInterchanges.add(i - 1);
      }
    }

    Integer startingIndex = 0;
    for (Integer index : indexesOfStartingInterchanges) {
      listOfDirectRoutes.add(shortestPathWithoutDuplicates.subList(startingIndex, index + 1));
      startingIndex = index + 1;
    }
    listOfDirectRoutes.add(shortestPathWithoutDuplicates.subList(startingIndex, shortestPathWithoutDuplicates.size()));
    return listOfDirectRoutes;
  }

  private static List<ShortestPathVertex> removeDuplicateStationsFromStartAndEnd(List<ShortestPathVertex> shortestPath) {
    int numberOfElementsToRemoveFromStart = 0;
    String firstVertexDisplayName = shortestPath.get(0).getVertex().getDisplayName();

    for (int i = 1; i < shortestPath.size(); i++) {
      if (shortestPath.get(i).getVertex().getDisplayName().equals(firstVertexDisplayName)) {
        numberOfElementsToRemoveFromStart++;
      } else {
        break;
      }
    }

    int numberOfElementsToRemoveFromEnd = 0;
    String lastVertexDisplayName = shortestPath.get(shortestPath.size() - 1).getVertex().getDisplayName();

    for (int i = shortestPath.size() - 2; i > 0; i--) {
      if (shortestPath.get(i).getVertex().getDisplayName().equals(lastVertexDisplayName)) {
        numberOfElementsToRemoveFromEnd++;
      } else {
        break;
      }
    }

    shortestPath = shortestPath.subList(numberOfElementsToRemoveFromStart, shortestPath.size() - numberOfElementsToRemoveFromEnd);
    return shortestPath;
  }
}


