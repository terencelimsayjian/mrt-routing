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

  public void populateEdges() throws FileNotFoundException {
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
        System.out.println(td.getStationId());
        // Manually add these later (STC / PTC)
      }

      td.setLineCode(lineCode);
      td.setIndex(stationIntIndex);
    });

    Collections.sort(trainData, Comparator.comparing(TrainData::getLineCode).thenComparing(TrainData::getIndex));

    List<Edge> edges = new ArrayList<>();

    // Find all MRTs along a single line
    for (TrainData originalTrainData : trainData) {
      trainData
          .stream()
          .filter(td -> td.getStationName().equals(originalTrainData.getStationName()) && !td.getStationId().equals(originalTrainData.getStationId()))
          .findFirst()
          .ifPresent(nextTrainData -> {
            edges.add(new Edge(originalTrainData.getStationId(), nextTrainData.getStationId(), "2"));
          });
    }

    // Find all MRTs that share the same name, for interchanges
    for (TrainData originalTrainData : trainData) {
      String lineCode = originalTrainData.getLineCode();
      int index = originalTrainData.getIndex();

      trainData
          .stream()
          .filter(td -> td.getLineCode().equals(lineCode) && td.getIndex() == index + 1)
          .findFirst()
          .ifPresent(nextTrainData -> {
            edges.add(new Edge(originalTrainData.getStationId(), nextTrainData.getStationId(), "2"));
          });
      }
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
