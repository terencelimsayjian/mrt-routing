package traindata;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {
  @Test
  void shouldBeEqualIfFieldsAreIdentical() {
    Edge a = new Edge("A", "B", 2);
    Edge b = new Edge("A", "B", 2);

    assertTrue(a.equals(b));
  }

  @Test
  void shouldNotBeEqualIfStationIdsAreIdenticalButTimeIsDifferent() {
    Edge a = new Edge("A", "B", 2);
    Edge b = new Edge("A", "B", 4);

    assertFalse(a.equals(b));
  }

  @Test
  void shouldBeEqualIfStationIdsAreFlipped() {
    Edge a = new Edge("A", "B", 2);
    Edge b = new Edge("B", "A", 2);

    assertTrue(a.equals(b));
  }

  @Test
  void shouldNotBeEqualIfStationIdsAreFlippedAndTimeIsDifferent() {
    Edge a = new Edge("A", "B", 2);
    Edge b = new Edge("B", "A", 4);

    assertFalse(a.equals(b));
  }

}
