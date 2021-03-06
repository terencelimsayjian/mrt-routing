package traindata;

import graph.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static graph.MrtTrack.NORTH_SOUTH_LINE;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrainDataParserTest {

  private TrainDataParser trainDataParser;

  private CsvTrainDataSource csvTrainDataSource;


  @BeforeEach
  void setUp() {
    csvTrainDataSource = mock(CsvTrainDataSource.class);
    trainDataParser = new TrainDataParser(csvTrainDataSource);
  }

  @Nested
  class BuildVertices {

    @Test
    void shouldMapTrainDataToVertices() {
      TrainData trainData1 = new TrainData("A", "CC1", 0, 0, "NS", "CC", 1);
      TrainData trainData2 = new TrainData("B", "CC2", 1, 1, "NS", "CC", 2);
      TrainData trainData3 = new TrainData("C", "DD1", 2, 2, "NS", "DD", 1);

      ArrayList<TrainData> trainData = new ArrayList<>();
      trainData.add(trainData1);
      trainData.add(trainData2);
      trainData.add(trainData3);

      when(csvTrainDataSource.getTrainData()).thenReturn(trainData);

      List<Vertex> vertices = trainDataParser.buildVertices();

      assertTrue(vertices.contains(new Vertex("CC1", "A", NORTH_SOUTH_LINE, 0, 0)));
      assertTrue(vertices.contains(new Vertex("CC2", "B", NORTH_SOUTH_LINE, 1, 1)));
      assertTrue(vertices.contains(new Vertex("DD1", "C", NORTH_SOUTH_LINE, 2, 2)));
    }
  }

  @Nested
  class BuildEdges {
    @Test
    void shouldPopulateDuplicateTrainDataWithDifferentIdsAsInterchanges() {
      TrainData trainData1 = new TrainData("A", "CC1", 0, 0, "NS", "CC", 1);
      TrainData trainData2 = new TrainData("A", "DD1", 0, 0, "NS", "DD", 1);
      TrainData trainData3 = new TrainData("A", "EE1", 0, 0, "NS", "EE", 1);

      ArrayList<TrainData> trainData = new ArrayList<>();
      trainData.add(trainData1);
      trainData.add(trainData2);
      trainData.add(trainData3);

      when(csvTrainDataSource.getTrainData()).thenReturn(trainData);

      List<Edge> edges = trainDataParser.buildEdges();

      Edge edge1 = new Edge("CC1", "DD1", 5);
      Edge edge2 = new Edge("DD1", "EE1", 5);
      Edge edge3 = new Edge("EE1", "CC1", 5);

      assertTrue(edges.contains(edge1));
      assertTrue(edges.contains(edge2));
      assertTrue(edges.contains(edge3));
    }

    @Test
    void shouldPopulateTrainDataWithSameLineCodeAsASingleTrack() {
      TrainData trainData1 = new TrainData("A", "CC1", 0, 0, "NS", "CC", 1);
      TrainData trainData2 = new TrainData("B", "CC2", 0, 0, "NS", "CC", 2);
      TrainData trainData3 = new TrainData("C", "CC3", 0, 0, "NS", "CC", 3);

      ArrayList<TrainData> trainData = new ArrayList<>();
      trainData.add(trainData1);
      trainData.add(trainData2);
      trainData.add(trainData3);

      when(csvTrainDataSource.getTrainData()).thenReturn(trainData);

      List<Edge> edges = trainDataParser.buildEdges();

      Edge edge1 = new Edge("CC1", "CC2", 2);
      Edge edge2 = new Edge("CC2", "CC3", 2);

      assertTrue(edges.contains(edge1));
      assertTrue(edges.contains(edge2));
    }

    @Nested
    class EdgeCases {
      @Test
      void handleSTCLRTConnections() {
        List<Edge> edges = trainDataParser.buildEdges();
        Edge edge1 = new Edge("STC", "SE1", 2);
        Edge edge2 = new Edge("STC", "SE5", 2);
        Edge edge3 = new Edge("STC", "SW1", 2);
        Edge edge4 = new Edge("STC", "SW8", 2);

        assertTrue(edges.contains(edge1));
        assertTrue(edges.contains(edge2));
        assertTrue(edges.contains(edge3));
        assertTrue(edges.contains(edge4));
      }

      @Test
      void handlePTCLRTConnections() {
        List<Edge> edges = trainDataParser.buildEdges();
        Edge edge1 = new Edge("PTC", "PE1", 2);
        Edge edge2 = new Edge("PTC", "PE7", 2);
        Edge edge3 = new Edge("PTC", "PW1", 2);
        Edge edge4 = new Edge("PTC", "PW7", 2);

        assertTrue(edges.contains(edge1));
        assertTrue(edges.contains(edge2));
        assertTrue(edges.contains(edge3));
        assertTrue(edges.contains(edge4));
      }

      @Test
      void handleTanahMerahToExpoFork() {
        List<Edge> edges = trainDataParser.buildEdges();
        Edge edge = new Edge("EW4", "CG1", 2);

        assertTrue(edges.contains(edge));
      }

      @Test
      void handlePromenadeToBayfrontFork() {
        List<Edge> edges = trainDataParser.buildEdges();
        Edge edge = new Edge("CC4", "CE1", 2);

        assertTrue(edges.contains(edge));
      }

      @Test
      void shouldConnectNS5ToNS7() {
        TrainData trainData1 = new TrainData("A", "NS5", 0, 0, "NS", "CC", 5);
        TrainData trainData2 = new TrainData("B", "NS7", 0, 0, "NS", "CC", 7);
        TrainData trainData3 = new TrainData("C", "NS8", 0, 0, "NS", "CC", 8);

        ArrayList<TrainData> trainData = new ArrayList<>();
        trainData.add(trainData1);
        trainData.add(trainData2);
        trainData.add(trainData3);

        when(csvTrainDataSource.getTrainData()).thenReturn(trainData);

        List<Edge> edges = trainDataParser.buildEdges();

        Edge edge1 = new Edge("NS5", "NS7", 2);
        Edge edge2 = new Edge("NS7", "NS8", 2);

        assertTrue(edges.contains(edge1));
        assertTrue(edges.contains(edge2));
      }

      @Test
      void shouldConnectNE1ToNE3() {
        TrainData trainData1 = new TrainData("A", "NE1", 0, 0, "NS", "CC", 1);
        TrainData trainData2 = new TrainData("B", "NE3", 0, 0, "NS", "CC", 3);
        TrainData trainData3 = new TrainData("C", "NE4", 0, 0, "NS", "CC", 4);

        ArrayList<TrainData> trainData = new ArrayList<>();
        trainData.add(trainData1);
        trainData.add(trainData2);
        trainData.add(trainData3);

        when(csvTrainDataSource.getTrainData()).thenReturn(trainData);

        List<Edge> edges = trainDataParser.buildEdges();

        Edge edge1 = new Edge("NE1", "NE3", 2);
        Edge edge2 = new Edge("NE3", "NE4", 2);

        assertTrue(edges.contains(edge1));
        assertTrue(edges.contains(edge2));
      }
    }

  }
}
