package traindataparser;

import csv.TrainData;
import graph.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TrainDataParser {
  public static final String TIME_BETWEEN_INTERCHANGES = "5";
  public static final String TIME_BETWEEN_STATIONS = "2";

  public List<Vertex> buildVertices(List<TrainData> trainData) {
    return trainData.stream().map(td -> new Vertex(td.getStationId(), td.getStationName(), td.getLatitude(), td.getLongitude())).collect(Collectors.toList());
  }

  public List<Edge> buildEdges(List<TrainData> trainData) {
    Collections.sort(trainData, Comparator.comparing(TrainData::getLineCode).thenComparing(TrainData::getIndex));

    List<Edge> edges = new ArrayList<>();

    // Find all MRTs that share the same name, for interchanges
    for (TrainData originalTrainData : trainData) {
      trainData
          .stream()
          .filter(td -> td.getStationName().equals(originalTrainData.getStationName()) && !td.getStationId().equals(originalTrainData.getStationId()))
          .forEach(nextTrainData -> {
            Edge newEdge = new Edge(originalTrainData.getStationId(), nextTrainData.getStationId(), TIME_BETWEEN_INTERCHANGES);
            boolean alreadyExists = edges.stream().anyMatch(existingEdge -> existingEdge.equals(newEdge));

            if (!alreadyExists) {
              edges.add(newEdge);
            }
          });
    }

    // Find all MRTs along a single line
    for (TrainData originalTrainData : trainData) {
      String lineCode = originalTrainData.getLineCode();
      int index = originalTrainData.getIndex();

      // Take into account particular station that does not have sequential IDs
      if (originalTrainData.getStationId().equals("NE1")) {
        edges.add(new Edge(originalTrainData.getStationId(), "NE3", TIME_BETWEEN_STATIONS));
        continue;
      }

      trainData
          .stream()
          .filter(td -> td.getLineCode().equals(lineCode) && td.getIndex() == index + 1)
          .findFirst()
          .ifPresent(nextTrainData -> edges.add(new Edge(originalTrainData.getStationId(), nextTrainData.getStationId(), "2")));
    }

    // Edge cases:
    // STC: Routing to two different LRT lines, and loop [STC:SE1], [STC:SE5], [STC:SW1], [STC:SW8]
    edges.add(new Edge("STC", "SE1", "2"));
    edges.add(new Edge("STC", "SE5", "2"));
    edges.add(new Edge("STC", "SW1", "2"));
    edges.add(new Edge("STC", "SW8", "2"));

    // PTC: Routing to two different LRT lines, and loop [PTC:PE1], [PTC:PE7], [PTC:PW1], [PTC:PW7]
    edges.add(new Edge("PTC", "PE1", "2"));
    edges.add(new Edge("PTC", "PE7", "2"));
    edges.add(new Edge("PTC", "PW1", "2"));
    edges.add(new Edge("PTC", "PW7", "2"));

    // Routing from Tanah Merah to Expo branch [EW4:CG1]
    edges.add(new Edge("EW4", "CG1", "2"));

    // Handle fork from Promenade to Bayfront [CC4:CE1]
    edges.add(new Edge("CC4", "CE1", "2"));

    return edges;
  }
}
