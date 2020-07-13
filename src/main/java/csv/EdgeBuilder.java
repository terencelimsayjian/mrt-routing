package csv;


import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import graph.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EdgeBuilder {

  public void populateEdges() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
    List<TrainData> trainData = getTrainDataFromCSV();

    Collections.sort(trainData, Comparator.comparing(TrainData::getLineCode).thenComparing(TrainData::getIndex));

    List<Edge> edges = new ArrayList<>();

    // Find all MRTs that share the same name, for interchanges
    for (TrainData originalTrainData : trainData) {
      trainData
          .stream()
          .filter(td -> td.getStationName().equals(originalTrainData.getStationName()) && !td.getStationId().equals(originalTrainData.getStationId()))
          .forEach(nextTrainData -> {
            Edge newEdge = new Edge(originalTrainData.getStationId(), nextTrainData.getStationId(), "2");
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
        edges.add(new Edge(originalTrainData.getStationId(), "NE3", "2"));
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

    write(edges);
  }

  private List<TrainData> getTrainDataFromCSV() throws FileNotFoundException {
    File trainFile = getFileFromResources("trains_sg.csv");

    List<TrainData> trainData = new CsvToBeanBuilder(new FileReader(trainFile)).withType(TrainData.class).build().parse();

    trainData.forEach(td -> {
      String stationId = td.getStationId();

      String lineCode = stationId.substring(0, 2);
      String stationIndex = stationId.substring(2, stationId.length());

      int stationIntIndex = 0;
      try {
        stationIntIndex = Integer.parseInt(stationIndex);
      } catch (NumberFormatException e) {
        // To handle stations that don't adhere to naming conventions AA11, namely STC and PTC
        System.out.println(td.getStationId());
      }

      td.setLineCode(lineCode);
      td.setIndex(stationIntIndex);
    });
    return trainData;
  }

  public void write(List<Edge> edges) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
    String fileName = "edges.csv";
    File edgesFile = new File(fileName);
    edgesFile.createNewFile();

    File trainFile = getFileFromResources(fileName);
    Writer writer = new FileWriter(trainFile);
    StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).withApplyQuotesToAll(false).build();
    beanToCsv.write(edges);
    writer.close();
  }

  private File getFileFromResources(String fileName) {
    ClassLoader classLoader = getClass().getClassLoader();

    URL resource = classLoader.getResource(fileName);
    if (resource == null) {
      throw new IllegalArgumentException("file is not found!");
    } else {
      return new File(resource.getFile());
    }

  }
}
