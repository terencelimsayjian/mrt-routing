package csv;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EdgeBuilderTest {

  @Test
  void read() throws FileNotFoundException {
    new EdgeBuilder().populateEdges();
  }

  @Test
  void write() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

    List<Edge> edges = new ArrayList<>();
    edges.add(new Edge("CC1", "CC2", "2"));
    edges.add(new Edge("CC2", "CC3", "2"));
    edges.add(new Edge("CC3", "CC4", "2"));
    edges.add(new Edge("CC4", "CC5", "2"));


    new EdgeBuilder().write(edges);
  }
}
