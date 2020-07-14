package traindataparser;

import csv.TrainData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainDataParserTest {

  private TrainDataParser trainDataParser;

  @BeforeEach
  void setUp() {
    trainDataParser = new TrainDataParser();
  }

  @Nested
  class BuildEdges {
    @Test
    void shouldPopulateDuplicateTrainDataWithDifferentIdsAsInterchanges() {
      TrainData trainData1 = new TrainData("A", "CC1", 0, 0, "CC", 1);
      TrainData trainData2 = new TrainData("A", "DD1", 0, 0, "DD", 1);
      TrainData trainData3 = new TrainData("A", "EE1", 0, 0, "EE", 1);

      ArrayList<TrainData> trainData = new ArrayList<>();
      trainData.add(trainData1);
      trainData.add(trainData2);
      trainData.add(trainData3);

      List<Edge> edges = trainDataParser.buildEdges(trainData);

      Edge edge1 = new Edge("CC1", "DD1", "5");
      Edge edge2 = new Edge("DD1", "EE1", "5");
      Edge edge3 = new Edge("EE1", "CC1", "5");

      assertTrue(edges.contains(edge1));
      assertTrue(edges.contains(edge2));
      assertTrue(edges.contains(edge3));
    }

    @Test
    void shouldPopulateTrainDataWithSameLineCodeAsASingleTrack() {
      TrainData trainData1 = new TrainData("A", "CC1", 0, 0, "CC", 1);
      TrainData trainData2 = new TrainData("B", "CC2", 0, 0, "CC", 2);
      TrainData trainData3 = new TrainData("C", "CC3", 0, 0, "CC", 3);

      ArrayList<TrainData> trainData = new ArrayList<>();
      trainData.add(trainData1);
      trainData.add(trainData2);
      trainData.add(trainData3);

      List<Edge> edges = trainDataParser.buildEdges(trainData);

      Edge edge1 = new Edge("CC1", "CC2", "2");
      Edge edge2 = new Edge("CC2", "CC3", "2");

      assertTrue(edges.contains(edge1));
      assertTrue(edges.contains(edge2));
    }

    class EdgeCases {
      @Test
      void handleSTCLRTConnections() {
        List<Edge> edges = trainDataParser.buildEdges(new ArrayList<>());
        Edge edge1 = new Edge("STC", "SE1", "2");
        Edge edge2 = new Edge("STC", "SE5", "2");
        Edge edge3 = new Edge("STC", "SW1", "2");
        Edge edge4 = new Edge("STC", "SW8", "2");

        assertTrue(edges.contains(edge1));
        assertTrue(edges.contains(edge2));
        assertTrue(edges.contains(edge3));
        assertTrue(edges.contains(edge4));
      }

      @Test
      void handlePTCLRTConnections() {
        List<Edge> edges = trainDataParser.buildEdges(new ArrayList<>());
        Edge edge1 = new Edge("PTC", "PE1", "2");
        Edge edge2 = new Edge("PTC", "PE7", "2");
        Edge edge3 = new Edge("PTC", "PW1", "2");
        Edge edge4 = new Edge("PTC", "PW7", "2");

        assertTrue(edges.contains(edge1));
        assertTrue(edges.contains(edge2));
        assertTrue(edges.contains(edge3));
        assertTrue(edges.contains(edge4));
      }

      @Test
      void handleTanahMerahToExpoFork() {
        List<Edge> edges = trainDataParser.buildEdges(new ArrayList<>());
        Edge edge = new Edge("EW4", "CG1", "2");

        assertTrue(edges.contains(edge));
      }

      @Test
      void handlePromenadeToBayfrontFork() {
        List<Edge> edges = trainDataParser.buildEdges(new ArrayList<>());
        Edge edge = new Edge("CC4", "CE1", "2");

        assertTrue(edges.contains(edge));
      }
    }

  }
}