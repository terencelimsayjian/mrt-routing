package csv;


import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

public class CsvReader {
  public List<TrainData> getTrainDataFromCSV() throws FileNotFoundException {
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
